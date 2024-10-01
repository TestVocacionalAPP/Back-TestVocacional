package GTns_TestV.controller;

import GTns_TestV.model.dto.UsuarioDTO;
import GTns_TestV.security.LoginRequest;
import GTns_TestV.security.TokenResponse;
import GTns_TestV.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Marca esta clase como un controlador REST de Spring
@RequestMapping("/usuarios") // Define la ruta base para las solicitudes manejadas por este controlador
@RequiredArgsConstructor // Lombok genera un constructor con los campos final
public class UsuarioController {

    private final UsuarioService usuarioService; // Inyección de la dependencia del servicio de usuario

    /**
     * Método para iniciar sesión.
     * @param request Objeto que contiene las credenciales de inicio de sesión
     * @return ResponseEntity con el token JWT generado
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        // Llama al servicio de usuario para manejar el inicio de sesión
        TokenResponse token = usuarioService.login(request);
        return ResponseEntity.ok(token); // Devuelve el token en la respuesta
    }

    /**
     * Método para registrar un nuevo usuario.
     * @param usuarioDTO Objeto con los datos del nuevo usuario
     * @return ResponseEntity con el token JWT generado
     */
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody UsuarioDTO usuarioDTO) {
        // Llama al servicio de usuario para manejar el registro de un nuevo usuario
        TokenResponse tokenResponse = usuarioService.addUsuario(usuarioDTO);
        return ResponseEntity.ok(tokenResponse); // Devuelve el token en la respuesta
    }
}