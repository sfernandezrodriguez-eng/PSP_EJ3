import java.util.concurrent.ThreadLocalRandom;

public class Descarga implements Runnable{

    private String nombre;
    private int tiempoRandom;

    public Descarga(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempoRandom() {
        return tiempoRandom;
    }

    @Override
    public void run() {

        this.tiempoRandom = ThreadLocalRandom.current().nextInt(1000, 5001);
        for (int i = 0; i < 11; i++) {
            if (i!=10) {
                System.out.println("[" + this.nombre + "] " + i + "0%");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println("[" + this.nombre + "] completada en: " + tiempoRandom+" ms");
            }
        }

    }
}

