package GTns_TestV.service.impl;

import GTns_TestV.infra.email.PasswordResetToken;
import GTns_TestV.infra.repository.PasswordResetTokenRepository;
import GTns_TestV.infra.repository.UsuarioRepository;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.service.EmailService;
import GTns_TestV.service.PasswordResetService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String generateResetCode() {
        Random random = new Random();
        int resetCode = 100000 + random.nextInt(900000);
        return String.valueOf(resetCode);
    }

    @Override
    @Transactional
    public void sendPasswordResetEmail(String email) throws MessagingException {
        String resetCode = generateResetCode();

        // Elimina tokens antiguos
        tokenRepository.deleteByEmail(email);

        PasswordResetToken token = new PasswordResetToken();
        token.setEmail(email);
        token.setToken(resetCode);
        token.setExpiryDate(LocalDateTime.now().plusHours(1)); // Token válido por 1 hora
        tokenRepository.save(token);

        String subject = "Código de Restablecimiento de Contraseña";
        String text = "Tu código de restablecimiento de contraseña es: " + resetCode;
        emailService.sendEmail(email, subject, text);
    }

    @Override
    public boolean validateResetToken(String email, String token) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByEmail(email);
        return resetToken.isPresent() && resetToken.get().getToken().equals(token) &&
                resetToken.get().getExpiryDate().isAfter(LocalDateTime.now());
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        Optional<Usuario> user = usuarioRepository.findByCorreo(email);
        if (user.isPresent()) {
            Usuario usuario = user.get();
            usuario.setPassword(passwordEncoder.encode(newPassword));
            usuarioRepository.save(usuario);
        }
    }
}
