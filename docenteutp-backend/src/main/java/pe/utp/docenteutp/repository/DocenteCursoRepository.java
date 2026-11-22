package pe.utp.docenteutp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.docenteutp.model.DocenteCurso;

import java.util.List;

public interface DocenteCursoRepository extends JpaRepository<DocenteCurso, Long> {

    // Cursos que dicta un docente (perfil)
    List<DocenteCurso> findByDocenteId(Long docenteId);

    // Docentes que dictan un curso (comparador)
    List<DocenteCurso> findByCursoId(Long cursoId);
}