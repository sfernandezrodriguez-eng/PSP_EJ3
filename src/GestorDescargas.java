


void main(String[] args) {

    Descarga d1 = new Descarga("cuarzos.png");
    Descarga d2 = new Descarga("meditacion.mp4");
    Descarga d3 = new Descarga("mantras.mp3");
    Descarga d4 = new Descarga("horscopo.pdf");

    Thread t1 = new Thread(d1);
    Thread t2 = new Thread(d2);
    Thread t3 = new Thread(d3);
    Thread t4 = new Thread(d4);

    t1.start();
    t2.start();
    t3.start();
    t4.start();

    try {
        t1.join();
        t2.join();
        t3.join();
        t4.join();

    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
    Descarga masLenta = Stream.of(d1, d2, d3, d4).max(Comparator.comparingDouble(Descarga::getTiempoRandom)).orElse(d1);
    int suma = ThreadLocalRandom.current().nextInt(5, 15);
    System.out.println("Tiempo real: "+ (masLenta.getTiempoRandom()+suma) + " ms");

}

