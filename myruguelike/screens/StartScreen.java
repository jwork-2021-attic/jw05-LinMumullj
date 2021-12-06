package myruguelike.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("WELCOME TO THE", 3,AsciiPanel.brightMagenta);
		terminal.writeCenter(" Grandpa Saves Gourd Babies", 6,AsciiPanel.brightCyan);
        terminal.write((char)2,31,10,AsciiPanel.brightRed);
        terminal.write((char)2,34,10,AsciiPanel.yellow);
        terminal.write((char)2,37,10,AsciiPanel.brightYellow);
        terminal.write((char)2,40,10,AsciiPanel.brightGreen);
        terminal.write((char)2,43,10,AsciiPanel.brightCyan);
        terminal.write((char)2,46,10,AsciiPanel.brightBlue);
        terminal.write((char)2,49,10,AsciiPanel.brightMagenta);
		terminal.writeCenter("-- press [enter] to start / [B] to view the handbook / [Esc] to quit --", 26);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
        switch(key.getKeyCode())
        {
            case KeyEvent.VK_ENTER:
            return new PlayScreen();
            case KeyEvent.VK_B:
            return new TipScreen();
            case KeyEvent.VK_ESCAPE:
            return null;
            default:
            return this;
        }
	}
}
