package proyectoEntregablep3;

public abstract class Cliente {
    private int id;
    private String nombre;

    public Cliente(int id, String nombre) {
        this.id = id;
        this.nombre = validarNombre(nombre);
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

    public abstract double calcularDescuento(double subtotal);
}
