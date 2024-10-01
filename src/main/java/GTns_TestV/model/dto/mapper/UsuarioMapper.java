package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.UsuarioDTO;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final PasswordEncoder passwordEncoder;

    public UsuarioMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nombre(usuarioDTO.getNombre())
                .apellido(usuarioDTO.getApellido())
                .telefono(usuarioDTO.getTelefono())
                .correo(usuarioDTO.getCorreo())
                .password(passwordEncoder.encode(usuarioDTO.getPassword())) // Codificación de la contraseña
                .role(Role.USER) // Asignación de rol por defecto
                .build();
    }
    public UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .telefono(usuario.getTelefono())
                .correo(usuario.getCorreo())
                .build();
    }
}