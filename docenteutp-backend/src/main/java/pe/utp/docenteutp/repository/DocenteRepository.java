package pe.utp.docenteutp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.utp.docenteutp.model.Docente;

import java.util.List;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    // Busca por nombre del docente, nombre del curso o código del curso.
    // IMPORTANTE: el service debe mandar "" (vacío) en lugar de null.
    @Query("""
           select distinct d from Docente d
           left join DocenteCurso dc on dc.docente = d
           left join dc.curso c
           where (lower(d.nombre) like lower(concat('%', :q, '%'))
                  or lower(c.nombre) like lower(concat('%', :q, '%'))
                  or lower(c.codigo) like lower(concat('%', :q, '%')))
             and (:estilo = '' or d.estiloEnsenanza = :estilo)
           order by d.nombre
           """)
    List<Docente> buscar(@Param("q") String q, @Param("estilo") String estilo);
}