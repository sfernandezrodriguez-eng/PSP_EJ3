/**
 * Clase que comprueba la descarga de mantra.mp3 y meditacion.mp4, una vez ambas ya han sido descargadas
 * aparecerá un mensaje de instalación.
 * @author Sergio
 */
public class Instalador extends Thread{

    private final Thread hiloMeditacion;
    private final Thread hiloMantras;

    public Instalador(Thread hiloMeditacion, Thread hiloMantras) {
        this.hiloMeditacion = hiloMeditacion;
        this.hiloMantras = hiloMantras;
    }

    /**
     * Método en el cúal se comprueban cuando los hilos de mantra.mp3 y meditación.mp4 estén descargados(avisa si falta 1 o ambos en descargarse),
     * una vez descargado, el programa procederá a poner un mensaje en pantalla de que se están instalando y después lo habrá
     *"instalado".
     * * @author Sergio
     */
    @Override
    public void run() {
        try {
            hiloMantras.join();
            try {
                hiloMeditacion.join();
                System.out.println("[Instalador] meditación y mantras listos: instalando...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("[Instalador] Instalación terminada");
    }
}
