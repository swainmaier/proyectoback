package com.unrn.redm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Capitulo.
 */
@Entity
@Table(name = "capitulo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Capitulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "sinopsis")
    private String sinopsis;

    @Column(name = "logline")
    private String logline;

    @Column(name = "caratula")
    private String caratula;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "vimeo_id")
    private String vimeoID;

    @ManyToOne
    @JsonIgnoreProperties("capitulos")
    private Temporada temporada;

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

    public Capitulo titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public Capitulo numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public Capitulo sinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
        return this;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getLogline() {
        return logline;
    }

    public Capitulo logline(String logline) {
        this.logline = logline;
        return this;
    }

    public void setLogline(String logline) {
        this.logline = logline;
    }

    public String getCaratula() {
        return caratula;
    }

    public Capitulo caratula(String caratula) {
        this.caratula = caratula;
        return this;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public Capitulo duracion(Integer duracion) {
        this.duracion = duracion;
        return this;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getVimeoID() {
        return vimeoID;
    }

    public Capitulo vimeoID(String vimeoID) {
        this.vimeoID = vimeoID;
        return this;
    }

    public void setVimeoID(String vimeoID) {
        this.vimeoID = vimeoID;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public Capitulo temporada(Temporada temporada) {
        this.temporada = temporada;
        return this;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Capitulo)) {
            return false;
        }
        return id != null && id.equals(((Capitulo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Capitulo{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", numero=" + getNumero() +
            ", sinopsis='" + getSinopsis() + "'" +
            ", logline='" + getLogline() + "'" +
            ", caratula='" + getCaratula() + "'" +
            ", duracion=" + getDuracion() +
            ", vimeoID='" + getVimeoID() + "'" +
            "}";
    }
}
