package com.opscore.incident.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addServersItem(devServer())
                .addServersItem(prodServer())
                .components(
                        new Components()
                                .addSecuritySchemes("accessCookieAuth", accessCookieScheme())
                                .addSecuritySchemes("refreshCookieAuth", refreshCookieScheme())
                );
    }

    private SecurityScheme accessCookieScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("access_token")
                .description("JWT de acceso almacenado en cookie HTTP-only.");
    }

    private SecurityScheme refreshCookieScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("refresh_token")
                .description("JWT de refresco almacenado en cookie HTTP-only.");
    }

    private Server devServer() {
        return new Server()
                .url("http://localhost:8081")
                .description("Servidor de desarrollo local");
    }

    private Server prodServer() {
        return new Server()
                .url("http://localhost:9090")
                .description("Servidor de desarrollo local");
    }

    private Info apiInfo() {
        return new Info()
                .title("OpsCore API")
                .version("1.0.0")
                .description("""
                        OpsCore API es un servicio backend diseñado para gestionar
                        incidentes operativos dentro de una planta industrial,
                        permitiendo reportar fallos, asignar responsables,
                        monitorear el progreso de resolución y analizar causas raíz
                        para reducir la recurrencia de incidentes críticos.
                        
                        Funcionalidades principales:
                        - Registro de incidentes desde dispositivos móviles.
                        - Seguimiento del estado de incidentes (abierto, en proceso y cerrado).
                        - Asignación de responsables técnicos por parte de supervisores.
                        - Registro del tiempo de respuesta y resolución.
                        - Análisis de causas raíz por tipo de incidente y área.
                        - Métricas operativas para supervisión y toma de decisiones.
                        
                        Flujo principal:
                        1. Un operador detecta una falla o incidente.
                        2. Reporta el incidente desde un formulario móvil.
                        3. Un supervisor recibe la alerta y asigna un responsable.
                        4. El técnico resuelve el incidente y documenta la solución aplicada.
                        5. El sistema registra tiempos y genera trazabilidad.
                        6. Gerencia analiza patrones recurrentes y causas raíz.
                        
                        Seguridad:
                        La autenticación se implementa mediante JWT usando:
                        - Access Token almacenado en cookie HTTP-only (`access_token`).
                        - Refresh Token almacenado en cookie HTTP-only (`refresh_token`).
                        
                        Esto permite mantener sesiones seguras minimizando
                        la exposición de tokens al navegador.
                        """)
                .contact(new Contact()
                        .name("Equipo OpsCore")
                        .email("support@opscore.local"));
    }
}
