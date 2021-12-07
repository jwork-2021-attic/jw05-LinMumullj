package myruguelike.screens;


import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class WinScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		//terminal.writeCenter("You won!!!!", 3 ,AsciiPanel.brightCyan);
		terminal.writeCenter("You find the first baby, level up!!!!", 3 ,AsciiPanel.brightMagenta);
		terminal.write((char)2,31,10,AsciiPanel.brightCyan);
		terminal.write((char)2,34,10,AsciiPanel.brightBlue);
		// terminal.write((char)2,43,10,AsciiPanel.brightRed);
        // terminal.write((char)2,46,10,AsciiPanel.yellow);
        // terminal.write((char)2,37,10,AsciiPanel.brightYellow);
        // terminal.write((char)2,40,10,AsciiPanel.brightGreen);
        
        
        // terminal.write((char)2,49,10,AsciiPanel.brightMagenta);
		terminal.writeCenter("-- press [enter] to restart --", 26);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}
}
