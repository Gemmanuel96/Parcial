package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.model.Interface.Calculable;
import org.example.model.Enums.Estado;
import org.example.model.Enums.FormaPago;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "PEDIDO")
public class Pedido extends Base implements Calculable {

    @Temporal(TemporalType.DATE)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Double total;

    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "pedido_id")
    @Builder.Default
    private Set<DetallePedido> detallePedidos = new HashSet<>();

    @Override
    public void calcularTotal() {
        total = 0.0;
        total = detallePedidos.stream().mapToDouble(DetallePedido::getSubtotal).sum();
        System.out.println("TOTAL DETALLE PEDIDO: " + total);
    }

    public void addDetallePedido(int cantidad, Producto producto) {
        try {
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setCantidad(cantidad);
            detallePedido.setProducto(producto);
            detallePedidos.add(detallePedido);
            System.out.println("DETALLE PEDIDO: " + detallePedidos);
        }catch (Exception e){
            System.out.println("Error al agregar DetallePedido: " + e.getMessage());
        }
    }
}
