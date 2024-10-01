package GTns_TestV.controller;


import GTns_TestV.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para manejar el envío de correos electrónicos.
 */
@RestController
@RequestMapping("/email")
@CrossOrigin("*") // Permite solicitudes de cualquier origen
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        try {
            emailService.sendEmail(to, subject, text);
            return ResponseEntity.ok("Email enviado exitosamente");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Error al enviar el email: " + e.getMessage());
        }
    }
}