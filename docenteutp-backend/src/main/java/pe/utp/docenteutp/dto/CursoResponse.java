package pe.utp.docenteutp.dto;

public record CursoResponse(
        Long id,
        String codigo,      // ej. MAT-101
        String nombre       // ej. Algoritmos y Estructuras de Datos
) {}