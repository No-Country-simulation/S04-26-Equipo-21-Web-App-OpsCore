package com.opscore.incident.service;

import com.opscore.incident.dto.AuthResponse;
import com.opscore.incident.dto.AuthUserInfo;
import com.opscore.incident.dto.LoginRequest;
import com.opscore.incident.exception.RefreshTokenException;
import com.opscore.incident.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthCookieService cookieService;
    private final UsuarioService usuarioService;

    public AuthResponse login(LoginRequest request) {

        Usuario usuario = usuarioService.authenticate(
                request.username(),
                request.password()
        );

        String access = jwtService.generateAccessToken(usuario);
        String refresh = jwtService.generateRefreshToken(usuario);

        ResponseCookie accessCookie =
                cookieService.accessTokenCookie(access, 15);

        ResponseCookie refreshCookie =
                cookieService.refreshTokenCookie(refresh, 8);


        return new AuthResponse(
                accessCookie,
                refreshCookie,
                new AuthUserInfo(usuario.getId(), usuario.getNombre(),
                        usuario.getUsername(), usuario.getRol().name())
        );
    }

    public AuthResponse refresh(String refreshToken) {

        if (jwtService.isTokenValid(refreshToken) ||
                !jwtService.isRefreshToken(refreshToken)) {
            throw new RefreshTokenException("Invalid refresh token");
        }

        String username = jwtService.extractUsername(refreshToken);
        Usuario usuario = usuarioService.findByUsername(username);

        String newAccess = jwtService.generateAccessToken(usuario);
        ResponseCookie accessCookie =
                cookieService.accessTokenCookie(newAccess, 15);
        return new AuthResponse(
                accessCookie, null,
                new AuthUserInfo(usuario.getId(),usuario.getNombre(),
                        usuario.getUsername(), usuario.getRol().name())
        );
    }

    public ResponseEntity<Void> logout(Usuario usuario) {
        usuarioService.logout(usuario);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookieService.clearAccess().toString())
                .header(HttpHeaders.SET_COOKIE, cookieService.clearRefresh().toString())
                .build();
    }
}
