package pe.utp.docenteutp.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.utp.docenteutp.dto.DocenteResponse;
import pe.utp.docenteutp.exception.ApiException;
import pe.utp.docenteutp.model.Docente;
import pe.utp.docenteutp.model.DocenteGuardado;
import pe.utp.docenteutp.model.Usuario;
import pe.utp.docenteutp.repository.DocenteGuardadoRepository;
import pe.utp.docenteutp.repository.DocenteRepository;
import pe.utp.docenteutp.repository.UsuarioRepository;

import java.util.List;

@Service
public class GuardadoService {

    private final DocenteGuardadoRepository guardadoRepo;
    private final UsuarioRepository usuarioRepo;
    private final DocenteRepository docenteRepo;
    private final DocenteService docenteService;

    public GuardadoService(DocenteGuardadoRepository guardadoRepo,
                           UsuarioRepository usuarioRepo,
                           DocenteRepository docenteRepo,
                           DocenteService docenteService) {
        this.guardadoRepo = guardadoRepo;
        this.usuarioRepo = usuarioRepo;
        this.docenteRepo = docenteRepo;
        this.docenteService = docenteService;
    }

    public List<DocenteResponse> listar(Long usuarioId) {
        return guardadoRepo.findByUsuarioIdOrderByFechaDesc(usuarioId).stream()
                .map(g -> docenteService.aResponse(g.getDocente()))
                .toList();
    }

    @Transactional
    public void guardar(Long usuarioId, Long docenteId) {
        // Si ya estaba guardado no hace nada (evita error al hacer doble clic)
        if (guardadoRepo.existsByUsuarioIdAndDocenteId(usuarioId, docenteId)) {
            return;
        }
        Usuario u = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Usuario no verificado"));
        Docente d = docenteRepo.findById(docenteId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Docente no encontrado"));

        DocenteGuardado g = new DocenteGuardado();
        g.setUsuario(u);
        g.setDocente(d);
        guardadoRepo.save(g);
    }

    @Transactional
    public void quitar(Long usuarioId, Long docenteId) {
        guardadoRepo.findByUsuarioIdAndDocenteId(usuarioId, docenteId)
                .ifPresent(guardadoRepo::delete);
    }
}