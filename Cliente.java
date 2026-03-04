package proyectoEntregablep3;

public abstract class Cliente {
    private int id;
    private String nombre;
    private String direccion;

    public Cliente(int id, String nombre, String direccion) {
        this.id = id;
        this.nombre = validarNombre(nombre);
        this.direccion = direccion;
    }

    private String validarNombre(String nombre)
            throws IllegalArgumentException {
        if (nombre.equalsIgnoreCase(null) || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede ser nulo o vacio.");
        }
        return nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public abstract double calcularDescuento(double subtotal);
}
