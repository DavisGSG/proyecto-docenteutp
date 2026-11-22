package pe.utp.docenteutp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resenas",
       uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "docente_id", "curso_id"}))
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) private Usuario usuario;
    @ManyToOne(optional = false) private Docente docente;
    @ManyToOne(optional = false) private Curso curso;

    private int dominio;
    private int claridad;
    private int empatia;

    @Enumerated(EnumType.STRING)
    private NivelDificultad dificultad;

    @Column(length = 500)
    private String comentario;

    @Enumerated(EnumType.STRING)
    private EstadoResena estado = EstadoResena.PENDIENTE;

    private LocalDateTime fecha = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Docente getDocente() { return docente; }
    public void setDocente(Docente docente) { this.docente = docente; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
    public int getDominio() { return dominio; }
    public void setDominio(int dominio) { this.dominio = dominio; }
    public int getClaridad() { return claridad; }
    public void setClaridad(int claridad) { this.claridad = claridad; }
    public int getEmpatia() { return empatia; }
    public void setEmpatia(int empatia) { this.empatia = empatia; }
    public NivelDificultad getDificultad() { return dificultad; }
    public void setDificultad(NivelDificultad dificultad) { this.dificultad = dificultad; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public EstadoResena getEstado() { return estado; }
    public void setEstado(EstadoResena estado) { this.estado = estado; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}