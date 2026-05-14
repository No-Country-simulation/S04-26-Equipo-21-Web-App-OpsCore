package com.opscore.incident.service;



import com.opscore.incident.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsistenciaService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public void procesarEntradaMasiva(List<String> numerosReloj) {
        // Primero: Podríamos resetear a todos los técnicos a "desconectados"
        // si el JSON representa el total de personal presente hoy.

        for (String reloj : numerosReloj) {
            usuarioRepository.findByNumeroReloj(reloj).ifPresent(usuario -> {
                usuario.setConectado(true);
                usuario.setDisponible(true);
                usuarioRepository.save(usuario);
            });
        }
    }
}
