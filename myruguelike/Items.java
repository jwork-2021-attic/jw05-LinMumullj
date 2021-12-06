package myruguelike;
import java.awt.Color;
import myruguelike.screens.PlayScreen;
public class Items {
    public PlayScreen playscreen;
    public int x;
    public int y;
    private char glyph;
	public char glyph() { return glyph; }
	
	private Color color;
	public Color color() { return color; }

	private String name;
	public String name() { return name; }
	
	private int hpvalue;
	public int getHpvalue() { return hpvalue; }


    public Items(char glyph, Color color, String name,int hpvalue){
		this.glyph = glyph;
		this.color = color;
		this.name = name;
        this.hpvalue=hpvalue;
        this.x=0;
        this.y=0;
	}
    public void setScreen(PlayScreen playscreen)
	{
		this.playscreen=playscreen;
	}
}