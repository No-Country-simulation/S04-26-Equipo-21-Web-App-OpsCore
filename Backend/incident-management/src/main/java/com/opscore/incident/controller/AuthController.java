package com.opscore.incident.controller;

import com.opscore.incident.dto.AuthResponse;
import com.opscore.incident.dto.AuthUserInfo;
import com.opscore.incident.dto.LoginRequest;
import com.opscore.incident.model.Usuario;
import com.opscore.incident.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthUserInfo> login(@RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.accessCookie().toString())
                .header(HttpHeaders.SET_COOKIE, response.refreshCookie().toString())
                .body(response.userInfo());
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthUserInfo> refresh(
            @CookieValue("refresh_token") String refreshToken
    ) {
        AuthResponse response = authService.refresh(refreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.accessCookie().toString())
                .body(response.userInfo());
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal Usuario usuario) {
        return authService.logout(usuario);
    }
}
