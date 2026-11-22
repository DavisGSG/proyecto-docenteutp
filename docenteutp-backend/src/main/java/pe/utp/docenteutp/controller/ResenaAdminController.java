package pe.utp.docenteutp.controller;

import org.springframework.web.bind.annotation.*;
import pe.utp.docenteutp.dto.MensajeResponse;
import pe.utp.docenteutp.dto.ResenaResponse;
import pe.utp.docenteutp.service.ResenaService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/resenas")
public class ResenaAdminController {

    private final ResenaService resenaService;

    public ResenaAdminController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @GetMapping("/pendientes")
    public List<ResenaResponse> pendientes() {
        return resenaService.listarPendientes();
    }

    @PutMapping("/{id}/aprobar")
    public MensajeResponse aprobar(@PathVariable Long id) {
        resenaService.cambiarEstado(id, true);
        return new MensajeResponse("Reseña aprobada");
    }

    @PutMapping("/{id}/rechazar")
    public MensajeResponse rechazar(@PathVariable Long id) {
        resenaService.cambiarEstado(id, false);
        return new MensajeResponse("Reseña rechazada");
    }
}