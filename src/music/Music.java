package music;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Music implements Runnable {

    public Music() {

    }

    @Override
    public void run() {
        while (true) {
            try {

                FileInputStream f = new FileInputStream("res/John Williams - Main Title and Escape.mp3");
                try {
                    Player player = new Player(f);
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        f.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
