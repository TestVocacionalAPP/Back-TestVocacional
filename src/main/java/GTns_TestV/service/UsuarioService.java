package GTns_TestV.service;

import GTns_TestV.model.dto.UsuarioDTO;
import GTns_TestV.model.dto.UsuarioPerfilDTO;
import GTns_TestV.security.LoginRequest;
import GTns_TestV.security.TokenResponse;
import GTns_TestV.model.entity.Usuario;

public interface UsuarioService {

    TokenResponse login(LoginRequest request);

    TokenResponse addUsuario(UsuarioDTO usuarioDTO);

    Usuario getAuthenticatedUser();

    Usuario crearExperto(UsuarioDTO usuarioDTO);

    void eliminarCuenta();

    Usuario actualizarPerfil(UsuarioPerfilDTO usuarioPerfilDTO);
}