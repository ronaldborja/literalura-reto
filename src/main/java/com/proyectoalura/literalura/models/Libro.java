package com.proyectoalura.literalura.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="libros")
public class Libro {

    //Atributos:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String titulo;

    private List<String> idiomas;

    private String idioma;

    private Double numeroDescargas;

    //Relación muchos a uno
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="autor_id") //Especifica el atributo por el que se relacionana
    private Autor autor;

    //Constructores
    public Libro() {
    }

    public Libro(String titulo, Autor autor, String idioma, double numDescargas) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDescargas = numDescargas;
        this.autor = autor;
    }

    //Getters & Setters


    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getIdioma() {
        return idiomas;
    }

    public void setIdioma(List<String> idioma) {
        this.idiomas = idioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma=" + idiomas +
                ", numeroDescargas=" + numeroDescargas +
                ", autor=" + autor +
                '}';
    }
}
