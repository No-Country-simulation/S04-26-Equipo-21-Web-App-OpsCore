package com.opscore.incident.service;

import com.opscore.incident.dto.UsuarioDTO;
import com.opscore.incident.model.Usuario;
import com.opscore.incident.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorNumeroReloj(String numeroReloj) {
        return usuarioRepository.findByNumeroReloj(numeroReloj);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(u -> new UsuarioDTO(
                        u.getId(),
                        u.getNombre(),
                        u.getUsername(),
                        u.getNumeroReloj(),
                        u.getRol(),
                        u.isConectado(),
                        u.isDisponible(),
                        u.getArea() != null ? u.getArea().getNombre() : null
                ))
                .toList();
    }
}
