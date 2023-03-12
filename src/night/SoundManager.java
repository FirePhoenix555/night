package night;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
	public SoundManager() {
		//
	}
	
	// https://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator
	// https://stackoverflow.com/questions/12917297/using-a-url-with-audioinputstream
	public void play(String file) {
		try {
			InputStream is = ClassLoader.getSystemResource(file).openStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
