package testScript;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class testMusic {
	static Clip c1;
	static Clip c2;
	static Clip c3;
	static Clip c4;
	static Clip c5;
	AudioInputStream audioInputStream;
	public testMusic(){
		try {
			audioInputStream = AudioSystem
					.getAudioInputStream(new File("chooserDisplayMusic.wav").getAbsoluteFile());
			c1 = AudioSystem.getClip();
			c1.open(audioInputStream);
			audioInputStream = AudioSystem
					.getAudioInputStream(new File("displayMusic.wav").getAbsoluteFile());
			c2 = AudioSystem.getClip();
			c1.open(audioInputStream);
			audioInputStream = AudioSystem
					.getAudioInputStream(new File("introMusic.wav").getAbsoluteFile());
			c3 = AudioSystem.getClip();
			c1.open(audioInputStream);
			audioInputStream = AudioSystem
					.getAudioInputStream(new File("rainSound.wav").getAbsoluteFile());
			c4 = AudioSystem.getClip();
			c1.open(audioInputStream);
			audioInputStream = AudioSystem
					.getAudioInputStream(new File("trumpetSound.wav").getAbsoluteFile());
			c5 = AudioSystem.getClip();
			c1.open(audioInputStream);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		new testMusic();
		c1.start();
		//c2.start();
		//c3.start();
		//c4.start();
		//c5.start();
	}
}
