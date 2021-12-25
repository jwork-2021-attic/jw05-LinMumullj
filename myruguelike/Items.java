package myruguelike;
import java.awt.Color;
import asciiPanel.AsciiPanel;
import myruguelike.screens.PlayScreen;
public enum Items{
	HEART((char)3, AsciiPanel.brightGreen, "heart" ,10),
	GOURD((char)2, AsciiPanel.brightBlue, "gourd1" ,10);


    public PlayScreen playscreen;
    public int x;
    public int y;
    private char glyph;
	public char glyph() { return glyph; }
	
	private Color color;
	public Color color() { return color; }

	private String itemname;
	public String getname() { return itemname; }
	
	private int hpvalue;
	public int getHpvalue() { return hpvalue; }


    Items(char glyph, Color color, String itemname,int hpvalue){
		this.glyph = glyph;
		this.color = color;
		this.itemname = itemname;
        this.hpvalue=hpvalue;
		this.x=0;
		this.y=0;
	}
    public void setScreen(PlayScreen playscreen)
	{
		this.playscreen=playscreen;
	}
}