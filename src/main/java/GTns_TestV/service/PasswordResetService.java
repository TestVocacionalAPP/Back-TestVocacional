package GTns_TestV.service;

import jakarta.mail.MessagingException;

public interface PasswordResetService {

    String generateResetCode();

    void sendPasswordResetEmail(String email) throws MessagingException;

    boolean validateResetToken(String email, String token);

    void updatePassword(String email, String newPassword);
}
