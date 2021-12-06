package myruguelike;

import java.util.List;

import asciiPanel.AsciiPanel;

public class CreatureFactory{
	private World world;
	
	public CreatureFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(List<String> messages){
		Creature player = new Creature(world, (char)2, AsciiPanel.brightCyan, 100, 20, 5);
		world.addAtEmptyLocation(player);
		new PlayerAi(player, messages);
		return player;
	}
	public Creature newBat(){
		Creature bat = new Creature(world, (char)157, AsciiPanel.brightRed, 100, 5, 0);
		world.addAtEmptyLocation(bat);
		new BatAi(bat);
		return bat;
	}

	public Creature newEBat(Creature player){
		Creature bat = new Creature(world, (char)157, AsciiPanel.brightRed, 150, 10, 0);
		world.addAtEmptyLocation(bat);
		new EBatAi(bat, player);
		return bat;
	}

	public Items newHeart(){
		Items item = new Items((char)3, AsciiPanel.brightGreen, "heart" ,10);
		world.addAtEmptyLocation(item);
		return item;
	}

	public Items newGourd(){
		Items item = new Items((char)2, AsciiPanel.brightMagenta, "gourd1" ,10);
		world.addAtEmptyLocation(item);
		return item;
	}
	
}
