# 						**myruguelike learning**

## 																	————from the day one

**191220059  林龙杰**

## 一、ruguelike构思

在jw04的作业中，了解到ruguelike的历史背景。以及一些经典的地牢算法，比如地牢的随机生成算法————通过多次smooth地砖与墙壁来随机构造地牢，还有一些其他知识也引起了我很大的兴趣。在jw05中将要求实现一个地牢游戏，这对于我来说是一个大挑战。所以我初步构想，沿用jw04提供的地牢框架，来简易实现一个爷爷救葫芦娃的rugulike。

### 1.规则构思

为了配合我的低技术力，我的想法是，爷爷作为主角，在洞穴中寻找七个葫芦娃，找到七个葫芦娃并打败最终boss蝎子精即可获胜。在寻找途中将可以进行跟怪物战斗，拾取加血道具，拾取装备等操作。

### 2.框架构思

我暂时准备实现一个视角无限，可以挖掘墙壁，可以战斗，有状态栏，队伍成员栏，有一定关卡的地牢。就利用asciiPanel提供的图标绘制简易的界面。怪物的一些功能将会使用学到的多线程技术实现，比如射箭就准备用多线程来实现。怪物的移动也将采取一定智能的寻路算法。游戏将随着开发从回合制转化为实时对战游戏。



## 二、主要类的介绍（逐步完善）

### 1.界面类

a.Screen接口类

每一个画面都要完成“打印画面内容”和“响应用户操作”两个操作，所以将这两个功能用Screen接口包含。

```java
public interface Screen {
	public void displayOutput(AsciiPanel terminal);
	
	public Screen respondToUserInput(KeyEvent key);
}
```

b.StartScreen类

运行游戏第一个出现的画面类，负责打印最初的画面，玩家可以进行ENTER进入游戏或者B查看手册操作。

```java
public class StartScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("WELCOME TO THE", 2);
		terminal.writeCenter("Grandpa saves gourd baby", 4);
		terminal.writeCenter("-- press [enter] to start / press [B] to view the handbook --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
        switch(key.getKeyCode())
        {
            case KeyEvent.VK_ENTER:
            return new PlayScreen();
            case KeyEvent.VK_B:
            return new TipScreen();
            default:
            return this;
        }
	}
}
```

c.WinScreen/LoseScreen类

显示胜利失败画面。

```java
public class WinScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("You won.", 3);
		terminal.writeCenter("-- press [enter] to restart --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}
}
```

d.PlayScreen类

游戏交互刷新发生地，

### 2.世界构建类

a.Tile砖块类

Tile声明为一个枚举类，枚举游戏中基本的砖块类型：墙壁，地板......

```java
public enum Tile {
	FLOOR((char)250, AsciiPanel.yellow),
	WALL((char)177, AsciiPanel.yellow),
	BOUNDS('x', AsciiPanel.brightBlack);
	
	private char glyph;
	public char glyph() { return glyph; }
	
	private Color color;
	public Color color() { return color; }
	
	Tile(char glyph, Color color){
		this.glyph = glyph;
		this.color = color;
	}

	public boolean isGround() {
		return this != WALL && this != BOUNDS;
	}

	public boolean isDiggable() {
		return this == Tile.WALL;
	}
}
```

b.World类

World类来容纳这些砖块，对砖块进行管理。并且对世界中的生物进行管理。其中用二维数组储存Tile，用一个List储存Creature。为了操作方便：

对于Tile：提供了获取特定砖块，挖掘（dig）功能实现函数。

对于Creature：提供了随机生成生物函数，删除生物函数，必要的更新生物状态函数、

```java
public class World {
	private Tile[][] tiles;
	private int width;
	public int width() { return width; }
	
	private int height;
	public int height() { return height; }
	
	private List<Creature> creatures;
	
	public World(Tile[][] tiles){
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.creatures = new ArrayList<Creature>();
	}

	public Creature creature(int x, int y){
		for (Creature c : creatures){
			if (c.x == x && c.y == y)
				return c;
		}
		return null;
	}
	
	public Tile tile(int x, int y){
		if (x < 0 || x >= width || y < 0 || y >= height)
			return Tile.BOUNDS;
		else
			return tiles[x][y];
	}
	
	public char glyph(int x, int y){
		return tile(x, y).glyph();
	}
	
	public Color color(int x, int y){
		return tile(x, y).color();
	}

	public void dig(int x, int y) {
		if (tile(x,y).isDiggable())
			tiles[x][y] = Tile.FLOOR;
	}
	
	public void addAtEmptyLocation(Creature creature){
		int x;
		int y;
		
		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		} 
		while (!tile(x,y).isGround() || creature(x,y) != null);
		
		creature.x = x;
		creature.y = y;
		creatures.add(creature);
	}
	
	public void update(){
		List<Creature> toUpdate = new ArrayList<Creature>(creatures);
		for (Creature creature : toUpdate){
			creature.update();
		}
	}

	public void remove(Creature other) {
		creatures.remove(other);
	}
}
```

c.WorldBuilder类

构造世界，为了趣味性，这里用到了构造地牢的smooth函数来进行构建。该过程是用洞穴地板和墙壁随机填充该区域，然后通过将大部分相邻墙壁的区域变成墙壁，将大部分相邻地板的区域变成地板来平滑所有内容。重复几次平滑过程，就会得到一个有趣的洞穴墙壁和地板组合。这里涉及到我们的第一次大量更新数据，这样更新经常读取的数据的时候，一定要注意使用双缓存，勿直接修改原数据。

```java
public class WorldBuilder {
	private int width;
	private int height;
	private Tile[][] tiles;

	public WorldBuilder(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
	}

	public World build() {
		return new World(tiles);
	}

	private WorldBuilder randomizeTiles() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
			}
		}
		return this;
	}

	private WorldBuilder smooth(int times) {
		Tile[][] tiles2 = new Tile[width][height];
		for (int time = 0; time < times; time++) {

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int floors = 0;
					int rocks = 0;

					for (int ox = -1; ox < 2; ox++) {
						for (int oy = -1; oy < 2; oy++) {
							if (x + ox < 0 || x + ox >= width || y + oy < 0
									|| y + oy >= height)
								continue;

							if (tiles[x + ox][y + oy] == Tile.FLOOR)
								floors++;
							else
								rocks++;
						}
					}
					tiles2[x][y] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
				}
			}
			tiles = tiles2;
		}
		return this;
	}

	public WorldBuilder makeCaves() {
		return randomizeTiles().smooth(8);
	}
}
```



### 3.生物类

生物目前主要包括主角与蝙蝠兵：



### 4.AI类

主要由基类CreatureAi以及他的派生类PlayerAi，BatAi等组成：


### 4.物品类

物品类目前主要包括血包❤，与葫芦娃（目前葫芦娃以类似于KEY的形式存在在地牢中，只要爷爷拾取到葫芦娃，即可将其加入队伍）。

## 三、展示与细节

展示视频发布在bilibili：

展示描述：

- 首先进入游戏，BGM响起，眼前的是PlayScreen欢迎你来到“爷爷救葫芦娃”这个游戏，“WELCOME TO THE GAME”，此时可以选择进行游戏，查看手册，或者退出游戏。

  ![image-20211207021722568](C:\Users\huawei\AppData\Roaming\Typora\typora-user-images\image-20211207021722568.png)

- 如果选择查看手册则进入手册界面，你可以形象的了解到游戏操作方法，怪物种类和规则。

  ![image-20211207021825161](C:\Users\huawei\AppData\Roaming\Typora\typora-user-images\image-20211207021825161.png)

- 选择继续游戏，进入Play界面：

  蓝色小人为主角爷爷，下方左侧状态栏显示你的HP，得分，以及关卡难度（LEVEL），右侧显示队伍成员信息，暂时只有爷爷在队伍中，下方是提示信息。

  ![image-20211207021928325](C:\Users\huawei\AppData\Roaming\Typora\typora-user-images\image-20211207021928325.png)

- 你可以进行挖掘：

  ![image-20211207022238856](C:\Users\huawei\AppData\Roaming\Typora\typora-user-images\image-20211207022238856.png)

- 蝙蝠兵¥在你靠近时（设置的半径为6）将会主动攻击你，在远离时则随机游走。你可以攻击它们，他们也会攻击你：

- ![image-20211207022559740](C:\Users\huawei\AppData\Roaming\Typora\typora-user-images\image-20211207022559740.png)

  ![image-20211207022614352](C:\Users\huawei\AppData\Roaming\Typora\typora-user-images\image-20211207022614352.png)

- 随时注意你的状态。绿色血量表示你还比较强壮，黄色则表示你可能要注意一下血量了，红色则代表你已经很虚弱了。

  ![image-20211207022722431](C:\Users\huawei\AppData\Roaming\Typora\typora-user-images\image-20211207022722431.png)

  ![image-20211207022814333](C:\Users\huawei\AppData\Roaming\Typora\typora-user-images\image-20211207022814333.png)

  ![image-20211207022842728](C:\Users\huawei\AppData\Roaming\Typora\typora-user-images\image-20211207022842728.png)

- 在杀死敌方单位后你会获得金币，而被地方杀死则会来到Lost界面，当然，你可以选择重头再来。

  ![image-20211207023049559](C:\Users\huawei\AppData\Roaming\Typora\typora-user-images\image-20211207023049559.png)

![image-20211207023123702](C:\Users\huawei\AppData\Roaming\Typora\typora-user-images\image-20211207023123702.png)



## 四、思考与总结