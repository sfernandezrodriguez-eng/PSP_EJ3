import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase que ejecuta la descarga de los elementos y cada x tiempo avisa el porcentaje de la decraga,
 * a parte de esto al final pone tiempo final.
 * @author Sergio
 */
public class Descarga implements Runnable {

    private final String nombre;
    private final int tiempoBloque;
    private int tiempoTotal;

    public Descarga(String nombre) {
        this.nombre = nombre;
        // Elección aleatoria entre 100 y 500 ms por cada bloque
        this.tiempoBloque = ThreadLocalRandom.current().nextInt(100, 501);
    }

    public int getTiempoTotal() {
        return tiempoTotal;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo en el cual se hace la descarga, primero se calcula con timepoEfectivoBloque cuanto va a
     * tardar la descarga despues cada x tiempo se imprime como va la descraga y al final sale el tiempo de la descarga".
     * * @author Sergio
     */
    @Override
    public void run() {
        int tiempoEfectivoBloque = this.tiempoBloque;
        this.tiempoTotal = tiempoEfectivoBloque * 10;

        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(tiempoEfectivoBloque);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Descarga interrumpida: " + nombre, e);
            }

            if (i < 10) {
                System.out.println("[" + this.nombre + "] " + (i * 10) + "%");
            } else {
                System.out.println("[" + this.nombre + "] completada en " + this.tiempoTotal + " ms");
            }
        }
    }
}