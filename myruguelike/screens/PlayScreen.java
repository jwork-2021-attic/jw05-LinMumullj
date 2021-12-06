package myruguelike.screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import myruguelike.Creature;
import myruguelike.CreatureFactory;
import myruguelike.World;
import myruguelike.WorldBuilder;

public class PlayScreen implements Screen {
	private World world;
	private Creature player;
	private int screenWidth;
	private int screenHeight;
	private List<String> messages;
	private int score;
	private int level;
	public PlayScreen(){
		score=0;
		level=1;
		screenWidth = 80;
		screenHeight = 23;
		messages = new ArrayList<String>();
		createWorld();
		
		CreatureFactory creatureFactory = new CreatureFactory(world);
		createCreatures(creatureFactory);
	}
	
	private void createCreatures(CreatureFactory creatureFactory){
		player = creatureFactory.newPlayer(messages);  //将消息列表传递给playerAI
		
		for (int i = 0; i < 2; i++){
			creatureFactory.newBat();
		}
		for (int i = 0; i < 22; i++){
			creatureFactory.newEBat(player);
		}
		for (int i = 0; i < 2; i++){
			creatureFactory.newHeart();
		}
	}
	
	private void createWorld(){
		//90 32
		world = new WorldBuilder(200, 170).makeCaves().build();
		//world = new WorldBuilder(90, 30).makeCaves().build();
	}
	//取打印左上角x，若当前Screen没有碰右下角，且不没有碰左上角，则返回当前Screen左上角x
	public int getScrollX() { return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth)); }
	//取打印左上角y，同上
	public int getScrollY() { return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight)); }
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
		int top = getScrollY(); 
		
		displayTiles(terminal, left, top);
		displayMessages(terminal, messages);
		
		terminal.writeCenter("-- press [Esc] to back --", 26);

		String stats = String.format("HP: %3d/%3d", player.hp(), player.maxHp());
		if(player.hp()>70)
		{
			terminal.write(stats, 1, 24,AsciiPanel.green);
		}
		else if(player.hp()<=70&&player.hp()>40)
		{
			terminal.write(stats, 1, 24,AsciiPanel.yellow);
		}
		else
		{
			terminal.write(stats, 1, 24,AsciiPanel.red);
		}

		score=world.score;
		String stats2 = String.format("SCORE: %d", score);
		terminal.write(stats2,1,25,AsciiPanel.brightYellow);
		String stats3 = String.format("LEVEL: %d", level);
		terminal.write(stats3,1,26);

		terminal.write("Team members: ",64,24);
		terminal.write((char)2,64,26,AsciiPanel.brightCyan);
	}

	private void displayMessages(AsciiPanel terminal, List<String> messages) {
		int top = screenHeight - messages.size();
		for (int i = 0; i < messages.size(); i++){
			//terminal.writeCenter(messages.get(i), top + i);
			terminal.writeCenter(messages.get(i), top);
		}
		messages.clear();
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
		for (int x = 0; x < screenWidth; x++){
			for (int y = 0; y < screenHeight; y++){
				int wx = x + left;
				int wy = y + top;

				Creature creature = world.creature(wx, wy);

				//生物存在creature里，非生物（tile，floor）直接可用world中方法获取。
				if (creature != null)
					terminal.write(creature.glyph(), creature.x - left, creature.y - top, creature.color());
				else
					terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
			}
		}
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()){
		case KeyEvent.VK_ESCAPE: return new StartScreen();
		
		case KeyEvent.VK_UP:player.moveBy( 0,-1); break;
		case KeyEvent.VK_DOWN:player.moveBy( 0, 1); break;
		case KeyEvent.VK_LEFT:player.moveBy(-1, 0); break;
		case KeyEvent.VK_RIGHT:player.moveBy( 1, 0); break;

		}
		//每次按键响应后，更新一次世界
		world.update();
		if (player.hp() < 1)
			return new LoseScreen();
		
		return this;
	}
}