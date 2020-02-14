package com.unrn.redm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Temporada.
 */
@Entity
@Table(name = "temporada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Temporada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "cant_capitulos")
    private Integer cantCapitulos;

    @Column(name = "duracion_total")
    private Integer duracionTotal;

    @OneToMany(mappedBy = "temporada")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Capitulo> capitulos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("temporadas")
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

    public Temporada titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public Temporada numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getCantCapitulos() {
        return cantCapitulos;
    }

    public Temporada cantCapitulos(Integer cantCapitulos) {
        this.cantCapitulos = cantCapitulos;
        return this;
    }

    public void setCantCapitulos(Integer cantCapitulos) {
        this.cantCapitulos = cantCapitulos;
    }

    public Integer getDuracionTotal() {
        return duracionTotal;
    }

    public Temporada duracionTotal(Integer duracionTotal) {
        this.duracionTotal = duracionTotal;
        return this;
    }

    public void setDuracionTotal(Integer duracionTotal) {
        this.duracionTotal = duracionTotal;
    }

    public Set<Capitulo> getCapitulos() {
        return capitulos;
    }

    public Temporada capitulos(Set<Capitulo> capitulos) {
        this.capitulos = capitulos;
        return this;
    }

    public Temporada addCapitulo(Capitulo capitulo) {
        this.capitulos.add(capitulo);
        capitulo.setTemporada(this);
        return this;
    }

    public Temporada removeCapitulo(Capitulo capitulo) {
        this.capitulos.remove(capitulo);
        capitulo.setTemporada(null);
        return this;
    }

    public void setCapitulos(Set<Capitulo> capitulos) {
        this.capitulos = capitulos;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public Temporada contenido(Contenido contenido) {
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
        if (!(o instanceof Temporada)) {
            return false;
        }
        return id != null && id.equals(((Temporada) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Temporada{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", numero=" + getNumero() +
            ", cantCapitulos=" + getCantCapitulos() +
            ", duracionTotal=" + getDuracionTotal() +
            "}";
    }
}
