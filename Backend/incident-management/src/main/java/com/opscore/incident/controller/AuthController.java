///package com.opscore.incident.controller;
///
///import com.opscore.incident.dto.AuthResponse;
///import com.opscore.incident.dto.AuthUserInfo;
///import com.opscore.incident.dto.LoginRequest;
///import com.opscore.incident.model.Usuario;
///import com.opscore.incident.service.AuthService;
///import io.swagger.v3.oas.annotations.Operation;
///import io.swagger.v3.oas.annotations.responses.ApiResponse;
///import io.swagger.v3.oas.annotations.tags.Tag;
///import io.swagger.v3.oas.annotations.security.SecurityRequirement;
///import org.springframework.http.HttpHeaders;
///import org.springframework.http.ResponseEntity;
///import org.springframework.security.access.prepost.PreAuthorize;
///import org.springframework.security.core.annotation.AuthenticationPrincipal;
///import org.springframework.web.bind.annotation.*;
///
///@RestController
///@RequestMapping("/api/auth")
///@Tag(name = "Autenticación", description = "Gestión de login, refresh de sesión y logout de usuarios en OpsCore")
///public class AuthController {
///
///    private final AuthService authService;
///
///    public AuthController(AuthService authService) {
///        this.authService = authService;
///    }
///
///    @Operation(
///            summary = "Iniciar sesión",
///            description = """
///                    Autentica un usuario en el sistema OpsCore.
///
///                    Si las credenciales son válidas:
///                    - Se genera un Access Token (JWT) en cookie HTTP-only.
///                    - Se genera un Refresh Token en cookie HTTP-only.
///                    - Se retorna la información básica del usuario autenticado.
///                    """
///    )
///    @ApiResponse(responseCode = "200", description = "Login exitoso")
///    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
///    @PostMapping("/login")
///    public ResponseEntity<AuthUserInfo> login(@RequestBody LoginRequest request) {
///
///        AuthResponse response = authService.login(request);
///
///        return ResponseEntity.ok()
///                .header(HttpHeaders.SET_COOKIE, response.accessCookie().toString())
///                .header(HttpHeaders.SET_COOKIE, response.refreshCookie().toString())
///                .body(response.userInfo());
///    }
///
///    @Operation(
///            summary = "Renovar sesión",
///            description = """
///                    Renueva el Access Token usando el Refresh Token almacenado en cookie HTTP-only.
///
///                    No requiere enviar credenciales en el body.
///                    """
///    )
///    @ApiResponse(responseCode = "200", description = "Token renovado correctamente")
///    @ApiResponse(responseCode = "401", description = "Refresh token inválido o expirado")
///    @PostMapping("/refresh")
///    public ResponseEntity<AuthUserInfo> refresh(
///            @CookieValue("refresh_token") String refreshToken
///    ) {
///        AuthResponse response = authService.refresh(refreshToken);
///
///        return ResponseEntity.ok()
///                .header(HttpHeaders.SET_COOKIE, response.accessCookie().toString())
///                .body(response.userInfo());
///    }
///
///    @Operation(
///            summary = "Cerrar sesión",
///            description = """
///                    Cierra la sesión del usuario autenticado.
///
///                    Elimina las cookies:
///                    - access_token
///                    - refresh_token
///
///                    Requiere autenticación activa.
///                    """
///    )
///    @ApiResponse(responseCode = "200", description = "Logout exitoso")
///    @ApiResponse(responseCode = "401", description = "Usuario no autenticado")
///    @SecurityRequirement(name = "accessCookieAuth")
///    @PostMapping("/logout")
///    @PreAuthorize("isAuthenticated()")
///    public ResponseEntity<Void> logout(@AuthenticationPrincipal Usuario usuario) {
///        return authService.logout(usuario);
///    }
///}