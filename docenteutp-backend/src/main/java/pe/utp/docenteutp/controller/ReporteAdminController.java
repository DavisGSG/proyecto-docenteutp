package pe.utp.docenteutp.controller;

import org.springframework.web.bind.annotation.*;
import pe.utp.docenteutp.dto.ReporteDocenteResponse;
import pe.utp.docenteutp.service.ReporteService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reportes")
public class ReporteAdminController {

    private final ReporteService reporteService;

    public ReporteAdminController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    // Reporte agregado para coordinaciones: promedio y cantidad de reseñas por docente
    @GetMapping("/docentes")
    public List<ReporteDocenteResponse> reporteDocentes() {
        return reporteService.reportePorDocente();
    }
}