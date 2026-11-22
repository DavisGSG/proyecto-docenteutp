package pe.utp.docenteutp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.docenteutp.model.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Optional<Curso> findByCodigo(String codigo);

    List<Curso> findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCase(String nombre, String codigo);
}