package pe.utp.docenteutp.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.utp.docenteutp.dto.MensajeResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Errores que lanzamos nosotros desde los services
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<MensajeResponse> manejarApi(ApiException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new MensajeResponse(ex.getMessage()));
    }

    // Fallas de @Valid: correo que no es @utp.edu.pe, comentario de menos de 30 caracteres, etc.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensajeResponse> manejarValidacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(e -> e.getDefaultMessage())
                .orElse("Datos inválidos");
        return ResponseEntity.badRequest().body(new MensajeResponse(mensaje));
    }

    // Respaldo de la base de datos: correo repetido o reseña duplicada
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MensajeResponse> manejarIntegridad(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new MensajeResponse("Ese registro ya existe"));
    }

    // Cualquier otro error inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensajeResponse> manejarGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MensajeResponse("Error interno del servidor"));
    }
}