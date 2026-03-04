package proyectoEntregablep3;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GestorReportesSistema {

    private static final String ARCHIVO = "reporte_sistema.txt";

    public static synchronized void generarReporte(String contenido) {

        try (PrintWriter printWriter = new PrintWriter(new FileWriter("reporte_sistema.txt", true))) {

            printWriter.println(contenido);

        } catch (IOException e) {
            System.out.println("Error generando reporte_sistema.txt: " + e.getMessage());
        }
    }
}