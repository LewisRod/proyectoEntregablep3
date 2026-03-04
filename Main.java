package proyectoEntregablep3;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        SistemaPedidos sistema = new SistemaPedidos();

        ProcesadorPedidos procesador = new ProcesadorPedidos(sistema);
        Thread hiloProcesador = new Thread(procesador);

        GeneradorReportes generador = new GeneradorReportes(sistema);
        Thread hiloReportes = new Thread(generador);

        hiloReportes.setDaemon(true);
    
        hiloProcesador.start();
        hiloReportes.start();

        Scanner scanner = new Scanner(System.in);

        int opcion = -1;

        do {

            System.out.println("\n ---- SISTEMA DE GESTION DE PEDIDOS ----\n");
            System.out.println("1. Registrar producto");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Crear pedido");
            System.out.println("4. Agregar producto a pedido");
            System.out.println("5. Confirmar pedido");
            System.out.println("6. Cancelar pedido");
            System.out.println("7. Listar productos");
            System.out.println("8. Listar clientes");
            System.out.println("9. Listar pedidos");
            System.out.println("10. Guardar sistema manualmente");
            System.out.println("11. Cargar sistema desde archivos");
            System.out.println("12. Generar reporte del sistema");
            System.out.println("13. Ver estado de procesamiento");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            try {

                switch (opcion) {

                    case 1:
                        System.out.print("Id: ");
                        int idProd = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Nombre: ");
                        String nombreProd = scanner.nextLine();

                        System.out.print("Precio: ");
                        double precio = scanner.nextDouble();

                        System.out.print("Stock: ");
                        int stock = scanner.nextInt();

                        sistema.registrarProducto(idProd, nombreProd, precio, stock);
                        break;

                    case 2:
                        System.out.print("Id Cliente: ");
                        int idCliente = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Nombre: ");
                        String nombreCliente = scanner.nextLine();

                        System.out.print("Direccion: ");
                        String direccionCliente = scanner.nextLine();

                        System.out.print("1 = Regular | 2 = VIP: ");
                        int tipo = scanner.nextInt();

                        if (tipo == 1) {
                            sistema.registrarClienteRegular(idCliente, nombreCliente, direccionCliente);
                        } else if (tipo == 2) {
                            sistema.registrarClienteVIP(idCliente, nombreCliente, direccionCliente);
                        }
                        break;

                    case 3:
                        System.out.print("Id Pedido: ");
                        int idPedido = scanner.nextInt();

                        System.out.print("Id Cliente: ");
                        int idCli = scanner.nextInt();

                        sistema.crearPedido(idPedido, idCli);
                        break;

                    case 4:
                        System.out.print("Id Pedido: ");
                        int idPed = scanner.nextInt();

                        System.out.print("Id Producto: ");
                        int idProducto = scanner.nextInt();

                        System.out.print("Cantidad: ");
                        int cantidad = scanner.nextInt();

                        sistema.agregarProductoAPedido(idPed, idProducto, cantidad);
                        break;

                    case 5:
                        System.out.print("Id Pedido: ");
                        int idConfirmar = scanner.nextInt();
                        sistema.confirmarPedido(idConfirmar);
                        break;

                    case 6:
                        System.out.print("Id Pedido: ");
                        int idCancelar = scanner.nextInt();
                        sistema.cancelarPedido(idCancelar);
                        break;

                    case 7:
                        sistema.listarProductos();
                        break;

                    case 8:
                        sistema.listarClientes();
                        break;

                    case 9:
                        sistema.listarPedidos();
                        break;

                    case 10:
                        sistema.guardarTodo();
                        System.out.println("Sistema guardado manualmente.");
                        break;

                    case 11:
                        sistema.recargarSistema();
                        break;

                    case 12:
                        String reporte = sistema.generarReporteConsolidado();
                        GestorReportesSistema.generarReporte(reporte);
                        System.out.println("Reporte generado correctamente.");
                        break;

                    case 13:
                        System.out.println(sistema.obtenerEstadoProcesamiento());
                        break;

                    case 0:
                        System.out.println("Cerrando sistema...");

                        procesador.detener();
                        hiloProcesador.join();

                        sistema.guardarTodo();

                        System.out.println("Sistema cerrado correctamente.");
                        break;

                    default:
                        System.out.println("Opcion invalida.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);

        scanner.close();
    }
}