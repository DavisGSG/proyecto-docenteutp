package pe.utp.docenteutp.controller;

import org.springframework.web.bind.annotation.*;
import pe.utp.docenteutp.dto.DocenteResponse;
import pe.utp.docenteutp.dto.MensajeResponse;
import pe.utp.docenteutp.service.GuardadoService;

import java.util.List;

@RestController
@RequestMapping("/api/guardados")
public class GuardadoController {

    private final GuardadoService guardadoService;

    public GuardadoController(GuardadoService guardadoService) {
        this.guardadoService = guardadoService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<DocenteResponse> listar(@PathVariable Long usuarioId) {
        return guardadoService.listar(usuarioId);
    }

    @PostMapping("/usuario/{usuarioId}/docente/{docenteId}")
    public MensajeResponse guardar(@PathVariable Long usuarioId, @PathVariable Long docenteId) {
        guardadoService.guardar(usuarioId, docenteId);
        return new MensajeResponse("Docente guardado");
    }

    @DeleteMapping("/usuario/{usuarioId}/docente/{docenteId}")
    public MensajeResponse quitar(@PathVariable Long usuarioId, @PathVariable Long docenteId) {
        guardadoService.quitar(usuarioId, docenteId);
        return new MensajeResponse("Docente quitado de guardados");
    }
}