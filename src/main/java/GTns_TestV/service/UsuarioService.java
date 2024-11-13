package GTns_TestV.service;

import GTns_TestV.model.dto.usuario.UsuarioDTO;
import GTns_TestV.model.dto.usuario.UsuarioPerfilDTO;
import GTns_TestV.model.dto.usuario.UsuarioUpdateDTO;
import GTns_TestV.security.LoginRequest;
import GTns_TestV.security.TokenResponse;
import GTns_TestV.model.entity.Usuario;

import java.util.Optional;

public interface UsuarioService {

    TokenResponse login(LoginRequest request);

    TokenResponse addUsuario(UsuarioDTO usuarioDTO);

    Usuario getAuthenticatedUser();

    void eliminarCuenta();

    Usuario actualizarPerfil(UsuarioUpdateDTO usuarioUpdateDTO);

    UsuarioPerfilDTO listarPerfilUsuario();

    Optional<Usuario> obtenerUsuarioPorId(Long usuarioId);

    Usuario actualizarImagenPerfil(String imagenBase64);
}