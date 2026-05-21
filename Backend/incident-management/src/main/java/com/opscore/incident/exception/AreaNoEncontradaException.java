package com.opscore.incident.exception;

public class AreaNoEncontradaException extends RuntimeException {
    public AreaNoEncontradaException(Long id) {
        super("No se encontró el área con id: " + id);
    }
}
