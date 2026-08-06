package org.example.model;

import jakarta.persistence.*;
import org.example.model.Interface.Calculable;
import org.example.model.enums.Estado;
import org.example.model.enums.FormaPago;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//Jpa
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    private Set<DetallePedido> detallePedidos;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario user;

    public Pedido() {
        this.estado = Estado.PENDIENTE;
        this.fecha = LocalDate.now();
        this.total = 0.0;
        this.detallePedidos = new HashSet<>();
    }

    public Pedido(LocalDate fecha, Estado estado, Double total, FormaPago formaPago, Set<DetallePedido> detallePedidos) {
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.formaPago = formaPago;
        this.detallePedidos = detallePedidos;
    }

    public Pedido(Long id, LocalDate fecha, Estado estado, Double total, FormaPago formaPago, Set<DetallePedido> detallePedidos) {
        super(id);
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.formaPago = formaPago;
        this.detallePedidos = detallePedidos;
    }

    public Pedido(LocalDate fecha, Estado estado, Double total, FormaPago formaPago, Set<DetallePedido> detallePedidos, Usuario usuario) {
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.formaPago = formaPago;
        this.detallePedidos = detallePedidos;
        this.user = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Set<DetallePedido> getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(Set<DetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void addDetallePedido(DetallePedido det) {
        this.detallePedidos.add(det);
        System.out.println("Producto agregado.");
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        DetallePedido detallePedidos = this.detallePedidos.stream()
                .filter(det -> det.getProducto().equals(producto))
                .findFirst()
                .orElse(null);
        return detallePedidos;
    }

    public void deleteDetallePedido(Producto producto) {
        DetallePedido det = detallePedidos.stream().filter(d -> d.getProducto().equals(producto)).findFirst().orElse(null);
        detallePedidos.remove(det);
        System.out.println("Detalle pedido eliminado.");
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "fecha=" + fecha +
                ", estado=" + estado +
                ", total=" + total +
                ", formaPago=" + formaPago +
                '}';
    }

    @Override
    public void calcularTotal() {
        double total = this.detallePedidos.stream().mapToDouble(DetallePedido::getSubtotal).sum();
        System.out.println("Total: " + total);
    }
}
