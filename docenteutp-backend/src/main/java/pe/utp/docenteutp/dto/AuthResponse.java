package pe.utp.docenteutp.dto;

public record AuthResponse(
        Long id,
        String nombre,
        String correo,
        String carrera,
        int ciclo,
        String rol          // ESTUDIANTE o ADMIN
) {}