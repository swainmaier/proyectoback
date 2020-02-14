package com.unrn.redm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Trailer.
 */
@Entity
@Table(name = "trailer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trailer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JsonIgnoreProperties("trailers")
    private Contenido contenido;

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

    public Trailer titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public Trailer url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public Trailer contenido(Contenido contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(Contenido contenido) {
        this.contenido = contenido;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trailer)) {
            return false;
        }
        return id != null && id.equals(((Trailer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Trailer{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
