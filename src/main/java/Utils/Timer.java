package Utils;

/**
 * A singleton class representing a countdown timer that runs in a separate thread.
 * It is used to manage time-based restrictions in the application, such as when commands can be taken.
 */
public class Timer extends Thread {

    private static Timer instance;

    public int timeLeft;
    private boolean running;

    /**
     * Private constructor to initialize the timer with a specific duration in minutes.
     *
     * @param minutes the initial time in minutes for the countdown
     */
    private Timer(int minutes) {
        this.timeLeft = minutes * 60;
        this.running = true;
    }

    /**
     * Creates and starts a singleton instance of the timer if it doesn't already exist.
     *
     * @param minutes the initial time in minutes for the timer
     */
    public static void createInstance(int minutes) {
        if (instance == null) {
            instance = new Timer(minutes);
            instance.start();
        }
    }

    /**
     * Retrieves the singleton instance of the Timer.
     *
     * @return the current Timer instance
     */
    public static Timer getInstance() {
        return instance;
    }

    /**
     * Runs the countdown in a background thread, decreasing the time every second.
     * Stops automatically when time reaches zero or when manually stopped.
     */
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

    /**
     * Returns the remaining time in seconds.
     *
     * @return the number of seconds left in the countdown
     */
    public synchronized int getTimeLeft() {
        return timeLeft;
    }

    /**
     * Determines whether a command can still be taken based on the remaining time.
     *
     * @return true if more than 15 minutes are left, false otherwise
     */
    public boolean canTakeCommande() {
        return timeLeft > 15 * 60;
    }
}
