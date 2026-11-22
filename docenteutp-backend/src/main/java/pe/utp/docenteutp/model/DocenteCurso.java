package pe.utp.docenteutp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "docente_curso",
       uniqueConstraints = @UniqueConstraint(columnNames = {"docente_id", "curso_id"}))
public class DocenteCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Docente docente;

    @ManyToOne(optional = false)
    private Curso curso;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Docente getDocente() { return docente; }
    public void setDocente(Docente docente) { this.docente = docente; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
}