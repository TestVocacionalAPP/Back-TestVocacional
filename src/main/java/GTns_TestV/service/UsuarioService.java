package GTns_TestV.service;

import GTns_TestV.model.dto.UsuarioDTO;
import GTns_TestV.security.LoginRequest;
import GTns_TestV.security.TokenResponse;
import GTns_TestV.model.entity.Usuario;

public interface UsuarioService {

    TokenResponse login(LoginRequest request);


}