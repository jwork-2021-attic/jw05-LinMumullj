package myruguelike;

import myruguelike.screens.PlayScreen;

public class BatAi extends CreatureAi {

	public BatAi(Creature creature, PlayScreen plsc) {
		super(creature,plsc);
	}

	public void onUpdate(){
		wander();
		wander();
	}
	
}