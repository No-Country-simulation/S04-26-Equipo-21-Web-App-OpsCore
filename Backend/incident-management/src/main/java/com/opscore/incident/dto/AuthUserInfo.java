package com.opscore.incident.dto;

public record AuthUserInfo(
        Long userId,
        String nombre,
        String username,
        String role
) {}