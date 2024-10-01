package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.UsuarioRepository;
import GTns_TestV.model.dto.UsuarioPerfilDTO;
import GTns_TestV.model.dto.mapper.UsuarioMapper;
import GTns_TestV.model.dto.UsuarioDTO;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.enums.Role;
import GTns_TestV.security.JwtService;
import GTns_TestV.security.LoginRequest;
import GTns_TestV.security.TokenResponse;
import GTns_TestV.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioMapper usuarioMapper;

    @Override
    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
        );

        Usuario user = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el correo: " + request.getCorreo()));

        String token = jwtService.getToken(user, user);
        return TokenResponse.builder().token(token).build();
    }

    @Override
    public TokenResponse addUsuario(UsuarioDTO usuarioDTO) {
        Usuario user = usuarioMapper.toEntity(usuarioDTO);
        usuarioRepository.save(user);

        String token = jwtService.getToken(user, user);
        return TokenResponse.builder().token(token).build();
    }

    @Override
    public Usuario getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) authentication.getPrincipal();  // Asegúrate de que la clase Usuario implemente UserDetails
    }

    // Método para crear un usuario experto, solo accesible por admin
    public Usuario crearExperto(UsuarioDTO usuarioDTO) {
        Usuario experto = usuarioMapper.toEntity(usuarioDTO);
        experto.setRole(Role.EXPERTO);  // Forzamos el rol a EXPERTO
        return usuarioRepository.save(experto);
    }
    @Override
    public void eliminarCuenta() {
        // Obtener el usuario autenticado
        Usuario usuarioActual = getAuthenticatedUser();

        // Eliminar el usuario de la base de datos
        usuarioRepository.deleteById(usuarioActual.getId());
    }

    @Override
    public Usuario actualizarPerfil(UsuarioPerfilDTO usuarioPerfilDTO) {
        // Obtener el usuario autenticado
        Usuario usuarioActual = getAuthenticatedUser();

        // Actualizar los datos del perfil
        usuarioActual.setNombre(usuarioPerfilDTO.getNombre());
        usuarioActual.setApellido(usuarioPerfilDTO.getApellido());
        usuarioActual.setTelefono(usuarioPerfilDTO.getTelefono());
        usuarioActual.setCorreo(usuarioPerfilDTO.getCorreo());

        // Guardar los cambios
        return usuarioRepository.save(usuarioActual);
    }
}
