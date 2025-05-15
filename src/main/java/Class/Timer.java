package Class;

public class Timer extends Thread{
    private static Timer instance;
    private int timeLeft;
    private  boolean running;
    private Runnable onUpdate;

    public Timer(int minutes,Runnable onUpdate){
        this.timeLeft = minutes * 60;
        this.onUpdate = onUpdate;

    }
    public static Timer getInstance(int minutes, Runnable onUpdate) {
        if (instance == null) {
            instance = new Timer(minutes, onUpdate);
            instance.start();
        } else {
            instance.onUpdate = onUpdate; // Update the callback for new screen
        }
        return instance;
    }

    @Override
    public void run() {
        running = true; // start the loop
        while (running && timeLeft > 0) {
            try {
                Thread.sleep(1000);
                synchronized (this) {
                    timeLeft--;
                }
                if (onUpdate != null) onUpdate.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public synchronized int getTimeLeft() {
        return Math.max(timeLeft, 0);
    }


    public synchronized void stopTimer(){
        running=false;
    }
    //@todo change Command -> Oder  Plat -> Dishe later Employe -> Employee
    public boolean canTakeCommande(){
        return getTimeLeft() > 15 * 60;
    }

}
