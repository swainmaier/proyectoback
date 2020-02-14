package com.unrn.redm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Genero.
 */
@Entity
@Table(name = "genero")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Genero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @OneToMany(mappedBy = "genero")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contenido> contenidos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("generos")
    private SubGenero subGenero;

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

    public Genero titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Contenido> getContenidos() {
        return contenidos;
    }

    public Genero contenidos(Set<Contenido> contenidos) {
        this.contenidos = contenidos;
        return this;
    }

    public Genero addContenido(Contenido contenido) {
        this.contenidos.add(contenido);
        contenido.setGenero(this);
        return this;
    }

    public Genero removeContenido(Contenido contenido) {
        this.contenidos.remove(contenido);
        contenido.setGenero(null);
        return this;
    }

    public void setContenidos(Set<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public SubGenero getSubGenero() {
        return subGenero;
    }

    public Genero subGenero(SubGenero subGenero) {
        this.subGenero = subGenero;
        return this;
    }

    public void setSubGenero(SubGenero subGenero) {
        this.subGenero = subGenero;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Genero)) {
            return false;
        }
        return id != null && id.equals(((Genero) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Genero{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }
}
