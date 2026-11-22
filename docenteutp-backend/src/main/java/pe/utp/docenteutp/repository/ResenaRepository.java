package pe.utp.docenteutp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.utp.docenteutp.model.EstadoResena;
import pe.utp.docenteutp.model.Resena;

import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Long> {

    List<Resena> findByDocenteIdAndEstadoOrderByFechaDesc(Long docenteId, EstadoResena estado);

    List<Resena> findByUsuarioIdOrderByFechaDesc(Long usuarioId);

    List<Resena> findByEstadoOrderByFechaAsc(EstadoResena estado);

    boolean existsByUsuarioIdAndDocenteIdAndCursoId(Long usuarioId, Long docenteId, Long cursoId);

    long countByDocenteIdAndEstado(Long docenteId, EstadoResena estado);

    // Promedios por criterio: SOLO reseñas aprobadas.
    // Devuelve una fila: [promDominio, promClaridad, promEmpatia, total]
    // Si no hay reseñas, los promedios vienen null (el service debe manejarlo).
    @Query("""
           select avg(r.dominio), avg(r.claridad), avg(r.empatia), count(r)
           from Resena r
           where r.docente.id = :docenteId
             and r.estado = pe.utp.docenteutp.model.EstadoResena.APROBADA
           """)
    List<Object[]> promediosAprobadas(@Param("docenteId") Long docenteId);

    // Nivel de dificultad más frecuente: la primera fila es el ganador.
    // Cada fila: [NivelDificultad, cantidad]
    @Query("""
           select r.dificultad, count(r)
           from Resena r
           where r.docente.id = :docenteId
             and r.estado = pe.utp.docenteutp.model.EstadoResena.APROBADA
           group by r.dificultad
           order by count(r) desc
           """)
    List<Object[]> dificultadesFrecuentes(@Param("docenteId") Long docenteId);
}