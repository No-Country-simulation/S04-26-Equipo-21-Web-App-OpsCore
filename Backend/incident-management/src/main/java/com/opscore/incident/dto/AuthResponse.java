package com.opscore.incident.dto;

import org.springframework.http.ResponseCookie;

public record AuthResponse(
        ResponseCookie accessCookie,
        ResponseCookie refreshCookie,
        AuthUserInfo userInfo
) {}
