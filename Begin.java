import myruguelike.screens.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

import asciiPanel.AsciiPanel;

import asciiPanel.AsciiFont;

import java.io.*;

public class Begin extends JFrame implements KeyListener {
	private static final long serialVersionUID = 191220059L;
	
	private AsciiPanel terminal;
	private Screen screen;
	
	public class musicStuff {
		void playMusic(String musicLocation)
		{
			try
			{
				File musicPath = new File(musicLocation);
				
				if(musicPath.exists())
				{
					AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
					Clip clip = AudioSystem.getClip();
					clip.open(audioInput);
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
				else
				{
					
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	
	}
	// public static void playMusic() {// 背景音乐播放
	// 	try {
	// 		AudioInputStream ais = AudioSystem.getAudioInputStream(new File("resources/war1.wav")); // 绝对路径
	// 		AudioFormat aif = ais.getFormat();
	// 		final SourceDataLine sdl;
	// 		DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
	// 		sdl = (SourceDataLine) AudioSystem.getLine(info);
	// 		sdl.open(aif);
	// 		sdl.start();
	// 		FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
	// 		// value可以用来设置音量，从0-2.0
	// 		double value = 2;
	// 		float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
	// 		fc.setValue(dB);
	// 		int nByte = 0;
	// 		final int SIZE = 1024 * 64;
	// 		byte[] buffer = new byte[SIZE];
	// 		while (nByte != -1) {
	// 			nByte = ais.read(buffer, 0, SIZE);
	// 			sdl.write(buffer, 0, nByte);
	// 		}
	// 		sdl.stop();
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	// }
	musicStuff musicObject;
	public Begin(){
		super();
		terminal = new AsciiPanel(80,28,AsciiFont.TALRYTH_15_15);
		add(terminal);
		pack();
		screen = new StartScreen();
		addKeyListener(this);
		repaint();
		musicObject = new musicStuff();
	}
	

	@Override
	public void repaint(){
		terminal.clear();
		screen.displayOutput(terminal);
		super.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		screen = screen.respondToUserInput(e);
		if(screen == null)
		{
			this.dispose();
			System.exit(0);
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e) { }
	
	public static void main(String[] args) {
		Begin app = new Begin();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
		// playMusic();
		String filepath = "resources/Title.wav";
		app.musicObject.playMusic(filepath);
	}
}