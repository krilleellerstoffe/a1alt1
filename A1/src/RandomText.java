import javax.swing.*;
import java.util.Random;

/**
 * Label that appears at random locations within a frame
 * @author Christopher O'Driscoll
 * Id: <al0038>
 * Study program: <Systemutvecklare TGSYA20h>
 */
class RandomText extends JLabel implements Runnable {

    private Thread thread;
    private boolean running;

    /**
     * Creates label and initialises text
     * @param text
     */
    public RandomText (String text) {
        this.setText(text);
    }

    /**
     * Executes continuously while 'running' condition is true
     * Creates a new random location for the button
     * and resets every 200ms
     */
    @Override
    public void run() {
        running = true;
        while(running) {
            int x = new Random().nextInt(200);
            int y = new Random().nextInt(200);
            setLocation(x, y);
            try {
                thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops randomisation.
     * Condition for thread to continue is changed, ultimately stopping the thread
     */
    public void stopRunning() {
        running = false;
    }

    /**
     * Randomises location of text.
     * Creates a new thread and executes run() method
     */
    public void randomise() {
        thread = new Thread(this);
        thread.start();
    }
}
