package pe.utp.docenteutp.dto;

public record ReporteDocenteResponse(
        Long docenteId,
        String docenteNombre,
        String facultad,
        double promedioGeneral,
        double promedioDominio,
        double promedioClaridad,
        double promedioEmpatia,
        long totalResenas,          // aprobadas
        long resenasPendientes      // en espera de moderación
) {}