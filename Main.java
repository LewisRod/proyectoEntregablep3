package proyectoEntregablep3;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        SistemaPedidos sistema = new SistemaPedidos();

        int opcion;

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
                        System.out.println("Producto registrado correctamente.");
                        break;

                    case 2:
                        System.out.print("Id Cliente: ");
                        int idCliente = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Nombre: ");
                        String nombreCliente = scanner.nextLine();

                        System.out.print("1 = Regular | 2 = VIP: ");
                        int tipo = scanner.nextInt();

                        if (tipo == 1) {
                            sistema.registrarClienteRegular(idCliente, nombreCliente);
                        } 
                        else if (tipo == 2) {
                            sistema.registrarClienteVIP(idCliente, nombreCliente);
                        } else {
                            System.out.println("Tipo invalido.");
                        }

                        System.out.println("Cliente registrado.");
                        break;

                    case 3:
                        System.out.print("ID Pedido: ");
                        int idPedido = scanner.nextInt();

                        System.out.print("ID Cliente: ");
                        int idCli = scanner.nextInt();

                        sistema.crearPedido(idPedido, idCli);
                        System.out.println("Pedido creado correctamente.");
                        break;

                    case 4:
                        System.out.print("ID Pedido: ");
                        int idPed = scanner.nextInt();

                        System.out.print("ID Producto: ");
                        int idProducto = scanner.nextInt();

                        System.out.print("Cantidad: ");
                        int cantidad = scanner.nextInt();

                        sistema.agregarProductoAPedido(idPed, idProducto, cantidad);
                        System.out.println("Producto agregado al pedido.");
                        break;

                    case 5:
                        System.out.print("ID Pedido: ");
                        int idConfirmar = scanner.nextInt();

                        sistema.confirmarPedido(idConfirmar);
                        System.out.println("Pedido confirmado.");
                        break;

                    case 6:
                        System.out.print("ID Pedido: ");
                        int idCancelar = scanner.nextInt();

                        sistema.cancelarPedido(idCancelar);
                        System.out.println("Pedido cancelado.");
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

                    case 0:
                        System.out.println("FIN DEL PROGRAMA");
                        break;

                    default:
                        System.out.println("Opcion invalida.");
                }

            } catch (ProductoNoEncontradoException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (StockInsuficienteException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (PedidoInvalidoException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Error de datos: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());

            } finally {
                System.out.println("Operacion finalizada.\n");
            }

        } while (opcion != 0);

        scanner.close();
    }
}
