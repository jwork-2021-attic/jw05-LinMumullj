package myruguelike.screens;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import asciiPanel.AsciiPanel;

public class LoadScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("You have a game saving .", 3, AsciiPanel.red);
		terminal.writeCenter("-- press [enter] to continue your game --", 26);
	}

    public Screen loadsave() throws Exception
    {
        FileInputStream fis=new FileInputStream("save.ser");
        ObjectInputStream ois=new ObjectInputStream(fis);

        //读取
        PlayScreen sc= (PlayScreen)ois.readObject();
        ois.close();
        //System.out.println(sc.toString());
        return sc;
    }

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
        {
            Screen tmp;
            try {
                tmp=loadsave();
            } catch (Exception e) {
                System.out.println("你没有存档");
                System.out.println("错误信息：" + e.getMessage());  
                tmp=new PlayScreen();
            }
            return tmp;
           
        }
        else
        {
            return this;
        }
	}
}
