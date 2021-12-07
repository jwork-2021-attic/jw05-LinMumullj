package myruguelike;

import java.awt.Color;

import myruguelike.screens.PlayScreen;

public class Creature {
	public World world;
	public PlayScreen playscreen;
	public int x;
	public int y;
	
	private char glyph;
	public char glyph() { return glyph; }
	
	private Color color;
	public Color color() { return color; }

	private CreatureAi ai;
	public void setCreatureAi(CreatureAi ai) { this.ai = ai; }
	
	private int maxHp;
	public int maxHp() { return maxHp; }
	
	private int hp;
	public int hp() { return hp; }
	
	private String name;
	public String name() { return name; }
	
	private int attackValue;
	public int attackValue() { return attackValue; }

	private int defenseValue;
	public int defenseValue() { return defenseValue; }
	
	public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense,PlayScreen plsc){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;
		this.playscreen=plsc;
	}
	public void setScreen(PlayScreen playscreen)
	{
		this.playscreen=playscreen;
	}
	
	public void moveBy(int mx, int my){

		if(mx==0&&my==0)
			return;
		
		Creature other = world.creature(x+mx, y+my);
		
		if (other == null)
		{
			if(world.item(x+mx, y+my)==null)
				ai.go(x+mx, y+my, world.tile(x+mx, y+my),world.item(x+mx, y+my));
			else
			{
				// world.removeitem(x+mx, y+my);
				// if(hp>=90)
				// 	hp=100;
				// else
				// 	hp+=10;
				ai.go(x+mx, y+my, world.tile(x+mx, y+my),world.item(x+mx, y+my));
			}
		}
		else
			attack(other);
	}

	public Tile tile(int wx, int wy) {
		return world.tile(wx, wy);
	}
	public Creature creature(int wx, int wy) {
		return world.creature(wx, wy);
	}

	public void attack(Creature other){
		int atkva = Math.max(0, attackValue() - other.defenseValue())+1;
		doAction("attack the '%s' for %d hp", other.glyph, atkva);
		
		other.changeHp(-atkva);
	}

	public void changeHp(int amount) { 
		hp += amount;
		if(hp>100)
			hp=100;
		if (hp < 1) {
			doAction("painfully die");
			world.score+=10;
			world.remove(this);
		}
	}
	
	public void dig(int wx, int wy) {
		world.dig(wx, wy);
		doAction("dig a hole");
	}
	
	public void update(){
		ai.onUpdate();
	}

	public boolean canSee(int wx, int wy){
		return ai.canSee(wx, wy);
	}

	public boolean canEnter(int wx, int wy) {
		return world.tile(wx, wy).isGround() && world.creature(wx, wy) == null;
	}

	public void notify(String message, Object ... params){
		ai.onNotify(String.format(message, params));
	}
	
	public void doAction(String message, Object ... params){
		int r = 10;  //响应半径
		for (int ox = -r; ox < r+1; ox++){
			for (int oy = -r; oy < r+1; oy++){
				if (ox*ox + oy*oy > r*r)
					continue;
				
				Creature other = world.creature(x+ox, y+oy);
				
				if (other == null)
					continue;
				
				if (other == this)
					other.notify("You " + message + ".", params);
				else if(other.canSee(x, y))
					other.notify(String.format("The '%s' %s.", glyph, makeSecondPerson(message)), params);
			}
		}
	}
	
	private String makeSecondPerson(String text){
		String[] words = text.split(" ");
		words[0] = words[0] + "s";
		
		StringBuilder builder = new StringBuilder();
		for (String word : words){
			builder.append(" ");
			builder.append(word);
		}
		
		return builder.toString().trim();
	}
}
