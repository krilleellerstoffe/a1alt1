/**
 * Class that can start and stop the playing of a sound in it's own thread
 * @author Christopher O'Driscoll
 * Id: <al0038>
 * Study program: <Systemutvecklare TGSYA20h>
 */
public class MusicPlayer implements Runnable{

    private Thread thread;
    private boolean playing;

    private Sound sound;
    private String fileName;

    /**
     * When thread is started, a sound object is created from file
     * Sound class is then used to play the sound
     */
    @Override
    public void run() {
        sound = Sound.getSound(fileName);
        playing = true;
        while (playing) {
            sound.play();
        }
        sound.stop();
    }

    /**
     * Stores the location of selected mp3
     * @param fileName location of mp3
     */
    public void setSound(String fileName) {
        this.fileName = fileName;
    }

    public void pause() {
        sound.pause();
    }

    /**
     * Begins playing of sound, by creating a new thread and starting the run() method
     */
    public void startPlay() {
        thread = new Thread (this);
        thread.start();
    }

    /**
     * Stops playing of sound.
     * Condition for thread to continue is changed, ultimately stopping the thread
     */
    public void stopPlay() {
        playing = false;
    }
}
