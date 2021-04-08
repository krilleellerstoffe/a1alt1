import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * A button that moves around the frame, bouncing with slightly random directions
 * @author Christopher O'Driscoll
 * Id: <al0038>
 * Study program: <Systemutvecklare TGSYA20h>
 */

public class CatchMeButton extends JButton implements Runnable {

    private Thread thread;
    private boolean gameRunning;
    private int delay = 20; //Initial delay (ms between movement to new position)
    private int hits = 0;   //number of successful hits by user

    private int x = new Random().nextInt(233); //Initialise buttons location at
    private int y = new Random().nextInt(235); //random location within frame

    private boolean increaseX = true;   //Denotes whether the button is moving left/right
    private boolean increaseY = true;   //Denotes whether the button is moving up/down
    private int XVector = 1;            //Angle of movement, in vertical/horizontal pixels
    private int YVector = 1;            //-> (1, 1) = 45 degrees

    /**
     * Constructor creates a 50x50pixel button
     */
    public CatchMeButton() {

        ImageIcon imageIcon = new ImageIcon("A1/resources/images/horse.jpg");
        imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(50, 50, 0));
        setIcon(imageIcon);
        setPreferredSize(new Dimension(50, 50));
        setVisible(false);
    }

    /**
     * When class is run in thread, the location is reset over and over again.
     * Time between resets determined by delay field
     * Runs while gameRunning is  true
     */
    @Override
    public void run() {
        setVisible(true);
        gameRunning = true;
        while (gameRunning) {
            moveButton();
            try {
                thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * New random location of button within frame
     */
    public void setRandomLocation() {
        x = new Random().nextInt(233);
        y = new Random().nextInt(235);
        setLocation(x,y);
    }
    /**
     * Moves the button
     * Randomises x/y direction a little
     * with each bounce
     */

    public void moveButton() {

        if(increaseX){
            x+= XVector;
            if(x>=233) {
                XVector = new Random().nextInt(3)+1;
                increaseX = false;
            }
        } else {
            x-= XVector;
            if(x<=0) {
                XVector = new Random().nextInt(3)+1;
                increaseX = true;
            }
        }
        if(increaseY){
            y++;
            if(y==235) {
                YVector = new Random().nextInt(3)+1;
                increaseY = false;
            }
        } else {
            y--;
            if(y==0) {
                YVector = new Random().nextInt(3)+1;
                increaseY = true;
            }
        }
        setLocation(x, y);
    }

    /**
     * Starts the game by creating a new thread and executing the run() method
     */
    public void startGame() {
        setRandomLocation();
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops game.
     * Sets gameRunning to false, stopping thread's execution
     */
    public void stopGame() {
        gameRunning = false;
        setVisible(false);
    }

    /**
     * Get number of times button has been hit
     * @return
     */
    public int getHits() {
        return hits;
    }

    /**
     * Increases hit count by one
     */
    public void increaseHits() {
        hits++;
    }

    /**
     * Change delay amount
     * @param delay ms between button refresh
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }
}
