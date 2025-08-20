package com.alura.LiterAlura.modelos;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    private Integer descargas;
    private List<String> lenguaje;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autor;

    public Libro() {
    }

    public Libro(DatosLibro libro) {
        this.titulo = libro.titulo();
        this.lenguaje = libro.lenguaje();
        this.descargas = libro.descargas();
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autor) {
        autor.forEach(e -> e.setLibro(this));
        this.autor = autor;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public List<String> getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(List<String> lenguaje) {
        this.lenguaje = lenguaje;
    }

    @Override
    public String toString() {
        return "Titulo = '" + titulo +
                "', autor = " + autor +
                ", lenguaje = " + lenguaje +
                ", descargas = " + descargas;
    }
}
