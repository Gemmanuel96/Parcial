package org.example.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

//Jpa
@Entity
@Table(name = "Categoria")
@NamedQueries({
         @NamedQuery(
                 name = "Categoria.findAll",
                 query = "select c from Categoria c "
         ),
        @NamedQuery(
                name = "Categoria.find",
                query = "select c from Categoria c where c.nombre =: nombre"
        )
})
public class Categoria extends Base{
    private String nombre;
    private String descripcion;

    public Categoria() {
    }

    public Categoria(Long id, String nombre, String descripcion) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return  "Categoria{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
