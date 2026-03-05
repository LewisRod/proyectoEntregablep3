package proyectoEntregablep3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorClientes {

    private static final String ARCHIVO = "clientes.dat";

    public static synchronized void guardarClientes(List<Cliente> clientes) {

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(ARCHIVO))) {

            for (Cliente c : clientes) {

                out.writeInt(c.getId());
                out.writeUTF(c.getNombre());
                out.writeUTF(c.getDireccion());

               
                if (c instanceof ClienteVIP) {
                    out.writeUTF("VIP");
                } else {
                    out.writeUTF("REGULAR");
                }
            }

        } catch (EOFException e) {
               e.printStackTrace();
            } catch (FileNotFoundException e) {
                System.out.println("Archivo clientes.dat no encontrado.");
            } catch (IOException e) {
                System.out.println("Error de lectura en clientes.dat: " + e.getMessage());
            }
    }

    public static List<Cliente> cargarClientes() {

        List<Cliente> clientes = new ArrayList<>();
        File archivo = new File(ARCHIVO);

        try {

            if (!archivo.exists()) {
                archivo.createNewFile();
                return clientes;
            }

            try (DataInputStream in = new DataInputStream(new FileInputStream(archivo))) {

                while (true) {

                    int id = in.readInt();
                    String nombre = in.readUTF();
                    String direccion = in.readUTF();
                    String tipo = in.readUTF();

                    if (tipo.equals("VIP")) {
                        clientes.add(new ClienteVIP(id, nombre, direccion));
                    } else {
                        clientes.add(new ClienteRegular(id, nombre, direccion));
                    }
                }

            } catch (EOFException e) {
               e.printStackTrace();
            } catch (FileNotFoundException e) {
                System.out.println("Archivo clientes.dat no encontrado.");
            } catch (IOException e) {
                System.out.println("Error de lectura en clientes.dat: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Error al cargar clientes: " + e.getMessage());
        }

        return clientes;
    }

}