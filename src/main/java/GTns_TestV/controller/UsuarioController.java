package GTns_TestV.controller;

import GTns_TestV.model.dto.usuario.UsuarioDTO;
import GTns_TestV.model.dto.usuario.UsuarioPerfilDTO;
import GTns_TestV.model.dto.usuario.UsuarioUpdateDTO;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.security.LoginRequest;
import GTns_TestV.security.TokenResponse;
import GTns_TestV.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
             TokenResponse token = usuarioService.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody UsuarioDTO usuarioDTO) {
               TokenResponse tokenResponse = usuarioService.addUsuario(usuarioDTO);
        return ResponseEntity.ok(tokenResponse);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarCuenta() {
                usuarioService.eliminarCuenta();
        return ResponseEntity.ok("Cuenta eliminada exitosamente");
    }

    @PutMapping("/actualizar")
    public ResponseEntity<UsuarioPerfilDTO> actualizarPerfilUsuario(@Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        UsuarioPerfilDTO perfilActualizado = new UsuarioPerfilDTO(
                usuarioUpdateDTO.getNombre(),
                usuarioUpdateDTO.getApellido(),
                usuarioUpdateDTO.getTelefono(),
                usuarioUpdateDTO.getCorreo()
        );
        usuarioService.actualizarPerfil(usuarioUpdateDTO);
        return ResponseEntity.ok(perfilActualizado);
    }

    @GetMapping("/perfil")
    public UsuarioPerfilDTO obtenerPerfilUsuario() {
        return usuarioService.listarPerfilUsuario();
    }
}