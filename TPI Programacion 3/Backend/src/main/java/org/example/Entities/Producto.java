package org.example.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


//Jpa
@Entity
@Table(name = "Producto")
@NamedQueries({
        @NamedQuery(
                name = "Producto.findAll",
                query = "select p from Producto p"
        )
})

public class Producto extends Base{

    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;

    @ManyToOne
    private Categoria categoria;

    public Producto() {
    }

    public Producto(String nombre, double precio, String descripcion, int stock, boolean disponible, Categoria categoria, String imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.disponible = disponible;
        this.categoria = categoria;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
