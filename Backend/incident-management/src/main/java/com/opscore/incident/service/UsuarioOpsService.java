package com.opscore.incident.service;

import com.opscore.incident.enums.Rol;
import com.opscore.incident.model.Usuario;
import com.opscore.incident.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioOpsService {

    private final UsuarioRepository usuarioRepository;

    // =====================================================
    // MARCAR ASISTENCIA MASIVA
    // =====================================================
    @Transactional
    public void marcarPresente(List<String> numerosReloj) {

        for (String reloj : numerosReloj) {
            usuarioRepository.findByNumeroReloj(reloj)
                    .ifPresent(usuario -> {
                        usuario.setConectado(true);
                        usuario.setDisponible(true);
                        usuarioRepository.save(usuario);
                    });
        }
    }

    // =====================================================
    // TECNICOS DISPONIBLES
    // =====================================================
    public List<Usuario> tecnicosDisponibles() {
        return usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getRol() == Rol.TECNICO)
                .filter(Usuario::isConectado)
                .filter(Usuario::isDisponible)
                .toList();
    }

    // =====================================================
    // MARCAR AUSENTE
    // =====================================================
    @Transactional
    public void marcarAusente(String numeroReloj) {

        usuarioRepository.findByNumeroReloj(numeroReloj)
                .ifPresent(usuario -> {
                    usuario.setConectado(false);
                    usuario.setDisponible(false);
                    usuarioRepository.save(usuario);
                });
    }
}