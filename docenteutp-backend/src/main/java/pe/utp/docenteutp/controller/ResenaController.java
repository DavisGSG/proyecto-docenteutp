package pe.utp.docenteutp.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.utp.docenteutp.dto.MensajeResponse;
import pe.utp.docenteutp.dto.ResenaRequest;
import pe.utp.docenteutp.dto.ResenaResponse;
import pe.utp.docenteutp.service.ResenaService;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    // Publicar reseña (el request incluye usuarioId, docenteId y cursoId)
    @PostMapping
    public ResponseEntity<ResenaResponse> publicar(@Valid @RequestBody ResenaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaService.publicar(request));
    }

    @GetMapping("/docente/{docenteId}")
    public List<ResenaResponse> porDocente(@PathVariable Long docenteId) {
        return resenaService.listarPorDocente(docenteId);
    }

    // "Mis reseñas"
    @GetMapping("/usuario/{usuarioId}")
    public List<ResenaResponse> porUsuario(@PathVariable Long usuarioId) {
        return resenaService.listarPorUsuario(usuarioId);
    }

    @DeleteMapping("/{id}")
    public MensajeResponse eliminar(@PathVariable Long id, @RequestParam Long usuarioId) {
        resenaService.eliminar(id, usuarioId);
        return new MensajeResponse("Reseña eliminada");
    }
}