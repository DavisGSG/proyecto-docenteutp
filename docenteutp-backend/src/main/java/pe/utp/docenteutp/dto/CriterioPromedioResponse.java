package pe.utp.docenteutp.dto;

public record CriterioPromedioResponse(
        String criterio,    // Dominio del tema, Claridad de explicación, Empatía y accesibilidad
        double promedio
) {}