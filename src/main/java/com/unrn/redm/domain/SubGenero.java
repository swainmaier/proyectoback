package com.unrn.redm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SubGenero.
 */
@Entity
@Table(name = "sub_genero")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubGenero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @OneToMany(mappedBy = "subGenero")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Genero> generos = new HashSet<>();

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

    public SubGenero titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Genero> getGeneros() {
        return generos;
    }

    public SubGenero generos(Set<Genero> generos) {
        this.generos = generos;
        return this;
    }

    public SubGenero addGenero(Genero genero) {
        this.generos.add(genero);
        genero.setSubGenero(this);
        return this;
    }

    public SubGenero removeGenero(Genero genero) {
        this.generos.remove(genero);
        genero.setSubGenero(null);
        return this;
    }

    public void setGeneros(Set<Genero> generos) {
        this.generos = generos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubGenero)) {
            return false;
        }
        return id != null && id.equals(((SubGenero) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubGenero{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }
}
