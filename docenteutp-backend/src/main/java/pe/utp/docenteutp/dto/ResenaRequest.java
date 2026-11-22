package pe.utp.docenteutp.dto;

import jakarta.validation.constraints.*;
import pe.utp.docenteutp.model.NivelDificultad;

public record ResenaRequest(
        @NotNull Long usuarioId,
        @NotNull Long docenteId,
        @NotNull Long cursoId,
        @Min(1) @Max(5) int dominio,
        @Min(1) @Max(5) int claridad,
        @Min(1) @Max(5) int empatia,
        @NotNull NivelDificultad dificultad,
        @NotBlank @Size(min = 30, max = 500) String comentario
) {}