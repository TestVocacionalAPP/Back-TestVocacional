package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.CompraRecursoRepository;
import GTns_TestV.infra.repository.RecursoRepository;
import GTns_TestV.model.dto.CompraRequestDTO;
import GTns_TestV.model.dto.CompraResponseDTO;
import GTns_TestV.model.dto.PagoDTO;
import GTns_TestV.model.dto.recurso.RecursoCreateDTO;
import GTns_TestV.model.dto.recurso.RecursoResponseDTO;
import GTns_TestV.model.entity.CompraRecurso;
import GTns_TestV.model.entity.Recurso;
import GTns_TestV.model.dto.mapper.RecursoMapper;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.enums.EstadoCompra;
import GTns_TestV.service.RecursoService;
import GTns_TestV.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecursoServiceImpl implements RecursoService {

    private final RecursoRepository recursoRepository;
    private final RecursoMapper recursoMapper;
    private final CompraRecursoRepository compraRecursoRepository;
    private final UsuarioService usuarioService;
    @Override
    public RecursoResponseDTO crearRecurso(RecursoCreateDTO recursoCreateDTO) {
        Recurso recurso = recursoMapper.toEntity(recursoCreateDTO);
        Recurso recursoGuardado = recursoRepository.save(recurso);
        return recursoMapper.toResponseDTO(recursoGuardado);
    }

    @Override
    public List<RecursoResponseDTO> listarRecursos() {
        Usuario usuario = usuarioService.getAuthenticatedUser();

        List<Recurso> recursos = recursoRepository.findAll();

        return recursos.stream()
                .map(recurso -> {
                    boolean tieneAcceso = false;
                    // Si el recurso es Premium, verifica si el usuario lo ha comprado
                    if (recurso.isEsPremium()) {
                        tieneAcceso = compraRecursoRepository.existsByUsuarioIdAndRecursoId(usuario.getId(), recurso.getId());
                    } else {
                        // Si el recurso no es premium, tiene acceso automáticamente
                        tieneAcceso = true;
                    }
                    return recursoMapper.toResponseDTO(recurso, tieneAcceso);
                })
                .collect(Collectors.toList());
    }



    @Override
    public List<RecursoResponseDTO> buscarRecursosPorTitulo(String titulo) {
        List<Recurso> recursos = recursoRepository.findByTituloContaining(titulo);

        return recursos.stream()
                .map(recurso -> recursoMapper.toResponseDTO(recurso, false)) // tieneAcceso en false por defecto
                .collect(Collectors.toList());
    }


    @Override
    public RecursoResponseDTO actualizarRecurso(Long id, RecursoCreateDTO recursoCreateDTO) {
        Recurso recursoExistente = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        recursoExistente.setTitulo(recursoCreateDTO.getTitulo());
        recursoExistente.setDescripcion(recursoCreateDTO.getDescripcion());
        recursoExistente.setTipoRecurso(recursoCreateDTO.getTipoRecurso());
        recursoExistente.setUrlRecurso(recursoCreateDTO.getUrlRecurso());
        recursoExistente.setCategoriaRecurso(recursoCreateDTO.getCategoriaRecurso());
        recursoExistente.setPrecio(recursoCreateDTO.getPrecio()); // Actualizar el precio

        Recurso recursoActualizado = recursoRepository.save(recursoExistente);
        return recursoMapper.toResponseDTO(recursoActualizado);
    }


    @Override
    public void eliminarRecurso(Long id) {
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        recursoRepository.delete(recurso);
    }


    public CompraResponseDTO comprarRecurso(Long idRecurso, PagoDTO pagoDTO, Integer cantidad) {
        Usuario usuario = usuarioService.getAuthenticatedUser();
        Recurso recurso = recursoRepository.findById(idRecurso)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        if (!recurso.isEsPremium()) {
            throw new IllegalArgumentException("Este recurso no es premium y no requiere compra.");
        }

        boolean yaComprado = compraRecursoRepository.existsByUsuarioIdAndRecursoId(usuario.getId(), idRecurso);
        if (yaComprado) {
            throw new IllegalArgumentException("Este recurso ya ha sido comprado por el usuario.");
        }

        // Validación del número de tarjeta y extracción de los últimos cuatro dígitos
        String numeroTarjeta = pagoDTO.getNumeroTarjeta();
        if (numeroTarjeta == null || numeroTarjeta.length() < 4) {
            throw new IllegalArgumentException("Número de tarjeta inválido.");
        }
        String ultimosDigitosTarjeta = numeroTarjeta.substring(numeroTarjeta.length() - 4);

        // Crear la compra del recurso con la cantidad asignada
        CompraRecurso compraRecurso = CompraRecurso.builder()
                .usuario(usuario)
                .recurso(recurso)
                .estado(EstadoCompra.APROBADO)
                .ultimosDigitosTarjeta(ultimosDigitosTarjeta)
                .tipoTarjeta(pagoDTO.getTipoTarjeta())
                .cantidad(cantidad) // Asignar la cantidad aquí
                .fecha(LocalDate.now()) // Asignar la fecha actual
                .build();

        // Guardar la compra en el repositorio
        compraRecursoRepository.save(compraRecurso);

        // Crear y devolver un CompraResponseDTO
        CompraResponseDTO compraResponseDTO = new CompraResponseDTO();
        compraResponseDTO.setId(compraRecurso.getId());
        compraResponseDTO.setRecursoTitulo(recurso.getTitulo());
        compraResponseDTO.setPrecio(recurso.getPrecio().doubleValue());
        compraResponseDTO.setCantidad(compraRecurso.getCantidad());
        compraResponseDTO.setFecha(compraRecurso.getFecha() != null ? compraRecurso.getFecha().toString() : "Sin fecha");

        return compraResponseDTO;
    }



    @Override
    public List<CompraResponseDTO> obtenerHistorialCompras() {
        Usuario usuario = usuarioService.getAuthenticatedUser();
        List<CompraRecurso> compras = compraRecursoRepository.findByUsuarioId(usuario.getId());

        return compras.stream().map(compra -> {
            CompraResponseDTO compraResponseDTO = new CompraResponseDTO();
            compraResponseDTO.setId(compra.getId());
            compraResponseDTO.setRecursoTitulo(compra.getRecurso().getTitulo());
            compraResponseDTO.setPrecio(compra.getRecurso().getPrecio().doubleValue());
            compraResponseDTO.setCantidad(compra.getCantidad());
            compraResponseDTO.setFecha(compra.getFecha() != null ? compra.getFecha().toString() : "Sin fecha"); // Manejo de nulos
            return compraResponseDTO;
        }).collect(Collectors.toList());
    }


}
