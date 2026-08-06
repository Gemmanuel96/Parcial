package org.example.model;

import jakarta.persistence.*;


//Jpa
@Entity
@Table(name = "DETALLE_PEDIDO")
public class DetallePedido extends Base {

    @Column(name = "Cantidad")
    private int cantidad;

    private double subtotal;

    //Muchos Pedidos tienen 1 producto
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public DetallePedido() {
    }

    public DetallePedido(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = producto.getPrecio() * cantidad;

    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                ", producto=" + producto +
                '}';
    }
}
