package GTns_TestV.service;

import GTns_TestV.model.dto.usuario.UsuarioDTO;
import GTns_TestV.model.dto.usuario.UsuarioPerfilDTO;
import GTns_TestV.security.LoginRequest;
import GTns_TestV.security.TokenResponse;
import GTns_TestV.model.entity.Usuario;

public interface UsuarioService {

    TokenResponse login(LoginRequest request);

    TokenResponse addUsuario(UsuarioDTO usuarioDTO);

    Usuario getAuthenticatedUser();

    void eliminarCuenta();

    Usuario actualizarPerfil(UsuarioPerfilDTO usuarioPerfilDTO);

    UsuarioPerfilDTO listarPerfilUsuario();
}