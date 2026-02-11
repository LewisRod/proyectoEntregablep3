package proyectoEntregablep3;

public class Pedido {

    public enum EstadoPedido {
        BORRADOR,
        CONFIRMADO,
        CANCELADO
    }

    private int id;
    private Cliente cliente;
    private EstadoPedido estado;
    private DetallePedido[] detalles;
    private int totalProductos;

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.estado = EstadoPedido.BORRADOR;
        this.detalles = new DetallePedido[30];
        this.totalProductos = 0;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void agregarProducto(Producto producto, int cantidad) {

        if (estado != EstadoPedido.BORRADOR) {
            System.out.print("solo se agregan productos en estado borrador");
        }

        if (totalProductos < detalles.length) {
            detalles[totalProductos] = new DetallePedido(producto, cantidad);
            totalProductos++;
        }
    }

    public double calcularSubtotal() {
        double subtotal = 0;

        for (int i = 0; i < totalProductos; i++) {
            subtotal += detalles[i].calcularSubtotal();
        }
        return subtotal;
    }

    public double calcularDescuento() {
        return cliente.calcularDescuento(calcularSubtotal());
    }

    public double calcularTotalFinal() {
        return calcularSubtotal() - calcularDescuento();
    }

    public DetallePedido[] getDetalles() {
        return detalles;
    }

    public void confirmarPedido() {
        estado = EstadoPedido.CONFIRMADO;
    }

    public void cancelarPedido() {
        estado = EstadoPedido.CANCELADO;
    }

    public int getTotalDetalles() {
        return totalProductos;
    }
}
