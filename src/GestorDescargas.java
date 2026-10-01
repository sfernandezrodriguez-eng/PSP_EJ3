import java.util.Arrays;

/**
 * Clase principal que gestiona la ejecución concurrente de las descargas,
 * el monitor de estado y el instalador.
 */
public class GestorDescargas {

    public static void main(String[] args) {
        // Nivel 2: Nombres por línea de comandos o valores por defecto
        String[] nombres;
        if (args != null && args.length > 0) {
            nombres = args;
        } else {
            nombres = new String[]{"cuarzos.png", "meditacion.mp4", "mantras.mp3", "horoscopo.pdf"};
        }

        // Creación de objetos y referencias de hilos
        Descarga[] descargas = new Descarga[nombres.length];
        Thread[] hilos = new Thread[nombres.length];

        Thread hiloMeditacion = null;
        Thread hiloMantras = null;

        for (int i = 0; i < nombres.length; i++) {
            descargas[i] = new Descarga(nombres[i]);
            hilos[i] = new Thread(descargas[i], "Descarga-" + nombres[i]);

            // Referencias para el Instalador y la espera con límite (Nivel 3)
            if ("meditacion.mp4".equals(nombres[i])) {
                hiloMeditacion = hilos[i];
            } else if ("mantras.mp3".equals(nombres[i])) {
                hiloMantras = hilos[i];
            }
        }

        // Creación del Monitor (Nivel 2) e Instalador (Nivel 3)
        Monitor monitor = new Monitor(hilos);
        Instalador instalador = new Instalador(hiloMeditacion, hiloMantras);

        // Marca de tiempo de inicio
        long tiempoInicio = System.currentTimeMillis();

        // Inicio de la ejecución de todos los hilos
        for (Thread t : hilos) {
            t.start();
        }
        monitor.start();
        instalador.start();

        // Sincronización y esperas (join)
        try {
            // Nivel 3: El hilo Main espera un máximo de 3000 ms por meditacion.mp4
            if (hiloMeditacion != null) {
                hiloMeditacion.join(3000);
                if (hiloMeditacion.isAlive()) {
                    System.out.println("[Main] meditacion.mp4 sigue en segundo plano");
                }
            }

            // Esperar a que el resto de hilos de descarga terminen
            for (Thread t : hilos) {
                t.join();
            }

            // Esperar a que terminen monitor e instalador
            monitor.join();
            instalador.join();

        } catch (InterruptedException e) {
            System.err.println("Error durante la sincronización: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        // Cálculo de tiempos final
        long tiempoFin = System.currentTimeMillis();
        long tiempoReal = tiempoFin - tiempoInicio;

        // Suma de tiempos si se hicieran de forma secuencial
        int acumuladoSerie = 0;
        for (Descarga d : descargas) {
            acumuladoSerie += d.getTiempoTotal();
        }

        // Informe final
        System.out.println("Todas las descargas han terminado.");
        System.out.println("Tiempo real: " + tiempoReal + " ms");
        System.out.println("Si se hubieran descargado una detrás de otra: " + acumuladoSerie + " ms");
    }
}
