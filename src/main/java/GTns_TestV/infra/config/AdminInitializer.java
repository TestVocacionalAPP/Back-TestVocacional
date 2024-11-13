package GTns_TestV.infra.config;

import GTns_TestV.infra.repository.UsuarioRepository;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initializeAdmin() {
        return args -> {
            // Verifica si ya existe un usuario con rol ADMIN
            if (!usuarioRepository.existsByCorreo("admin@gmail.com")) {
                Usuario admin = Usuario.builder()
                        .nombre("Admin")
                        .apellido("Admin")
                        .telefono("123456789")
                        .correo("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))  // Encripta la contraseña
                        .role(Role.ADMIN)
                        .build();

                usuarioRepository.save(admin);
                System.out.println("Usuario administrador creado por defecto");
            } else {
                System.out.println("Usuario administrador ya existente, no se creó uno nuevo.");
            }
        };
    }
}
