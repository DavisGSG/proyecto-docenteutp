package pe.utp.docenteutp.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.utp.docenteutp.dto.CursoResponse;
import pe.utp.docenteutp.dto.DocenteResponse;
import pe.utp.docenteutp.exception.ApiException;
import pe.utp.docenteutp.model.Curso;
import pe.utp.docenteutp.repository.CursoRepository;
import pe.utp.docenteutp.repository.DocenteCursoRepository;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepo;
    private final DocenteCursoRepository docenteCursoRepo;
    private final DocenteService docenteService;

    public CursoService(CursoRepository cursoRepo,
                        DocenteCursoRepository docenteCursoRepo,
                        DocenteService docenteService) {
        this.cursoRepo = cursoRepo;
        this.docenteCursoRepo = docenteCursoRepo;
        this.docenteService = docenteService;
    }

    public List<CursoResponse> listar(String q) {
        List<Curso> cursos = (q == null || q.isBlank())
                ? cursoRepo.findAll()
                : cursoRepo.findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCase(q.trim(), q.trim());

        return cursos.stream()
                .map(c -> new CursoResponse(c.getId(), c.getCodigo(), c.getNombre()))
                .toList();
    }

    // Comparador: todos los docentes que dictan el mismo curso
    public List<DocenteResponse> docentesDelCurso(Long cursoId) {
        if (!cursoRepo.existsById(cursoId)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Curso no encontrado");
        }
        return docenteCursoRepo.findByCursoId(cursoId).stream()
                .map(dc -> docenteService.aResponse(dc.getDocente()))
                .toList();
    }
}