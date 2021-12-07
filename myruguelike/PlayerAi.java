package myruguelike;

import java.util.List;

import myruguelike.screens.PlayScreen;

public class PlayerAi extends CreatureAi {

	private List<String> messages;
	
	public PlayerAi(Creature creature, List<String> messages, PlayScreen plsc) {
		super(creature, plsc);
		this.messages = messages;
	}

	public void onEnter(int x, int y, Tile tile, Items item){
		if (tile.isGround()){
			if(item == Items.GOURD)
			{
				this.creature.world.removeitem(x, y);
				this.playscreen.iswin=true;
			}
			else if(item == Items.HEART)
			{
				this.creature.changeHp(10);
				this.creature.world.removeitem(x, y);
			}
			creature.x = x;
			creature.y = y;

		} else if (tile.isDiggable()) {
			creature.dig(x, y);
		}
	}
	
	public void onNotify(String message){
		messages.add(message);
	}
}
