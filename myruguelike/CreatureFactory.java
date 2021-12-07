package myruguelike;

import java.util.List;

import asciiPanel.AsciiPanel;
import myruguelike.screens.PlayScreen;

public class CreatureFactory{
	private World world;
	
	public CreatureFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(List<String> messages,PlayScreen plsc){
		Creature player = new Creature(world, (char)2, AsciiPanel.brightCyan, 100, 20, 5,plsc);
		world.addAtEmptyLocation(player);
		new PlayerAi(player, messages, plsc);
		return player;
	}
	public Creature newBat(PlayScreen plsc){
		Creature bat = new Creature(world, (char)157, AsciiPanel.brightRed, 100, 5, 0,plsc);
		world.addAtEmptyLocation(bat);
		new BatAi(bat, plsc);
		return bat;
	}

	public Creature newEBat(Creature player,PlayScreen plsc){
		Creature bat = new Creature(world, (char)157, AsciiPanel.brightMagenta, 150, 10, 0,plsc);
		world.addAtEmptyLocation(bat);
		new EBatAi(bat, player, plsc);
		return bat;
	}

	public Items newHeart(){
		Items item = Items.HEART;
		//Items item = new Items((char)3, AsciiPanel.brightGreen, "heart" ,10);
		world.addAtEmptyLocation(item);
		return item;
	}

	public Items newGourd(){
		Items item = Items.GOURD;
		//Items item = new Items((char)2, AsciiPanel.brightMagenta, "gourd1" ,10);
		world.addAtEmptyLocation(item);
		return item;
	}
	
}
