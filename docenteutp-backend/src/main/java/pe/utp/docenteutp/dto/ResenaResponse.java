package pe.utp.docenteutp.dto;

import java.time.LocalDateTime;

public record ResenaResponse(
        Long id,
        Long docenteId,
        String docenteNombre,
        String cursoNombre,
        double calificacion,   // promedio de los 3 criterios
        int dominio,
        int claridad,
        int empatia,
        String dificultad,
        String comentario,
        String estado,         // PENDIENTE / APROBADA / RECHAZADA
        LocalDateTime fecha
) {}