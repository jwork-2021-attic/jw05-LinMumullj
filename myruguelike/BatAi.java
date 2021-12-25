package myruguelike;

import myruguelike.screens.PlayScreen;

public class BatAi extends CreatureAi {

	/**
	 *
	 */
	private static final long serialVersionUID = 6946738739797945392L;

	public BatAi(Creature creature, PlayScreen plsc) {
		super(creature,plsc);
	}

	public void onUpdate(){
		wander();
		wander();
	}
	
}