package proyectoEntregablep3;

public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int id, String nombre, double precio, int stock)
            throws IllegalArgumentException {
        if (precio <= 0) {
            throw new IllegalArgumentException(" precio debe ser mayor que 0");
        }

        if (stock < 0) {
            throw new IllegalArgumentException(" stock no puede ser negativo");
        }

        this.id = id;
        this.nombre = validarNombre(nombre);
        this.precio = precio;
        this.stock = stock;
    }

    private String validarNombre(String nombre) {
        if (nombre.equalsIgnoreCase(null) || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacio");
        }
        return nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void disminuirStock(int cantidad)
            throws StockInsuficienteException {
        if (cantidad > stock) {
            throw new StockInsuficienteException("Stock insuficiente para el producto: " + nombre);
        }
        stock -= cantidad;
    }

    public void aumentarStock(int cantidad) {
        stock += cantidad;
    }
}
