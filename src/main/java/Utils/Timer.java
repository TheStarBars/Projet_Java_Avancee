package Utils;

public class Timer extends Thread {
    private static Timer instance;

    public int timeLeft;
    private boolean running;

    private Timer(int minutes){
        this.timeLeft = minutes * 60;
        this.running = true;
    }

    public static void createInstance(int minutes) {
        if (instance == null) {
            instance = new Timer(minutes);
            instance.start();
        }
    }

    public static Timer getInstance() {
        return instance;
    }

    @Override
    public void run() {
        while (running && timeLeft > 0) {
            try {
                Thread.sleep(1000);
                synchronized (this) {
                    timeLeft--;
                }
                System.out.println("Temps restant : " + timeLeft + " secondes");
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public synchronized int getTimeLeft(){
        return timeLeft;
    }

    public synchronized void stopTimer(){
        running = false;
    }

    public boolean canTakeCommande(){
        return timeLeft > 15 * 60;
    }
}
