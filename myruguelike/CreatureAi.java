package myruguelike;

public class CreatureAi {
	protected Creature creature;
	
	public CreatureAi(Creature creature){
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}
	
	public void onEnter(int x, int y, Tile tile){
		if (tile.isGround()){
			creature.x = x;
			creature.y = y;
		}
		//
	}
	
	public void onUpdate(){
	}
	
	public boolean canSee(int wx, int wy) {

		if ((creature.x-wx)<8&&(creature.y-wy)<8)
			return true;

		return false;
	}

	public void onNotify(String message){
	}
	public void wander(){
		int mx = (int)(Math.random() * 3) - 1;
		int my = (int)(Math.random() * 3) - 1;
		if (!creature.tile(creature.x+mx, creature.y+my).isGround())
			return;
		else
			creature.moveBy(mx, my);
		// if (creature.world.tile(creature.x+mx, creature.y+my).isGround()) 
		// 	creature.moveBy(mx, my);
		
	}
}
