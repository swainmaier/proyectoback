package com.unrn.redm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Proyecto.
 */
@Entity
@Table(name = "proyecto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "sumary")
    private String sumary;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "code")
    private String code;

    @Column(name = "fecha_pub")
    private String fechaPub;

    @Column(name = "sinopsis")
    private String sinopsis;

    @Column(name = "storyline")
    private String storyline;

    @Column(name = "logline")
    private String logline;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @OneToMany(mappedBy = "proyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contenido> contenidos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("proyectos")
    private EstadoProyecto estadoProyecto;

    @ManyToOne
    @JsonIgnoreProperties("proyectos")
    private Target target;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Proyecto titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSumary() {
        return sumary;
    }

    public Proyecto sumary(String sumary) {
        this.sumary = sumary;
        return this;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public String getSlogan() {
        return slogan;
    }

    public Proyecto slogan(String slogan) {
        this.slogan = slogan;
        return this;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCode() {
        return code;
    }

    public Proyecto code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFechaPub() {
        return fechaPub;
    }

    public Proyecto fechaPub(String fechaPub) {
        this.fechaPub = fechaPub;
        return this;
    }

    public void setFechaPub(String fechaPub) {
        this.fechaPub = fechaPub;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public Proyecto sinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
        return this;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getStoryline() {
        return storyline;
    }

    public Proyecto storyline(String storyline) {
        this.storyline = storyline;
        return this;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public String getLogline() {
        return logline;
    }

    public Proyecto logline(String logline) {
        this.logline = logline;
        return this;
    }

    public void setLogline(String logline) {
        this.logline = logline;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Proyecto imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Proyecto imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Set<Contenido> getContenidos() {
        return contenidos;
    }

    public Proyecto contenidos(Set<Contenido> contenidos) {
        this.contenidos = contenidos;
        return this;
    }

    public Proyecto addContenido(Contenido contenido) {
        this.contenidos.add(contenido);
        contenido.setProyecto(this);
        return this;
    }

    public Proyecto removeContenido(Contenido contenido) {
        this.contenidos.remove(contenido);
        contenido.setProyecto(null);
        return this;
    }

    public void setContenidos(Set<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public EstadoProyecto getEstadoProyecto() {
        return estadoProyecto;
    }

    public Proyecto estadoProyecto(EstadoProyecto estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
        return this;
    }

    public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public Target getTarget() {
        return target;
    }

    public Proyecto target(Target target) {
        this.target = target;
        return this;
    }

    public void setTarget(Target target) {
        this.target = target;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proyecto)) {
            return false;
        }
        return id != null && id.equals(((Proyecto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", sumary='" + getSumary() + "'" +
            ", slogan='" + getSlogan() + "'" +
            ", code='" + getCode() + "'" +
            ", fechaPub='" + getFechaPub() + "'" +
            ", sinopsis='" + getSinopsis() + "'" +
            ", storyline='" + getStoryline() + "'" +
            ", logline='" + getLogline() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            "}";
    }
}
