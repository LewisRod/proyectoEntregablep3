package proyectoEntregablep3;

import java.util.Scanner;

public class Main {
    static void main() {

        Scanner scanner = new Scanner(System.in);

        SistemaPedidos sistema = new SistemaPedidos(50, 50, 50);

        int opcion;

        do {
            System.out.println("\n SISTEMA DE GESTION DE PEDIDOS\n");
            System.out.println("1. Registrar producto");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Crear pedido");
            System.out.println("4. Agregar producto a pedido");
            System.out.println("5. Ver detalle de pedido");
            System.out.println("6. Listar productos");
            System.out.println("7. Listar pedidos");
            System.out.println("8. Cambiar estado de pedido");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    System.out.print("ID: ");
                    int idProd = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nombre: ");
                    String nombreProd = scanner.nextLine();

                    System.out.print("Precio: ");
                    double precio = scanner.nextDouble();

                    System.out.print("Stock: ");
                    int stock = scanner.nextInt();

                    sistema.registrarProducto(idProd, nombreProd, precio, stock);
                    System.out.println("Producto registrado.");
                    break;

                case 2:
                    System.out.print("Id Cliente: ");
                    int idCliente = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nombre: ");
                    String nombreCliente = scanner.nextLine();

                    System.out.print(" pulse  1 Regular o 2 VIP: ");
                    int tipo = scanner.nextInt();

                    if (tipo == 1) {
                        sistema.registrarClienteRegular(idCliente, nombreCliente);
                    } else if (tipo == 2) {
                        sistema.registrarClienteVIP(idCliente, nombreCliente);
                    } else {
                        System.out.println("Tipo invalido.");
                    }

                    System.out.println("Cliente registrado.");
                    break;

                case 3:
                    System.out.print("Id Pedido: ");
                    int idPedido = scanner.nextInt();

                    System.out.print("Id Cliente: ");
                    int idCliPedido = scanner.nextInt();

                    sistema.crearPedido(idPedido, idCliPedido);
                    System.out.println("Pedido creado.");
                    break;

                case 4:
                    System.out.print("Id Pedido: ");
                    int idPed = scanner.nextInt();

                    System.out.print("Id Producto: ");
                    int idProducto = scanner.nextInt();

                    System.out.print("Cantidad: ");
                    int cantidad = scanner.nextInt();

                    sistema.agregarProductoAPedido(idPed, idProducto, cantidad);
                    System.out.println("Producto agregado al pedido.");
                    break;

                case 5:
                    System.out.print("Id Pedido: ");
                    int idVer = scanner.nextInt();
                    sistema.verDetallePedido(idVer);
                    break;

                case 6:
                    sistema.listarProductos();
                    break;

                case 7:
                    sistema.listarPedidos();
                    break;

                case 8:
                    System.out.print("Id Pedido: ");
                    int idCambio = scanner.nextInt();

                    System.out.print(" pulse 1 para Confirmar o 2 Cancelar: ");
                    int accion = scanner.nextInt();

                    if (accion == 1) {
                        sistema.confirmarPedido(idCambio);
                        System.out.println("Pedido confirmado.");
                    } else if (accion == 2) {
                        sistema.cancelarPedido(idCambio);
                        System.out.println("Pedido cancelado.");
                    } else {
                        System.out.println("Opcion invalida.");
                    }
                    break;

                case 0:
                    System.out.println("FIN DE LA GESTION DE PRODUCTOS");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 0);
    }
}