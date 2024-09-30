package GTns_TestV.service;

import GTns_TestV.infra.repository.UsuarioRepository;
import GTns_TestV.model.dto.mapper.UsuarioMapper;
import GTns_TestV.model.dto.UsuarioDTO;
import GTns_TestV.model.entity.Usuario;
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


}
