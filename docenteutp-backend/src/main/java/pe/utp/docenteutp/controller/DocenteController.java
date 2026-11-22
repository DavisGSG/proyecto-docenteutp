package pe.utp.docenteutp.controller;

import org.springframework.web.bind.annotation.*;
import pe.utp.docenteutp.dto.DocenteDetalleResponse;
import pe.utp.docenteutp.dto.DocenteResponse;
import pe.utp.docenteutp.service.DocenteService;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    private final DocenteService docenteService;

    public DocenteController(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    // Buscar por nombre de docente, nombre o código de curso.
    // Filtros opcionales: ?q=carlos&estilo=Explicación clara&dificultad=MODERADO
    @GetMapping
    public List<DocenteResponse> buscar(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String estilo,
            @RequestParam(required = false) String dificultad) {
        return docenteService.buscar(q, estilo, dificultad);
    }

    // Perfil completo: calificación por criterios, curso, resumen
    @GetMapping("/{id}")
    public DocenteDetalleResponse obtenerPerfil(@PathVariable Long id) {
        return docenteService.obtenerDetalle(id);
    }
}