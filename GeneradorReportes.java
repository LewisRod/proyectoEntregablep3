package proyectoEntregablep3;

import java.util.List;

public class GeneradorReportes extends Thread {

    private SistemaPedidos sistema;
    private volatile boolean activo = true;

    public GeneradorReportes(SistemaPedidos sistema) {
        this.sistema = sistema;
    }

    public void detener() {
        activo = false;
    }

    @Override
public void run() {

    while (activo) {

        try {

            Thread.sleep(10000);

            String reporte = sistema.generarReporteGeneral();

            GestorReportesSistema.generarReporte(reporte);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

    private void generarReporte(List<Pedido> pedidos) {

        int total = pedidos.size();
        int confirmados = 0;
        int procesados = 0;
        int cancelados = 0;

        for (Pedido p : pedidos) {

            if (p.getEstado() == Pedido.EstadoPedido.CONFIRMADO) {
                confirmados++;
            }

            if (p.getEstado() == Pedido.EstadoPedido.PROCESADO) {
                procesados++;
            }

            if (p.getEstado() == Pedido.EstadoPedido.CANCELADO) {
                cancelados++;
            }
        }

        escribirArchivo(total, confirmados, procesados, cancelados);
    }

    private synchronized void escribirArchivo(
            int total,
            int confirmados,
            int procesados,
            int cancelados) {

    }
}