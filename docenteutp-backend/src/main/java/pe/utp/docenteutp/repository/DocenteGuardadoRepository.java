package pe.utp.docenteutp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.docenteutp.model.DocenteGuardado;

import java.util.List;
import java.util.Optional;

public interface DocenteGuardadoRepository extends JpaRepository<DocenteGuardado, Long> {

    List<DocenteGuardado> findByUsuarioIdOrderByFechaDesc(Long usuarioId);

    Optional<DocenteGuardado> findByUsuarioIdAndDocenteId(Long usuarioId, Long docenteId);

    boolean existsByUsuarioIdAndDocenteId(Long usuarioId, Long docenteId);
}