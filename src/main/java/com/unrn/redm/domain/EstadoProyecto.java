package com.unrn.redm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EstadoProyecto.
 */
@Entity
@Table(name = "estado_proyecto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstadoProyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @OneToMany(mappedBy = "estadoProyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Proyecto> proyectos = new HashSet<>();

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

    public EstadoProyecto titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public EstadoProyecto proyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
        return this;
    }

    public EstadoProyecto addProyecto(Proyecto proyecto) {
        this.proyectos.add(proyecto);
        proyecto.setEstadoProyecto(this);
        return this;
    }

    public EstadoProyecto removeProyecto(Proyecto proyecto) {
        this.proyectos.remove(proyecto);
        proyecto.setEstadoProyecto(null);
        return this;
    }

    public void setProyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoProyecto)) {
            return false;
        }
        return id != null && id.equals(((EstadoProyecto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstadoProyecto{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }
}
