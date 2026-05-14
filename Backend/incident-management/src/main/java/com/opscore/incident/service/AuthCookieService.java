package com.opscore.incident.service;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class AuthCookieService {

    public ResponseCookie accessTokenCookie(String token, long minutes) {
        return ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(minutes * 60)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie refreshTokenCookie(String token, long hours) {
        return ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(true)
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
