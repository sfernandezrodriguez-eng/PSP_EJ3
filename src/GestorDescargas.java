import java.util.Arrays;

public class GestorDescargas {

    public static void main(String[] args) {
        // Nivel 2:
        String[] nombres;

        // Comprobación para los nombres predeterminados
        if (args != null && args.length > 0) {
            nombres = args;
        } else {
            nombres = new String[]{"cuarzos.png", "meditacion.mp4", "mantras.mp3", "horoscopo.pdf"};
        }

        //Creación de los hilos
        Descarga[] descargas = new Descarga[nombres.length];
        Thread[] hilos = new Thread[nombres.length];


        Thread hiloMeditacion = null;
        Thread hiloMantras = null;

        //Crear la descarga y configurar los hilos con meditacion y mantras
        for (int i = 0; i < nombres.length; i++) {
            descargas[i] = new Descarga(nombres[i]);
            hilos[i] = new Thread(descargas[i], "Descarga-" + nombres[i]);

            if ("meditacion.mp4".equals(nombres[i])) {
                hiloMeditacion = hilos[i];
            } else if ("mantras.mp3".equals(nombres[i])) {
                hiloMantras = hilos[i];
            }
        }

        //Creacion del Monitor y El Instalador
        Monitor monitor = new Monitor(hilos);
        Instalador instalador = new Instalador(hiloMeditacion, hiloMantras);

        long tiempoInicio = System.currentTimeMillis();

        //Para iniciar los hilos
        for (Thread t : hilos) {
            t.start();
        }
        monitor.start();
        instalador.start();

        try {
            // Nivel 3:
            if (hiloMeditacion != null) {
                hiloMeditacion.join(3000);
                if (hiloMeditacion.isAlive()) {
                    System.out.println("[Main] meditacion.mp4 sigue en segundo plano");
                }
            }

            if (hiloMantras != null) {
                hiloMantras.join(3000);
                if (hiloMantras.isAlive()) {
                    System.out.println("[Main] mantras.mp3 sigue en segundo plano");
                }
            }

            for (Thread t : hilos) {
                t.join();
            }
            monitor.join();
            instalador.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error en la sincronización de los hilos", e);
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