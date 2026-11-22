package pe.utp.docenteutp.dto;

public record DocenteResponse(
        Long id,
        String nombre,
        String facultad,
        String cursoPrincipal,
        double calificacion,        // promedio de reseñas aprobadas
        long totalResenas,          // solo aprobadas
        String estiloEnsenanza,     // etiqueta: "Explicación clara", "Evaluación continua"
        boolean verificado
) {}