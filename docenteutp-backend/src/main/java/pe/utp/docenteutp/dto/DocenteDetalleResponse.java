package pe.utp.docenteutp.dto;

import java.util.List;

public record DocenteDetalleResponse(
        Long id,
        String nombre,
        String facultad,
        String sede,
        boolean verificado,
        double calificacion,
        long totalResenas,
        List<CriterioPromedioResponse> criterios,
        List<CursoResponse> cursos,
        String nivelDificultad,     // el más frecuente entre las reseñas
        String estiloEvaluacion,    // "Prácticas y exámenes"
        String resumen,             // bloque "En pocas palabras"
        List<String> etiquetas      // "Metodología clara", "Práctica semanal"
) {}