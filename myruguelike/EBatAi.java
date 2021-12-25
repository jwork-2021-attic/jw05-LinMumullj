package myruguelike;

import myruguelike.screens.PlayScreen;

public class EBatAi extends CreatureAi {
	/**
     *
     */
    private static final long serialVersionUID = -4729882164183352861L;
    private Creature player;
	
	public EBatAi(Creature creature, Creature player, PlayScreen plsc) {
		super(creature,plsc);
		this.player = player;
	}

	public void onUpdate(){
		if (Math.random() < 0.2)
			return;
		
		if (creature.canSee(player.x, player.y))
			hunt();
		else
        {
            wander();
            wander();
        }
	}
	
	public void hunt(){
        int mx=0;
        int my=0;
        int gapx=player.x-this.creature.x;
        int gapy=player.y-this.creature.y;
        if(gapx*gapx+gapy*gapy<36)
        {
            if(player.x>this.creature.x)
                mx=1;
            else
                mx=-1;
            if(player.y>this.creature.y)
                my=1;
            else
                my=-1;
            if(this.creature.world.tile(player.x+mx, player.y+my).isGround())
                creature.moveBy(mx, my);
            else
            {
                wander();
                wander();
            }
        }
        else
        {
            wander();
            wander();
        }
	}
}