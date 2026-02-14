package proyectoEntregablep3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SistemaPedidos {

    private List<Producto> productos;
    private List<Cliente> clientes;
    private List<Pedido> pedidos;

    public SistemaPedidos() {
        productos = new ArrayList<>();
        clientes = new ArrayList<>();
        pedidos = new ArrayList<>();
    }

    // ==============================
    // REGISTROS
    // ==============================

    public void registrarProducto(int id, String nombre, double precio, int stock) {
        if (buscarProductoInterno(id) != null) {
            throw new IllegalArgumentException("Ya existe un producto con ese ID");
        }

        productos.add(new Producto(id, nombre, precio, stock));
    }

    public void registrarClienteRegular(int id, String nombre) {
        if (buscarClienteInterno(id) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con ese ID");
        }

        clientes.add(new ClienteRegular(id, nombre));
    }

    public void registrarClienteVIP(int id, String nombre) {
        if (buscarClienteInterno(id) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con ese ID");
        }

        clientes.add(new ClienteVIP(id, nombre));
    }

    public void crearPedido(int idPedido, int idCliente)
            throws PedidoInvalidoException {

        if (buscarPedidoInterno(idPedido) != null) {
            throw new PedidoInvalidoException("Ya existe un pedido con ese ID");
        }

        Cliente cliente = buscarClienteInterno(idCliente);

        if (cliente == null) {
            throw new PedidoInvalidoException("El cliente no existe");
        }

        pedidos.add(new Pedido(idPedido, cliente));
    }

    // ==============================
    // BUSQUEDAS
    // ==============================

    private Producto buscarProductoInterno(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private Cliente buscarClienteInterno(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    private Pedido buscarPedidoInterno(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Producto buscarProductoPorId(int id)
            throws ProductoNoEncontradoException {

        Producto producto = buscarProductoInterno(id);

        if (producto == null) {
            throw new ProductoNoEncontradoException("Producto no encontrado");
        }

        return producto;
    }

    public List<Producto> buscarProductoPorNombre(String texto) {

        List<Producto> encontrados = new ArrayList<>();
        String criterio = texto.trim().toLowerCase();

        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(criterio)) {
                encontrados.add(p);
            }
        }

        return encontrados;
    }

    // ==============================
    // OPERACIONES PEDIDO
    // ==============================

    public void agregarProductoAPedido(int idPedido, int idProducto, int cantidad)
            throws ProductoNoEncontradoException,
                   StockInsuficienteException,
                   PedidoInvalidoException {

        Pedido pedido = buscarPedidoInterno(idPedido);

        if (pedido == null) {
            throw new PedidoInvalidoException("Pedido no encontrado");
        }

        Producto producto = buscarProductoPorId(idProducto);

        pedido.agregarProducto(producto, cantidad);
    }

    public void confirmarPedido(int idPedido)
            throws PedidoInvalidoException,
                   StockInsuficienteException {

        Pedido pedido = buscarPedidoInterno(idPedido);

        if (pedido == null) {
            throw new PedidoInvalidoException("Pedido no encontrado");
        }

        pedido.confirmarPedido();
    }

    public void cancelarPedido(int idPedido)
            throws PedidoInvalidoException {

        Pedido pedido = buscarPedidoInterno(idPedido);

        if (pedido == null) {
            throw new PedidoInvalidoException("Pedido no encontrado");
        }

        pedido.cancelarPedido();
    }

    // ==============================
    // LISTADOS (Iterable)
    // ==============================

    public void listarProductos() {
        for (Producto p : productos) {
            System.out.println("ID: " + p.getId()
                    + " | Nombre: " + p.getNombre()
                    + " | Precio: " + p.getPrecio()
                    + " | Stock: " + p.getStock());
        }
    }

    public void listarClientes() {
        for (Cliente c : clientes) {
            System.out.println("ID: " + c.getId()
                    + " | Nombre: " + c.getNombre());
        }
    }

    public void listarPedidos() {
        for (Pedido p : pedidos) {
            System.out.println("ID: " + p.getId()
                    + " | Cliente: " + p.getCliente().getNombre()
                    + " | Fecha: " + p.getFechaFormateada()
                    + " | Estado: " + p.getEstado()
                    + " | Total: " + p.calcularTotalFinal());
        }
    }

    public void eliminarProducto(int id) {

        Iterator<Producto> it = productos.iterator();

        while (it.hasNext()) {
            Producto p = it.next();
            if (p.getId() == id) {
                it.remove();   // eliminación segura
            }
        }
    }
}
