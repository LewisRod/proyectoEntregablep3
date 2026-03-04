package proyectoEntregablep3;

public class ClienteRegular extends Cliente {

    public ClienteRegular(int id, String nombre,String direccion) {
        super(id, nombre,direccion);
    }


    @Override
    public double calcularDescuento(double subtotal) {
        return 0;   
    }
}
