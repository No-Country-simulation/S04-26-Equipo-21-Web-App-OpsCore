package com.opscore.incident.dto;

public record LoginRequest(
        String username,
        String password
) {
}
