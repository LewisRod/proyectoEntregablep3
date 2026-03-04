package proyectoEntregablep3;

public class ProcesadorPedidos extends Thread {

    private SistemaPedidos sistema;
    private volatile boolean activo = true;

    public ProcesadorPedidos(SistemaPedidos sistema) {
        this.sistema = sistema;
    }

    public void detener() {
        activo = false;
    }

    @Override
    public void run() {

        while (activo) {

            sistema.procesarPedidosConfirmados();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("finalizo el procesamiento");
    }
}