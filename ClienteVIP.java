package proyectoEntregablep3;

public class ClienteVIP extends Cliente {

    private double porcentajeDescuento = 0.20;

    public ClienteVIP(int id, String nombre) {
        super(id, nombre);
    }

    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * porcentajeDescuento;
    }
}
