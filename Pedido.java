package proyectoEntregablep3;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class Pedido {

    public enum EstadoPedido {
        BORRADOR,
        CONFIRMADO,
        PROCESADO,
        CANCELADO
    }

    private int id;
    private Cliente cliente;
    private EstadoPedido estado;
    private List<DetallePedido> detalles;
    private Date fecha;

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.estado = EstadoPedido.BORRADOR;
        this.detalles = new ArrayList<>();
        this.fecha = new Date();
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public synchronized EstadoPedido getEstado() {
        return estado;
    }

    public Date getFecha() {
        return fecha;
    }


    public String getFechaFormateada() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(fecha);
    }


    public void agregarProducto(Producto producto, int cantidad)
            throws StockInsuficienteException {

        if (estado != EstadoPedido.BORRADOR) {
            throw new StockInsuficienteException("Solo se agregan productos en estado BORRADOR");
        }

        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException("Stock insuficiente");
        }

        detalles.add(new DetallePedido(producto, cantidad));
    }


    public double calcularSubtotal() {
        double subtotal = 0;
        for (DetallePedido detalle : detalles) {
            subtotal += detalle.calcularSubtotal();
        }
        return subtotal;
    }


    public double calcularDescuento() {
        return cliente.calcularDescuento(calcularSubtotal());
    }


    public double calcularTotalFinal() {
        return calcularSubtotal() - calcularDescuento();
    }


    public void confirmarPedido()
            throws PedidoInvalidoException, StockInsuficienteException {

        if (detalles.isEmpty()) {
            throw new PedidoInvalidoException("No se puede confirmar un pedido vacio");
        }

        for (DetallePedido detalle : detalles) {
            detalle.getProducto().disminuirStock(detalle.getCantidad());
        }

        estado = EstadoPedido.CONFIRMADO;
    }


    public void cancelarPedido() {

        if (estado == EstadoPedido.CONFIRMADO) {
            for (DetallePedido detalle : detalles) {
                detalle.getProducto().aumentarStock(detalle.getCantidad());
            }
        }

        estado = EstadoPedido.CANCELADO;
    }


    public List<DetallePedido> getDetalles() {
        return detalles;
    }


    public synchronized void marcarComoProcesado() {
        if (estado == EstadoPedido.CONFIRMADO) {
            estado = EstadoPedido.PROCESADO;
        }
    }


    public synchronized void marcarComoConfirmadoDesdeArchivo() {
    this.estado = EstadoPedido.CONFIRMADO;
}

public synchronized void marcarComoProcesadoDesdeArchivo() {
    this.estado = EstadoPedido.PROCESADO;
}
}
