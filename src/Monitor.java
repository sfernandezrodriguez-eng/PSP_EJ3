/**
 * Clase que comprueba el estado de las descargas, avisando al usuario cuantas quedan por acabar.
 * @author Sergio
 */
public class Monitor extends Thread {

    private final Thread[] hilos;


    public Monitor(Thread[] hilos) {
        this.hilos = hilos;
    }

    /**
     * Metodo en el cual se comprueban si los hilos estan funcionando o no, mediante un bucle mira cada hilo y con
     * .isAlive() comprueba si ya acabo la descarga o no.
     * * @author Sergio
     */
    public int contarHilosVivos() {
        int contador = 0;
        for (Thread t : hilos) {
            if (t != null && t.isAlive()) {
                contador++;
            }
        }
        return contador;
    }


    /**
     * Metodo en el cual se comprueban cuantos hilos estan siendo descargados en ese momento, el programa accede
     * a los hilos comprueba que siguen siendo descargados y pone el mensaje, esta acción se repite cada poco tiempo
     * como comprobante de como van las descargas.
     * * @author Sergio
     */
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