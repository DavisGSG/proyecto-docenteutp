package pe.utp.docenteutp.service;

import org.springframework.stereotype.Service;
import pe.utp.docenteutp.dto.ReporteDocenteResponse;
import pe.utp.docenteutp.model.EstadoResena;
import pe.utp.docenteutp.repository.DocenteRepository;
import pe.utp.docenteutp.repository.ResenaRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class ReporteService {

    private final DocenteRepository docenteRepo;
    private final ResenaRepository resenaRepo;
    private final DocenteService docenteService;

    public ReporteService(DocenteRepository docenteRepo,
                          ResenaRepository resenaRepo,
                          DocenteService docenteService) {
        this.docenteRepo = docenteRepo;
        this.resenaRepo = resenaRepo;
        this.docenteService = docenteService;
    }

    // Reporte agregado para coordinaciones, ordenado de mayor a menor calificación
    public List<ReporteDocenteResponse> reportePorDocente() {
        return docenteRepo.findAll().stream()
                .map(d -> {
                    DocenteService.Estadisticas est = docenteService.estadisticas(d.getId());
                    long pendientes = resenaRepo.countByDocenteIdAndEstado(d.getId(), EstadoResena.PENDIENTE);
                    return new ReporteDocenteResponse(
                            d.getId(), d.getNombre(), d.getFacultad(),
                            est.general(),
                            redondear(est.dominio()), redondear(est.claridad()), redondear(est.empatia()),
                            est.total(), pendientes);
                })
                .sorted(Comparator.comparingDouble(ReporteDocenteResponse::promedioGeneral).reversed())
                .toList();
    }

    private double redondear(double v) {
        return Math.round(v * 10) / 10.0;
    }
}