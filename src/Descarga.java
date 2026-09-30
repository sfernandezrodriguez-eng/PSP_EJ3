import java.util.concurrent.ThreadLocalRandom;


public class Descarga implements Runnable {


    private static final int AJUSTE_BLOQUE = 1;
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
    @Override
    public void run() {
        int tiempoEfectivoBloque = this.tiempoBloque * AJUSTE_BLOQUE;
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