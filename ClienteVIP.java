package proyectoEntregablep3;

public class ClienteVIP extends Cliente {

    private double porcentajeDescuento = 0.20;

    public ClienteVIP(int id, String nombre,String direccion) {
        super(id, nombre,direccion);
    }

    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * porcentajeDescuento;
    }
}
