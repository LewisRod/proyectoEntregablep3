package proyectoEntregablep3;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SistemaPedidos {

    GestorProductos gestorProductos = new GestorProductos();
    private List<Producto> productos;
    private List<Cliente> clientes;
    private List<Pedido> pedidos;

    public SistemaPedidos() {
        productos = new ArrayList<>();
        clientes = new ArrayList<>();
        pedidos = new ArrayList<>();
        productos = GestorProductos.cargarProductos();
        clientes = GestorClientes.cargarClientes();
        GestorPedidosTexto.cargarPedidos(this);
    }


    public synchronized void registrarProducto(int id, String nombre, double precio, int stock) {
        if (buscarProductoInterno(id) != null) {
            throw new IllegalArgumentException("Ya existe un producto con ese Id");
        }

        productos.add(new Producto(id, nombre, precio, stock));
        guardarTodo();
        gestorProductos.guardarProductos(productos);
    }


    public void registrarClienteRegular(int id, String nombre, String direccion) {
        if (buscarClienteInterno(id) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con ese Id");
        }

        clientes.add(new ClienteRegular(id, nombre, direccion));
        guardarTodo();
        GestorClientes.guardarClientes(clientes);
    }


    public void registrarClienteVIP(int id, String nombre, String direccion) {
        if (buscarClienteInterno(id) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con ese Id");
        }
        clientes.add(new ClienteVIP(id, nombre, direccion));
        guardarTodo();
        GestorClientes.guardarClientes(clientes);
    }


    public synchronized void crearPedido(int idPedido, int idCliente)
            throws PedidoInvalidoException {

        if (buscarPedidoInterno(idPedido) != null) {
            throw new PedidoInvalidoException("Ya existe un pedido con ese Id");
        }

        Cliente cliente = buscarClienteInterno(idCliente);

        if (cliente == null) {
            throw new PedidoInvalidoException("El cliente no existe");
        }

        pedidos.add(new Pedido(idPedido, cliente));
    }


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


    public List<Producto> buscarProductoPorNombre(String nombreProducto) {

        List<Producto> encontrados = new ArrayList<>();
        
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(nombreProducto.trim().toLowerCase())) {
                encontrados.add(p);
            }
        }

        return encontrados;
    }


    public synchronized void agregarProductoAPedido(int idPedido, int idProducto, int cantidad)
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


    public synchronized void confirmarPedido(int idPedido)
            throws PedidoInvalidoException,
            StockInsuficienteException {

        Pedido pedido = buscarPedidoInterno(idPedido);

        if (pedido == null) {
            throw new PedidoInvalidoException("Pedido no encontrado");
        }

        pedido.confirmarPedido();
        guardarTodo();
        GestorPedidosTexto.guardarPedidos(pedidos);
    }


    public synchronized void cancelarPedido(int idPedido)
            throws PedidoInvalidoException {

        Pedido pedido = buscarPedidoInterno(idPedido);

        if (pedido == null) {
            throw new PedidoInvalidoException("Pedido no encontrado");
        }

        pedido.cancelarPedido();
        GestorPedidosTexto.guardarPedidos(pedidos);
    }


    public void listarProductos() {
        for (Producto p : productos) {
            System.out.println("Id: " + p.getId() + " | Nombre: " + p.getNombre() + " | Precio: " + p.getPrecio()
                    + " | Stock: " + p.getStock());
        }
    }


    public void listarClientes() {
        for (Cliente c : clientes) {
            System.out.println("Id: " + c.getId() + " | Nombre: " + c.getNombre());
        }
    }


    public void listarPedidos() {
        for (Pedido p : pedidos) {
            System.out.println("Id: " + p.getId() + " | Cliente: " + p.getCliente().getNombre() + " | Fecha: "
                    + p.getFechaFormateada() + " | Estado: " + p.getEstado() + " | Total: " + p.calcularTotalFinal());
        }
    }


    public void listarPedidosPorFecha(Date fechaBuscada) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        boolean encontrado = false;

        for (Pedido p : pedidos) {

            String fechaPedido = simpleDateFormat.format(p.getFecha());
            String fechaFiltro = simpleDateFormat.format(fechaBuscada);

            if (fechaPedido.equals(fechaFiltro)) {
                System.out.println("Id: " + p.getId()
                        + " | Cliente: " + p.getCliente().getNombre()
                        + " | Fecha: " + p.getFechaFormateada()
                        + " | Total: " + p.calcularTotalFinal());
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No hay pedidos en esa fecha.");
        }
    }


    public void eliminarProducto(int id) {

        Iterator<Producto> iterator = productos.iterator();

        while (iterator.hasNext()) {
            Producto p = iterator.next();
            if (p.getId() == id) {
                iterator.remove();
            }
        }

        gestorProductos.guardarProductos(productos);
    }


    public void procesarPedidosConfirmados() {

    boolean huboCambio = false;

    List<Pedido> copia;

    synchronized (this) {
        copia = new ArrayList<>(pedidos);
    }

    for (Pedido p : copia) {

        if (p.getEstado() == Pedido.EstadoPedido.CONFIRMADO) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            p.marcarComoProcesado();
            huboCambio = true;
        }
    }

    if (huboCambio) {
        guardarTodo(); 
    }
}


    public List<Pedido> obtenerCopiaPedidos() {
        synchronized (this) {
            return new ArrayList<>(pedidos);
        }
    }


    public synchronized void reconstruirPedido(int idPedido, int idCliente, String fechaTexto, String estadoTexto) {

        Cliente cliente = buscarClienteInterno(idCliente);

        if (cliente == null) {
            return;
        }

        Pedido pedido = new Pedido(idPedido, cliente);

        if (estadoTexto.equals("CONFIRMADO")) {
            pedido.marcarComoConfirmadoDesdeArchivo();
        }

        if (estadoTexto.equals("PROCESADO")) {
            pedido.marcarComoProcesadoDesdeArchivo();
        }

        if (estadoTexto.equals("CANCELADO")) {
            pedido.cancelarPedido();
        }

        pedidos.add(pedido);
    }

    public String generarReporteGeneral() {

        List<Producto> copiaProductos;
        List<Cliente> copiaClientes;
        List<Pedido> copiaPedidos;

        synchronized (this) {
            copiaProductos = new ArrayList<>(productos);
            copiaClientes = new ArrayList<>(clientes);
            copiaPedidos = new ArrayList<>(pedidos);
        }

        int totalProductos = copiaProductos.size();
        int totalClientes = copiaClientes.size();

        int pendientes = 0;
        int confirmados = 0;
        int procesados = 0;

        double ingresosTotales = 0;

        StringBuilder productosStockBajo = new StringBuilder();

        for (Pedido p : copiaPedidos) {

            if (p.getEstado() == Pedido.EstadoPedido.BORRADOR) {
                pendientes++;
            }

            if (p.getEstado() == Pedido.EstadoPedido.CONFIRMADO) {
                confirmados++;
                ingresosTotales += p.calcularTotalFinal();
            }

            if (p.getEstado() == Pedido.EstadoPedido.PROCESADO) {
                procesados++;
                ingresosTotales += p.calcularTotalFinal();
            }
        }

        for (Producto pr : copiaProductos) {
            if (pr.getStock() < 5) {
                productosStockBajo.append(" - ")
                        .append(pr.getNombre())
                        .append(" (Stock: ")
                        .append(pr.getStock())
                        .append(")\n");
            }
        }

        String fechaHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date());

        return "---REPORTE DEL SISTEMA----\n" +
                "Fecha y hora: " + fechaHora + "\n\n" +
                "Total productos registrados: " + totalProductos + "\n" +
                "Total clientes registrados: " + totalClientes + "\n\n" +
                "Pedidos pendientes: " + pendientes + "\n" +
                "Pedidos confirmados: " + confirmados + "\n" +
                "Pedidos procesados: " + procesados + "\n\n" +
                "Productos con stock bajo:\n" +
                (productosStockBajo.length() == 0
                        ? "Ninguno\n"
                        : productosStockBajo.toString())
                +
                "\nTotal ingresos: " +
                ingresosTotales + "\n";
    }

    public synchronized void recargarSistema() {

    productos = GestorProductos.cargarProductos();
    clientes = GestorClientes.cargarClientes();


    pedidos.clear();

    GestorPedidosTexto.cargarPedidos(this);

    System.out.println("Sistema recargado correctamente desde archivos.");
}


    public String obtenerEstadoProcesamiento() {

    List<Pedido> copia;

    synchronized (this) {
        copia = new ArrayList<>(pedidos);
    }

    int confirmados = 0;
    int procesados = 0;

    for (Pedido p : copia) {

        if (p.getEstado() == Pedido.EstadoPedido.CONFIRMADO) {
            confirmados++;
        }

        if (p.getEstado() == Pedido.EstadoPedido.PROCESADO) {
            procesados++;
        }
    }

    return "Pedidos en espera de procesamiento: " + confirmados +
           "\nPedidos procesados: " + procesados;
}


    public void guardarTodo() {

        List<Producto> copiaProductos;
        List<Cliente> copiaClientes;
        List<Pedido> copiaPedidos;

        synchronized (this) {
            copiaProductos = new ArrayList<>(productos);
            copiaClientes = new ArrayList<>(clientes);
            copiaPedidos = new ArrayList<>(pedidos);
        }

        GestorProductos.guardarProductos(copiaProductos);
        GestorClientes.guardarClientes(copiaClientes);
        GestorPedidosTexto.guardarPedidos(copiaPedidos);
    }


}
