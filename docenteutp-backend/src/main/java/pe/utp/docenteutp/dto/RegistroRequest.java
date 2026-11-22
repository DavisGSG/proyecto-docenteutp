package pe.utp.docenteutp.dto;

import jakarta.validation.constraints.*;

public record RegistroRequest(
        @NotBlank String nombre,
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@utp\\.edu\\.pe$",
                 message = "Debes usar tu correo institucional @utp.edu.pe")
        String correo,
        @NotBlank @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,
        @NotBlank String carrera,
        @Min(1) @Max(12) int ciclo
) {}