package com.opscore.incident.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Habilita un broker de mensajes simple en memoria
        // /topic para mensajes masivos (ej: anuncios de planta)
        // /queue para mensajes privados (ej: asignación a un técnico específico)
        config.enableSimpleBroker("/topic", "/queue");

        // Prefijo para los mensajes que el cliente envía al servidor
        config.setApplicationDestinationPrefixes("/app");

        // Prefijo para destinos de usuario específicos (notificaciones privadas)
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Punto de entrada para la conexión inicial del cliente (Handshake)
        // Se permite el acceso desde cualquier origen para pruebas (setAllowedOriginPatterns)
        registry.addEndpoint("/ws-opscore")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // SockJS es un fallback si WebSocket no es soportado
    }
}
