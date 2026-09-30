
public class Monitor extends Thread {

    private final Thread[] hilos;


    public Monitor(Thread[] hilos) {
        this.hilos = hilos;
    }


    public int contarHilosVivos() {
        int contador = 0;
        for (Thread t : hilos) {
            if (t != null && t.isAlive()) {
                contador++;
            }
        }
        return contador;
    }


    @Override
    public void run() {
        int vivos = contarHilosVivos();

        while (vivos > 0) {
            System.out.println("[Monitor] Descargas en curso: " + vivos);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Monitor interrumpido", e);
            }
            vivos = contarHilosVivos();
        }

        System.out.println("[Monitor] No queda ninguna descarga en curso");
    }
}