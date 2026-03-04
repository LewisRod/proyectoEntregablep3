package proyectoEntregablep3;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GestorPedidosTexto {

    SistemaPedidos sistemaPedidos = new SistemaPedidos();
    private static final String ARCHIVO = "pedidos.txt";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static synchronized void guardarPedidos(List<Pedido> pedidos) {

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(ARCHIVO))) {

            for (Pedido p : pedidos) {

                String linea = p.getId() + "|" + p.getCliente().getId() + "|" + p.getFechaFormateada() + "|"
                        + p.getEstado() + "|" + p.calcularTotalFinal();
                printWriter.println(linea);
            }

        } catch (IOException e) {
            System.out.println("hubo error al guardar pedidos: " + e.getMessage());
        }
    }

    public static void cargarPedidos(SistemaPedidos sistema) {

        File archivo = new File("pedidos.txt");

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("No se pudo crear pedidos.txt");
            }
            return;
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = bufferedReader.readLine()) != null) {

                String[] partes = linea.split("\\|");

                int idPedido = Integer.parseInt(partes[0]);
                int idCliente = Integer.parseInt(partes[1]);
                String fecha = partes[2];
                String estado = partes[3];

                sistema.reconstruirPedido(idPedido, idCliente, fecha, estado);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Archivo pedidos.txt no encontrado.");
        } catch (IOException e) {
            System.out.println("Error al leer pedidos.txt: " + e.getMessage());
        }
    }
}