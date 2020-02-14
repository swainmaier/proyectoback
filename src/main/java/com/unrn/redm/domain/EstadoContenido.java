package com.unrn.redm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EstadoContenido.
 */
@Entity
@Table(name = "estado_contenido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstadoContenido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @OneToMany(mappedBy = "estadoContenido")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contenido> contenidos = new HashSet<>();

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

    public EstadoContenido titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Contenido> getContenidos() {
        return contenidos;
    }

    public EstadoContenido contenidos(Set<Contenido> contenidos) {
        this.contenidos = contenidos;
        return this;
    }

    public EstadoContenido addContenido(Contenido contenido) {
        this.contenidos.add(contenido);
        contenido.setEstadoContenido(this);
        return this;
    }

    public EstadoContenido removeContenido(Contenido contenido) {
        this.contenidos.remove(contenido);
        contenido.setEstadoContenido(null);
        return this;
    }

    public void setContenidos(Set<Contenido> contenidos) {
        this.contenidos = contenidos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoContenido)) {
            return false;
        }
        return id != null && id.equals(((EstadoContenido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstadoContenido{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }
}
