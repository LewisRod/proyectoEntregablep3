package proyectoEntregablep3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorProductos {

    private static final String ARCHIVO = "productos.dat";

    public static synchronized void guardarProductos(List<Producto> productos) {

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("productos.dat"))) {

            for (Producto p : productos) {
                out.writeInt(p.getId());
                out.writeUTF(p.getNombre());
                out.writeDouble(p.getPrecio());
                out.writeInt(p.getStock());
            }

        } catch (IOException e) {
            System.out.println("Error escribiendo productos.dat");
        }
    }

    public static List<Producto> cargarProductos() {

        List<Producto> productos = new ArrayList<>();
        File archivo = new File("productos.dat");

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("No se pudo crear productos.dat");
            }
            return productos;
        }

        try (DataInputStream in = new DataInputStream(new FileInputStream(archivo))) {

            while (true) {

                int id = in.readInt();
                String nombre = in.readUTF();
                double precio = in.readDouble();
                int stock = in.readInt();

                productos.add(new Producto(id, nombre, precio, stock));
            }

        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error leyendo productos.dat");
        }

        return productos;
    }
}