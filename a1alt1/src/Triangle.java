import javax.swing.*;
import java.awt.*;

/**
 * Draws a triangle graphic, rotates graphic while running
 * @author Christopher O'Driscoll
 * Id: <al0038>
 * Study program: <Systemutvecklare TGSYA20h>
 */
public class Triangle extends JPanel implements Runnable {

    private Thread thread;
    private boolean running;

    /**
     * Three (x, y) locations represent the three points on a triangle
     */
    private int[] xPoints = {32, 90, 148};
    private int[] yPoints = {140, 40, 140};

    private int angle = 0; //As angle is changed, so does the rotation of the triangle (max 360)

    public Triangle() {
        setPreferredSize(new Dimension(180, 180));
    }

    /**
     * Draws the triangle, taking into account the angle
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(angle), this.getWidth()/2, this.getHeight()/2);// sets the angle of rotation around centre of panel
        g2d.drawPolygon(xPoints, yPoints, 3);
        g2d.dispose();
    }

    /**
     * Begins rotation of triangle.
     * Creates a new thread and executes the run() method
     */
    public void rotate() {
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    /**
     * While thread is active, the angle of rotation is changed by one degree every 10 ms
     * Triangle is redrawn each loop
     */
    @Override
    public void run() {
        while (running) {
            try {
                thread.sleep(10);
                angle++;
                if (angle >= 360) {
                    angle = 0;
                }
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops rotation by changing condition needed to continue thread loop.
     */
    public void stopRotation() {
        running = false;
    }
}


