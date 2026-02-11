package proyectoEntregablep3;

public class DetallePedido {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public DetallePedido(Producto producto, int cantidad) {

        this.producto = producto;
         if (cantidad <= 0) {
            System.out.print("La cantidad debe ser mayor que 0");
        }
        else{
            this.cantidad = cantidad;
        }
        this.precioUnitario = producto.getPrecio();
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double calcularSubtotal() {
        return cantidad * precioUnitario;
    }
}
