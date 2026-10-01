public class Instalador extends Thread{

    private final Thread hiloMeditacion;
    private final Thread hiloMantras;


    public Instalador(Thread hiloMeditacion, Thread hiloMantras) {
        this.hiloMeditacion = hiloMeditacion;
        this.hiloMantras = hiloMantras;
    }


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
