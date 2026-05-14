package com.opscore.incident.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void enviarAlertaATecnico(String numeroReloj, String mensaje) {
        // Enviamos el mensaje al canal privado del técnico
        messagingTemplate.convertAndSendToUser(numeroReloj, "/queue/alertas", mensaje);
    }

    public void notificarIncidenteGeneral(String mensaje) {
        // Alerta general para todos los supervisores
        messagingTemplate.convertAndSend("/topic/incidentes", mensaje);
    }
    public void enviarNotificacionAsignacion(String numeroReloj, String mensaje) {
        // Envía el mensaje al canal privado del técnico basado en su número de reloj
        messagingTemplate.convertAndSendToUser(
                numeroReloj,
                "/queue/incidentes",
                mensaje
        );
    }
}
