package com.unrn.redm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Target.
 */
@Entity
@Table(name = "target")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Target implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @OneToMany(mappedBy = "target")
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

    public Target titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public Target proyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
        return this;
    }

    public Target addProyecto(Proyecto proyecto) {
        this.proyectos.add(proyecto);
        proyecto.setTarget(this);
        return this;
    }

    public Target removeProyecto(Proyecto proyecto) {
        this.proyectos.remove(proyecto);
        proyecto.setTarget(null);
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
        if (!(o instanceof Target)) {
            return false;
        }
        return id != null && id.equals(((Target) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Target{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }
}
