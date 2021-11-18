import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlayMusic {
    public static void playMusic(String filepath) {
        try{
            File musicPath = new File(filepath);
            if (musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filepath));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else {
                System.out.println("Cant find the music");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
