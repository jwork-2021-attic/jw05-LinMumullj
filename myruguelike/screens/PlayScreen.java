package myruguelike.screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import myruguelike.Creature;
import myruguelike.CreatureFactory;
import myruguelike.World;
import myruguelike.WorldBuilder;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PlayScreen implements Screen,Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private World world;
	private Creature player;
	private int screenWidth;
	private int screenHeight;
	private List<String> messages;
	private int score;
	private int level;
	public boolean iswin;
	public boolean ispause;
	public PlayScreen(){
		score=0;
		level=1;
		screenWidth = 80;
		screenHeight = 23;
		iswin=false;
		ispause=false;
		messages = new ArrayList<String>();
		createWorld();
		
		CreatureFactory creatureFactory = new CreatureFactory(world);
		createCreatures(creatureFactory);
	}
	public PlayScreen(World world,Creature player,int screenWidth,int screenHeight,List<String> messages,int score,int level,boolean iswin,boolean ispause){
		this.score=score;
		this.level=level;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.iswin=iswin;
		this.ispause=ispause;
		this.messages = messages;
		this.world=world;
		this.player=player;
	}

	public PlayScreen(PlayScreen tmp){
		score=tmp.score;
		level=tmp.level;
		screenWidth = tmp.screenWidth;
		screenHeight = tmp.screenHeight;
		iswin=tmp.iswin;
		ispause=tmp.ispause;
		messages = tmp.messages;
		world=tmp.world;
		player=tmp.player;
		//未改动的：
		CreatureFactory creatureFactory = new CreatureFactory(world);
		createCreatures(creatureFactory);
	}
	private void createCreatures(CreatureFactory creatureFactory){
		player = creatureFactory.newPlayer(messages,this);  //将消息列表传递给playerAI
		
		for (int i = 0; i < 10; i++){
			creatureFactory.newBat(this);
		}
		for (int i = 0; i < 30; i++){
			creatureFactory.newEBat(player,this);
		}
		for (int i = 0; i < 10; i++){
			creatureFactory.newHeart();
		}
		creatureFactory.newGourd();
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
		
		terminal.writeCenter("-- press [Esc] to back press [S] to save--", 26);

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
	
	public void save()throws Exception
	{
		FileOutputStream fos=new FileOutputStream("save.ser");
		ObjectOutputStream oos=new ObjectOutputStream(fos);

		//开始序列化
		PlayScreen tmp=new PlayScreen(world,player,screenWidth,screenHeight,messages,score,level,iswin,ispause);
		oos.writeObject(tmp);
		oos.close();

	}
	public void save(String path)throws Exception
	{
		throw new Exception("sth wrong");

	}
	@Override
	public Screen respondToUserInput(KeyEvent key){
		switch (key.getKeyCode()){
		case KeyEvent.VK_ESCAPE: return new StartScreen();
		case KeyEvent.VK_S: 
		{
			try {
				save();
				System.out.println("Success");
			} catch (Exception e) {
				return this;
			}
		}
		case KeyEvent.VK_UP:player.moveBy( 0,-1); break;
		case KeyEvent.VK_DOWN:player.moveBy( 0, 1); break;
		case KeyEvent.VK_LEFT:player.moveBy(-1, 0); break;
		case KeyEvent.VK_RIGHT:player.moveBy( 1, 0); break;

		}
		// if(ispause==false&&key.getKeyCode()==KeyEvent.VK_SPACE)
		// {
		// }
		//每次按键响应后，更新一次世界
		world.update();
		if(iswin==true)
			return new WinScreen();
		if (player.hp() < 1)
			return new LoseScreen();
		
		return this;
	}

	public Screen checkwin()
	{
		if(iswin==true)
			return new WinScreen();
		return this;
	}
}
