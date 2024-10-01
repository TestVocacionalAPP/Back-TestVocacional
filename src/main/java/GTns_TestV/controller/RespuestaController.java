package GTns_TestV.controller;

import GTns_TestV.model.entity.Usuario;
import GTns_TestV.service.RespuestaService;
import GTns_TestV.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para calcular la fila con mayor respuestas usando el usuario autenticado desde el token
    @GetMapping("/calcular-chaside")
    public ResponseEntity<Map<String, Object>> calcularChaside() {
        try {
            // Obtener el usuario autenticado desde el token
            Usuario usuario = usuarioService.getAuthenticatedUser();

            // Llamar al servicio para calcular los resultados del método Chaside para el usuario autenticado
            Map<String, Object> resultado = respuestaService.calcularFilaConMayorRespuestas(usuario.getId());
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
