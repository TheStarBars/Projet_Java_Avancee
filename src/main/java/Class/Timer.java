package Class;

public class Timer extends Thread{
    private int timeLeft;
    private  boolean running;

    public Timer(int minutes){
        this.timeLeft = minutes * 60;
    }

    @Override
    public void run() {
        while (true){
            try{
                Thread.sleep(1000);
                synchronized (this){
                    timeLeft--;
                }
                System.out.println();
            } catch (InterruptedException e){
                break;
            }

        }
    }

    public synchronized int getTimeLeft(){
        return timeLeft;
    }

    public synchronized void stopTimer(){
        running=false;
    }
    //@todo change Command -> Oder  Plat -> Dishe later Employe -> Employee
    public boolean canTakeCommande(){
        return getTimeLeft() > 15 * 60;
    }

}
