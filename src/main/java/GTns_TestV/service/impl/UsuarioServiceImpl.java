package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.UsuarioRepository;
import GTns_TestV.model.dto.usuario.UsuarioPerfilDTO;
import GTns_TestV.model.dto.mapper.UsuarioMapper;
import GTns_TestV.model.dto.usuario.UsuarioDTO;
import GTns_TestV.model.dto.usuario.UsuarioUpdateDTO;
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

    @Override
    public void eliminarCuenta() {
        // Obtener el usuario autenticado
        Usuario usuarioActual = getAuthenticatedUser();

        // Eliminar el usuario de la base de datos
        usuarioRepository.deleteById(usuarioActual.getId());
    }

    @Override
    public Usuario actualizarPerfil(UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuario = getAuthenticatedUser();

        // Actualizar los campos del usuario con los datos del DTO
        usuario.setNombre(usuarioUpdateDTO.getNombre());
        usuario.setApellido(usuarioUpdateDTO.getApellido());
        usuario.setTelefono(usuarioUpdateDTO.getTelefono());
        usuario.setCorreo(usuarioUpdateDTO.getCorreo());

        // Guardar los cambios en la base de datos
        return usuarioRepository.save(usuario);
    }


    @Override
    public UsuarioPerfilDTO listarPerfilUsuario() {
        try {
            Usuario usuario = getAuthenticatedUser();
            if (usuario == null) {
                throw new RuntimeException("Usuario no encontrado");
            }
            return new UsuarioPerfilDTO(
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getTelefono(),
                    usuario.getCorreo()
            );
        } catch (Exception e) {
            // Imprime el error en los logs para depuración
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el perfil del usuario", e);
        }
    }


}
