package pe.utp.docenteutp.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.utp.docenteutp.dto.ResenaRequest;
import pe.utp.docenteutp.dto.ResenaResponse;
import pe.utp.docenteutp.exception.ApiException;
import pe.utp.docenteutp.model.Curso;
import pe.utp.docenteutp.model.Docente;
import pe.utp.docenteutp.model.EstadoResena;
import pe.utp.docenteutp.model.Resena;
import pe.utp.docenteutp.model.Usuario;
import pe.utp.docenteutp.repository.CursoRepository;
import pe.utp.docenteutp.repository.DocenteRepository;
import pe.utp.docenteutp.repository.ResenaRepository;
import pe.utp.docenteutp.repository.UsuarioRepository;

import java.util.List;

@Service
public class ResenaService {

    private final ResenaRepository resenaRepo;
    private final UsuarioRepository usuarioRepo;
    private final DocenteRepository docenteRepo;
    private final CursoRepository cursoRepo;

    public ResenaService(ResenaRepository resenaRepo, UsuarioRepository usuarioRepo,
                         DocenteRepository docenteRepo, CursoRepository cursoRepo) {
        this.resenaRepo = resenaRepo;
        this.usuarioRepo = usuarioRepo;
        this.docenteRepo = docenteRepo;
        this.cursoRepo = cursoRepo;
    }

    // Publicar: la reseña nace PENDIENTE y espera moderación
    @Transactional
    public ResenaResponse publicar(ResenaRequest req) {
        Usuario usuario = usuarioRepo.findById(req.usuarioId())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Usuario no verificado"));
        Docente docente = docenteRepo.findById(req.docenteId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Docente no encontrado"));
        Curso curso = cursoRepo.findById(req.cursoId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        if (resenaRepo.existsByUsuarioIdAndDocenteIdAndCursoId(usuario.getId(), docente.getId(), curso.getId())) {
            throw new ApiException(HttpStatus.CONFLICT, "Ya enviaste una reseña para este docente y curso");
        }

        Resena r = new Resena();
        r.setUsuario(usuario);
        r.setDocente(docente);
        r.setCurso(curso);
        r.setDominio(req.dominio());
        r.setClaridad(req.claridad());
        r.setEmpatia(req.empatia());
        r.setDificultad(req.dificultad());
        r.setComentario(req.comentario().trim());
        r.setEstado(EstadoResena.PENDIENTE);
        return aResponse(resenaRepo.save(r));
    }

    // Lo que ve el público en el perfil: solo aprobadas
    public List<ResenaResponse> listarPorDocente(Long docenteId) {
        return resenaRepo.findByDocenteIdAndEstadoOrderByFechaDesc(docenteId, EstadoResena.APROBADA)
                .stream().map(this::aResponse).toList();
    }

    // "Mis reseñas": todas las del usuario, con su estado
    public List<ResenaResponse> listarPorUsuario(Long usuarioId) {
        return resenaRepo.findByUsuarioIdOrderByFechaDesc(usuarioId)
                .stream().map(this::aResponse).toList();
    }

    @Transactional
    public void eliminar(Long id, Long usuarioId) {
        Resena r = resenaRepo.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Reseña no encontrada"));
        if (!r.getUsuario().getId().equals(usuarioId)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Solo puedes eliminar tus propias reseñas");
        }
        resenaRepo.delete(r);
    }

    // ---- Moderación (la usa ResenaAdminController) ----

    public List<ResenaResponse> listarPendientes() {
        return resenaRepo.findByEstadoOrderByFechaAsc(EstadoResena.PENDIENTE)
                .stream().map(this::aResponse).toList();
    }

    @Transactional
    public void cambiarEstado(Long id, boolean aprobar) {
        Resena r = resenaRepo.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Reseña no encontrada"));
        if (r.getEstado() != EstadoResena.PENDIENTE) {
            throw new ApiException(HttpStatus.CONFLICT, "La reseña ya fue revisada");
        }
        r.setEstado(aprobar ? EstadoResena.APROBADA : EstadoResena.RECHAZADA);
        resenaRepo.save(r);
    }

    private ResenaResponse aResponse(Resena r) {
        double promedio = (r.getDominio() + r.getClaridad() + r.getEmpatia()) / 3.0;
        return new ResenaResponse(
                r.getId(),
                r.getDocente().getId(),
                r.getDocente().getNombre(),
                r.getCurso().getNombre(),
                Math.round(promedio * 10) / 10.0,
                r.getDominio(),
                r.getClaridad(),
                r.getEmpatia(),
                r.getDificultad().name(),
                r.getComentario(),
                r.getEstado().name(),
                r.getFecha());
    }
}