package pe.utp.docenteutp.controller;

import org.springframework.web.bind.annotation.*;
import pe.utp.docenteutp.dto.CursoResponse;
import pe.utp.docenteutp.dto.DocenteResponse;
import pe.utp.docenteutp.service.CursoService;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<CursoResponse> listar(@RequestParam(required = false) String q) {
        return cursoService.listar(q);
    }

    // Comparador: docentes que dictan el mismo curso
    @GetMapping("/{id}/docentes")
    public List<DocenteResponse> docentesDelCurso(@PathVariable Long id) {
        return cursoService.docentesDelCurso(id);
    }
}