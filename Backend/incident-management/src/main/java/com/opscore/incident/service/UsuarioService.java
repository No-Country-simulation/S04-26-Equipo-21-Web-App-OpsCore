package com.opscore.incident.service;

import com.opscore.incident.exception.UsuarioNoEncontradoException;
import com.opscore.incident.model.Usuario;
import com.opscore.incident.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public Usuario authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        Usuario usuario = (Usuario) authentication.getPrincipal();
        if (usuario != null) {
            usuario.setConectado(true);
            usuario.setDisponible(true);
            usuarioRepository.save(usuario);
            return usuario;
        }
        throw new UsuarioNoEncontradoException("Usuario no encontrado con el username: " + username);
    }

    public Usuario findByUsername(String username) {
        return usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con el username: " + username));
    }

    public Usuario crearUsuario(Usuario usuario) {
        return null;
    }

    public Optional<Usuario> buscarPorNumeroReloj(String numeroReloj) {
        return null;
    }

    public List<Usuario> listarUsuarios() {
        return null;
    }

    public void logout(Usuario usuario) {
        usuario.setConectado(false);
        usuario.setDisponible(false);
        usuarioRepository.save(usuario);
    }
}
