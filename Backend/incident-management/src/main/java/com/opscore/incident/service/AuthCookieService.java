package com.opscore.incident.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class AuthCookieService {

    @Value( "${security.app.secure-cookie}")
    private boolean secureCookie;

    public ResponseCookie accessTokenCookie(String token, long minutes) {
        return ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(secureCookie)
                .path("/")
                .maxAge(minutes * 60)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie refreshTokenCookie(String token, long hours) {
        return ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(secureCookie)
                .path("/api/auth/refresh")
                .maxAge(hours * 3600)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie clearAccess() {
        return ResponseCookie.from("access_token", "")
                .path("/")
                .maxAge(0)
                .build();
    }

    public ResponseCookie clearRefresh() {
        return ResponseCookie.from("refresh_token", "")
                .path("/api/auth/refresh")
                .maxAge(0)
                .build();
    }
}
