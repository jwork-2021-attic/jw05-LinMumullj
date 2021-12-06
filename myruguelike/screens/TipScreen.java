package myruguelike.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class TipScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("H A N D   B O O K", 1,AsciiPanel.brightRed);
        terminal.write("Your task is to save seven gourd babies in the cave and to beat the boss ", 5, 3);
		terminal.write("find all seven gourd babies and defeat the boss scorpion to win the game!", 3, 5);
		terminal.write((char)30,5,7);
		terminal.write((char)31,5,9);
		terminal.write((char)17,4,8);
		terminal.write((char)16,6,8);
		terminal.write("press UP DOWN LEFT RIGHT button to control your character", 10, 8, AsciiPanel.brightGreen);
		terminal.write((char)3,5,11,AsciiPanel.brightGreen);
		terminal.write("this is BLOOD BAG which can add HP",7,11);
		terminal.write((char)2,5,13,AsciiPanel.brightBlue);
		terminal.write("this is your gourd baby, find them to stronger your team",7,13);
		terminal.write((char)157,5,15,AsciiPanel.brightRed);
		terminal.write("Be cautious of bat. they will attack you when you get close",7,15);
		terminal.write((char)1,5,17,AsciiPanel.brightRed);
		terminal.write("Beat the scorpion monster, fight for the justice ! ",7,17);
		terminal.writeCenter("-- press [enter] to start / press [Esc] to back --", 26);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode())
        {
            case KeyEvent.VK_ENTER:
            return new PlayScreen();
            case KeyEvent.VK_ESCAPE:
            return new StartScreen();
            default:
            return this;
        }
	}
}
