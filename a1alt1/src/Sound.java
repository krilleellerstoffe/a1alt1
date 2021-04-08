
import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

/**
 * A class for playing a sound clip.
 * @author Christopher O'Driscoll
 * Id: <al0038>
 * Study program: <Systemutvecklare TGSYA20h>
 */
public class Sound implements Runnable {
    private final int BUFFERSIZE = 4096;
    private SourceDataLine line;
    private AudioInputStream currentDecoded;
    private AudioInputStream encoded;
    private AudioFormat encodedFormat;
    private AudioFormat decodedFormat;
    private boolean started = false;
    private boolean stopped = false;
    private boolean paused = false;


    /**
     * Constructs a Sound object specified by the <code>url</code> argument
     *
     * @param url giving the location of the sound file
     */
    private Sound(URL url) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this(AudioSystem.getAudioInputStream(url));
//        encoded = AudioSystem.getAudioInputStream(url);
//        encodedFormat = encoded.getFormat();
//        decodedFormat = new AudioFormat(
//                AudioFormat.Encoding.PCM_SIGNED,  // Encoding to use
//                encodedFormat.getSampleRate(),           // sample rate (same as base format)
//                16,               // sample size in bits (thx to Javazoom)
//                encodedFormat.getChannels(),             // # of Channels
//                encodedFormat.getChannels() * 2,           // Frame Size
//                encodedFormat.getSampleRate(),           // Frame Rate
//                false                 // Big Endian
//        );
//        currentDecoded = AudioSystem.getAudioInputStream(decodedFormat, encoded);
//        line = AudioSystem.getSourceDataLine(decodedFormat);
//        line.open(decodedFormat);
//        setPaused(true);
//        setStopped(false);
    }

    private Sound(AudioInputStream ais) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        encoded = ais;
        encodedFormat = encoded.getFormat();
        decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,  // Encoding to use
                encodedFormat.getSampleRate(),           // sample rate (same as base format)
                16,               // sample size in bits (thx to Javazoom)
                encodedFormat.getChannels(),             // # of Channels
                encodedFormat.getChannels() * 2,           // Frame Size
                encodedFormat.getSampleRate(),           // Frame Rate
                false                 // Big Endian
        );
        currentDecoded = AudioSystem.getAudioInputStream(decodedFormat, encoded);
        line = AudioSystem.getSourceDataLine(decodedFormat);
        line.open(decodedFormat);
        setPaused(true);
        setStopped(false);
    }

    /**
     * Returns a Sound object specified by the <code>filename</code> argument
     *
     * @param filename name of the sound file
     * @return a Sound object if possible, otherwise <code>null</code>
     */
    public static Sound getSound(String filename) {
        Sound sound = null;
        try {
            URL url = new File(filename).toURI().toURL();
            sound = new Sound(url);
        } catch (Exception e) {
            System.out.println(e);
        }
        return sound;
    }

    // AudioSystem.getAudioInputStream(new ByteArrayInputStream(sound));
    public static Sound getSound(byte[] byteSound) {
        Sound sound = null;
        try {
            sound = new Sound(AudioSystem.getAudioInputStream(new ByteArrayInputStream(byteSound)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return sound;
    }
    /*public static void main(String[] args) {
        File file = new File("A1/resources/sounds/music.mp3");
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int b = bis.read();
            while (b != -1) {
                baos.write(b);
                b = bis.read();
            }
            baos.flush();
            byte[] soundArray = baos.toByteArray();
            // Save soundArray in SoundMessage

            // play later
            Sound s = getSound(soundArray);
//	    	Sound s = getSound("sound/horse1.mp3");
            s.play();
        } catch (IOException e) {
        }
    }*/

    /**
     * Begins to play the sound / resumes play back of a sound that is paused. A sound can only be started once.
     */
    public synchronized void play() {
        if (!started) {
            new Thread(this).start();
            started = true;
        }
        setPaused(false);
        notify();
    }

    /**
     * Stops play back of the sound.
     */
    public synchronized void stop() {
        setStopped(true);
        setPaused(false);
        started = false;
        notify();
    }

    /**
     * Pauses play back of the sound.
     */
    public synchronized void pause() {
        setPaused(true);
    }

    private boolean getStopped() {
        return stopped;
    }

    private void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    private boolean getPaused() {
        return paused;
    }

    private void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void run() {
        line.start();
        byte[] b = new byte[BUFFERSIZE];
        int i = 0;
        setStopped(false);
        setPaused(false);
        while (!getStopped()) {
            while (getPaused()) {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        setStopped(true);
                        setPaused(false);
                    }
                }
            }
            try {
                i = currentDecoded.read(b, 0, b.length);
                if (i == -1) {
                    setStopped(true);
                } else
                    line.write(b, 0, i);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        line.drain();
        line.stop();
        line.close();
        try {
            currentDecoded.close();
            encoded.close();
        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println("Sound ended");
    }
}


