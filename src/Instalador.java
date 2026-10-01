/**
 * Clase que comprueba la descarga de mantra y meditacion, una vez ambas ya han sido descargadas
 * aparecera un mensaje de instalación.
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
     * Metodo en el cual se comprueban cuando los hilos de mantra y meditación esten descaragdos(avisa si falta 1 o ambos en descargarse),
     * una vez descargado, el programa procedera a poner un mensaje en pantalla de que se están instalando y despues lo habra
     *"instalado".
     * * @author Sergio
     */
    @Override
    public void run() {
        try {
            hiloMantras.join();
            try {
                hiloMeditacion.join();
                System.out.println("[Instalador] Meditación y mantras listos: instalando...");
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
