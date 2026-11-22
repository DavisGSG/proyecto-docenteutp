package pe.utp.docenteutp.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.utp.docenteutp.dto.CriterioPromedioResponse;
import pe.utp.docenteutp.dto.CursoResponse;
import pe.utp.docenteutp.dto.DocenteDetalleResponse;
import pe.utp.docenteutp.dto.DocenteResponse;
import pe.utp.docenteutp.exception.ApiException;
import pe.utp.docenteutp.model.Docente;
import pe.utp.docenteutp.repository.DocenteCursoRepository;
import pe.utp.docenteutp.repository.DocenteRepository;
import pe.utp.docenteutp.repository.ResenaRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class DocenteService {

    // Promedios de reseñas APROBADAS de un docente
    public record Estadisticas(double dominio, double claridad, double empatia, long total) {
        public double general() {
            return total == 0 ? 0.0 : redondear((dominio + claridad + empatia) / 3.0);
        }
    }

    private final DocenteRepository docenteRepo;
    private final DocenteCursoRepository docenteCursoRepo;
    private final ResenaRepository resenaRepo;

    public DocenteService(DocenteRepository docenteRepo,
                          DocenteCursoRepository docenteCursoRepo,
                          ResenaRepository resenaRepo) {
        this.docenteRepo = docenteRepo;
        this.docenteCursoRepo = docenteCursoRepo;
        this.resenaRepo = resenaRepo;
    }

    public List<DocenteResponse> buscar(String q, String estilo, String dificultad) {
        String texto = q == null ? "" : q.trim();
        String est = estilo == null ? "" : estilo.trim();

        List<Docente> docentes = docenteRepo.buscar(texto, est);

        // Filtro por dificultad: se compara con el nivel más frecuente en reseñas aprobadas
        if (dificultad != null && !dificultad.isBlank()) {
            docentes = docentes.stream()
                    .filter(d -> dificultadMasFrecuente(d.getId())
                            .map(n -> n.equalsIgnoreCase(dificultad.trim()))
                            .orElse(false))
                    .toList();
        }
        return docentes.stream().map(this::aResponse).toList();
    }

    public DocenteDetalleResponse obtenerDetalle(Long id) {
        Docente d = docenteRepo.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Docente no encontrado"));

        Estadisticas est = estadisticas(id);

        List<CriterioPromedioResponse> criterios = List.of(
                new CriterioPromedioResponse("Dominio del tema", redondear(est.dominio())),
                new CriterioPromedioResponse("Claridad de explicación", redondear(est.claridad())),
                new CriterioPromedioResponse("Empatía y accesibilidad", redondear(est.empatia()))
        );

        List<CursoResponse> cursos = docenteCursoRepo.findByDocenteId(id).stream()
                .map(dc -> new CursoResponse(dc.getCurso().getId(),
                        dc.getCurso().getCodigo(), dc.getCurso().getNombre()))
                .toList();

        String nivel = dificultadMasFrecuente(id).map(this::capitalizar).orElse("Sin datos");

        return new DocenteDetalleResponse(
                d.getId(), d.getNombre(), d.getFacultad(), d.getSede(), d.isVerificado(),
                est.general(), est.total(), criterios, cursos,
                nivel, d.getEstiloEvaluacion(), d.getResumen(), separarEtiquetas(d.getEtiquetas()));
    }

    // ---- Métodos reutilizados por otros services ----

    public DocenteResponse aResponse(Docente d) {
        Estadisticas est = estadisticas(d.getId());
        String cursoPrincipal = docenteCursoRepo.findByDocenteId(d.getId()).stream()
                .findFirst()
                .map(dc -> dc.getCurso().getNombre())
                .orElse("Sin curso asignado");

        return new DocenteResponse(d.getId(), d.getNombre(), d.getFacultad(), cursoPrincipal,
                est.general(), est.total(), d.getEstiloEnsenanza(), d.isVerificado());
    }

    public Estadisticas estadisticas(Long docenteId) {
        List<Object[]> filas = resenaRepo.promediosAprobadas(docenteId);
        if (filas.isEmpty()) {
            return new Estadisticas(0, 0, 0, 0);
        }
        Object[] f = filas.get(0);
        long total = f[3] == null ? 0 : ((Number) f[3]).longValue();
        return new Estadisticas(numero(f[0]), numero(f[1]), numero(f[2]), total);
    }

    // ---- Auxiliares ----

    private java.util.Optional<String> dificultadMasFrecuente(Long docenteId) {
        List<Object[]> filas = resenaRepo.dificultadesFrecuentes(docenteId);
        if (filas.isEmpty()) {
            return java.util.Optional.empty();
        }
        return java.util.Optional.of(((Enum<?>) filas.get(0)[0]).name());
    }

    private List<String> separarEtiquetas(String etiquetas) {
        if (etiquetas == null || etiquetas.isBlank()) {
            return List.of();
        }
        return Arrays.stream(etiquetas.split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList();
    }

    private String capitalizar(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    private static double numero(Object o) {
        return o == null ? 0.0 : ((Number) o).doubleValue();
    }

    private static double redondear(double v) {
        return Math.round(v * 10) / 10.0;
    }
}