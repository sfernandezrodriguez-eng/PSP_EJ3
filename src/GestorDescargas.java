import java.util.Arrays;


public class GestorDescargas {

    public static void main(String[] args) {
        String[] nombres = (args != null && args.length > 0)
                ? args
                : new String[]{"cuarzos.png", "meditacion.mp4", "mantras.mp3", "horoscopo.pdf"};

        Descarga[] descargas = new Descarga[nombres.length];
        Thread[] hilos = new Thread[nombres.length];

        for (int i = 0; i < nombres.length; i++) {
            descargas[i] = new Descarga(nombres[i]);
            hilos[i] = new Thread(descargas[i], "Descarga-" + nombres[i]);
        }

        Monitor monitor = new Monitor(hilos);

        long tiempoInicio = System.currentTimeMillis();

        // Arrancar hilos de descarga y monitor
        for (Thread t : hilos) {
            t.start();
        }
        monitor.start();

        // Esperar a que todos los hilos completen su trabajo
        try {
            for (Thread t : hilos) {
                t.join();
            }
            monitor.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error en la sincronización de hilos", e);
        }

        long tiempoFin = System.currentTimeMillis();
        long tiempoReal = tiempoFin - tiempoInicio;

        int acumuladoSerie = Arrays.stream(descargas)
                .mapToInt(Descarga::getTiempoTotal)
                .sum();

        System.out.println("Todas las descargas han terminado.");
        System.out.println("Tiempo real: " + tiempoReal + " ms");
        System.out.println("Si se hubieran descargado una detrás de otra: " + acumuladoSerie + " ms");
    }
}