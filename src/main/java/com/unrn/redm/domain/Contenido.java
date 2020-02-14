package com.unrn.redm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Contenido.
 */
@Entity
@Table(name = "contenido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contenido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "cant_temporadas")
    private Integer cantTemporadas;

    @Column(name = "fecha_publicacion")
    private String fechaPublicacion;

    @Column(name = "caratula")
    private String caratula;

    @OneToMany(mappedBy = "contenido")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Temporada> temporadas = new HashSet<>();

    @OneToMany(mappedBy = "contenido")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trailer> trailers = new HashSet<>();

    @OneToMany(mappedBy = "contenido")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Teaser> teasers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("contenidos")
    private Proyecto proyecto;

    @ManyToOne
    @JsonIgnoreProperties("contenidos")
    private EstadoContenido estadoContenido;

    @ManyToOne
    @JsonIgnoreProperties("contenidos")
    private Formato formato;

    @ManyToOne
    @JsonIgnoreProperties("contenidos")
    private Genero genero;

    @ManyToOne
    @JsonIgnoreProperties("contenidos")
    private Plataforma plataforma;

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

    public Contenido titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getCantTemporadas() {
        return cantTemporadas;
    }

    public Contenido cantTemporadas(Integer cantTemporadas) {
        this.cantTemporadas = cantTemporadas;
        return this;
    }

    public void setCantTemporadas(Integer cantTemporadas) {
        this.cantTemporadas = cantTemporadas;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public Contenido fechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
        return this;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getCaratula() {
        return caratula;
    }

    public Contenido caratula(String caratula) {
        this.caratula = caratula;
        return this;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    public Set<Temporada> getTemporadas() {
        return temporadas;
    }

    public Contenido temporadas(Set<Temporada> temporadas) {
        this.temporadas = temporadas;
        return this;
    }

    public Contenido addTemporada(Temporada temporada) {
        this.temporadas.add(temporada);
        temporada.setContenido(this);
        return this;
    }

    public Contenido removeTemporada(Temporada temporada) {
        this.temporadas.remove(temporada);
        temporada.setContenido(null);
        return this;
    }

    public void setTemporadas(Set<Temporada> temporadas) {
        this.temporadas = temporadas;
    }

    public Set<Trailer> getTrailers() {
        return trailers;
    }

    public Contenido trailers(Set<Trailer> trailers) {
        this.trailers = trailers;
        return this;
    }

    public Contenido addTrailer(Trailer trailer) {
        this.trailers.add(trailer);
        trailer.setContenido(this);
        return this;
    }

    public Contenido removeTrailer(Trailer trailer) {
        this.trailers.remove(trailer);
        trailer.setContenido(null);
        return this;
    }

    public void setTrailers(Set<Trailer> trailers) {
        this.trailers = trailers;
    }

    public Set<Teaser> getTeasers() {
        return teasers;
    }

    public Contenido teasers(Set<Teaser> teasers) {
        this.teasers = teasers;
        return this;
    }

    public Contenido addTeaser(Teaser teaser) {
        this.teasers.add(teaser);
        teaser.setContenido(this);
        return this;
    }

    public Contenido removeTeaser(Teaser teaser) {
        this.teasers.remove(teaser);
        teaser.setContenido(null);
        return this;
    }

    public void setTeasers(Set<Teaser> teasers) {
        this.teasers = teasers;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public Contenido proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public EstadoContenido getEstadoContenido() {
        return estadoContenido;
    }

    public Contenido estadoContenido(EstadoContenido estadoContenido) {
        this.estadoContenido = estadoContenido;
        return this;
    }

    public void setEstadoContenido(EstadoContenido estadoContenido) {
        this.estadoContenido = estadoContenido;
    }

    public Formato getFormato() {
        return formato;
    }

    public Contenido formato(Formato formato) {
        this.formato = formato;
        return this;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public Genero getGenero() {
        return genero;
    }

    public Contenido genero(Genero genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public Contenido plataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
        return this;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contenido)) {
            return false;
        }
        return id != null && id.equals(((Contenido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contenido{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", cantTemporadas=" + getCantTemporadas() +
            ", fechaPublicacion='" + getFechaPublicacion() + "'" +
            ", caratula='" + getCaratula() + "'" +
            "}";
    }
}
