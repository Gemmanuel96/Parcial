package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    public double getSubtotal(){
        subtotal = producto.getPrecio() * cantidad;
        return subtotal;
    }

}
