package proyectoEntregablep3;

public class SistemaPedidos {

    private Producto[] productos;
    private Cliente[] clientes;
    private Pedido[] pedidos;

    private int totalProductos;
    private int totalClientes;
    private int totalPedidos;

    public SistemaPedidos(int capacidadProductos, int capacidadClientes, int capacidadPedidos) {

        productos = new Producto[capacidadProductos];
        clientes = new Cliente[capacidadClientes];
        pedidos = new Pedido[capacidadPedidos];

        totalProductos = 0;
        totalClientes = 0;
        totalPedidos = 0;
    }

    public void registrarProducto(int id, String nombre, double precio, int stock) {

        if (buscarProductoPorId(id) != null) {
            System.out.println("Ya existe un producto con ese ID");
        }

        if (totalProductos < productos.length) {
            productos[totalProductos] = new Producto(id, nombre, precio, stock);
            totalProductos++;
        }
    }

    public Producto buscarProductoPorId(int id) {

        for (int i = 0; i < totalProductos; i++) {
            if (productos[i].getId() == id) {
                return productos[i];
            }
        }
        return null;
    }

    public Cliente buscarClientePorId(int id) {

        for (int i = 0; i < totalClientes; i++) {
            if (clientes[i].getId() == id) {
                return clientes[i];
            }
        }
        return null;
    }

    public void registrarClienteRegular(int id, String nombre) {

        if (buscarClientePorId(id) != null) {
            System.out.println("ya un cliente tiene ese ID");
        }

        if (totalClientes < clientes.length) {
            clientes[totalClientes] = new ClienteRegular(id, nombre);
            totalClientes++;
        }
    }

    public void registrarClienteVIP(int id, String nombre) {

        if (buscarClientePorId(id) != null) {
            System.out.println("ya un cliente tiene ese ID");
        }

        if (totalClientes < clientes.length) {
            clientes[totalClientes] = new ClienteVIP(id, nombre);
            totalClientes++;
        }
    }

    public Pedido buscarPedidoPorId(int id) {

        for (int i = 0; i < totalPedidos; i++) {
            if (pedidos[i].getId() == id) {
                return pedidos[i];
            }
        }

        return null;
    }

    public void crearPedido(int idPedido, int idCliente) {

        if (buscarPedidoPorId(idPedido) != null) {
            System.out.println("ya hay un pedido con ese ID");
        }

        Cliente cliente = buscarClientePorId(idCliente);

        if (cliente == null) {
            System.out.println("El cliente no existe");
        }

        if (totalPedidos < pedidos.length) {
            pedidos[totalPedidos] = new Pedido(idPedido, cliente);
            totalPedidos++;
        }
    }

    public void agregarProductoAPedido(int idPedido, int idProducto, int cantidad) {

        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido == null) {
            System.out.println("El pedido no existe");
        }

        Producto producto = buscarProductoPorId(idProducto);

        if (producto == null) {
            System.out.println("producto no registrado");
        }

        if (producto.getStock() < cantidad) {
            System.out.println("no hay esa cantidad en stock");
        }

        pedido.agregarProducto(producto, cantidad);
    }

    public void confirmarPedido(int idPedido) {

        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido == null) {
            System.out.println("El pedido no existe");
        }

        if (pedido.getEstado() != Pedido.EstadoPedido.BORRADOR) {
            System.out.println("El pedido no esta en estado BORRADOR");
        }

        if (pedido.getTotalDetalles() == 0) {
            System.out.println("No se puede confirmar un pedido sin productos");
        }

        for (int i = 0; i < pedido.getTotalDetalles(); i++) {
            DetallePedido detalle = pedido.getDetalles()[i];

            Producto producto = detalle.getProducto();
            int cantidad = detalle.getCantidad();

            producto.disminuirStock(cantidad);
        }

        pedido.confirmarPedido();
    }

    public void cancelarPedido(int idPedido) {

        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido == null) {
            System.out.println("El pedido no existe");
        }

        if (pedido.getEstado() == Pedido.EstadoPedido.CONFIRMADO) {

            for (int i = 0; i < pedido.getTotalDetalles(); i++) {

                DetallePedido detalle = pedido.getDetalles()[i];

                Producto producto = detalle.getProducto();
                int cantidad = detalle.getCantidad();

                producto.aumentarStock(cantidad);
            }
        }

        pedido.cancelarPedido();
    }

    public void listarProductos() {

        if (totalProductos == 0) {
            System.out.println("No hay productos registrados");
            return;
        }

        for (int i = 0; i < totalProductos; i++) {

            Producto p = productos[i];

            System.out.println("Id: " + p.getId() + " | Nombre: " + p.getNombre() + " | Precio: " + p.getPrecio()
                    + " | Stock: " + p.getStock());
        }
    }

    public void listarPedidos() {

        if (totalPedidos == 0) {
            System.out.println("No hay pedidos registrados.");
            return;
        }

        for (int i = 0; i < totalPedidos; i++) {

            Pedido p = pedidos[i];

            System.out.println("Id Pedido: " + p.getId() + " | Cliente: " + p.getCliente().getNombre() + " | Estado: "
                    + p.getEstado() + " | Total: " + p.calcularTotalFinal());
        }
    }

    public void verDetallePedido(int idPedido) {

        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido == null) {
            System.out.println("Pedido no encontrado");
            return;
        }

        System.out.println("Pedido Id: " + pedido.getId());
        System.out.println("Cliente: " + pedido.getCliente().getNombre());
        System.out.println("Estado: " + pedido.getEstado());
        System.out.println("  Detalles  ");

        for (int i = 0; i < pedido.getTotalDetalles(); i++) {

            DetallePedido detalle = pedido.getDetalles()[i];

            System.out.println(
                    "Producto: " + detalle.getProducto().getNombre() + " | Cantidad: " + detalle.getCantidad()
                            + " | Precio Unitario: " + detalle.getPrecioUnitario() + " | Subtotal: "
                            + detalle.calcularSubtotal());
        }

        System.out.println("Subtotal: " + pedido.calcularSubtotal());
        System.out.println("Descuento: " + pedido.calcularDescuento());
        System.out.println("Total Final: " + pedido.calcularTotalFinal());
    }

}
