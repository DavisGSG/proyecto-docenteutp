package pe.utp.docenteutp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "docentes")
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String facultad;
    private String sede = "UTP Lima Centro · Edificio Arequipa";
    private boolean verificado = true;
    private String estiloEnsenanza;
    private String estiloEvaluacion;

    @Column(length = 500)
    private String resumen;

    private String etiquetas;   // separadas por coma

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getFacultad() { return facultad; }
    public void setFacultad(String facultad) { this.facultad = facultad; }
    public String getSede() { return sede; }
    public void setSede(String sede) { this.sede = sede; }
    public boolean isVerificado() { return verificado; }
    public void setVerificado(boolean verificado) { this.verificado = verificado; }
    public String getEstiloEnsenanza() { return estiloEnsenanza; }
    public void setEstiloEnsenanza(String estiloEnsenanza) { this.estiloEnsenanza = estiloEnsenanza; }
    public String getEstiloEvaluacion() { return estiloEvaluacion; }
    public void setEstiloEvaluacion(String estiloEvaluacion) { this.estiloEvaluacion = estiloEvaluacion; }
    public String getResumen() { return resumen; }
    public void setResumen(String resumen) { this.resumen = resumen; }
    public String getEtiquetas() { return etiquetas; }
    public void setEtiquetas(String etiquetas) { this.etiquetas = etiquetas; }
}