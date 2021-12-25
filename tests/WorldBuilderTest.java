package tests;



import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import myruguelike.World;
import myruguelike.WorldBuilder;
public class WorldBuilderTest {
    WorldBuilder sample;
    @Before
    public void init()
    {
        //初始化worldbuilder
        sample=new WorldBuilder(5,5);
    }
    @Test(timeout = 1000)
    public void test0()
    {
        //smooth函数的超时测试
        sample=new WorldBuilder(5,5);
    }
    @Test
    public void test1()
    {
        //世界创造结果判定是否为空
        sample=new WorldBuilder(5,5);
        World world1 = sample.makeCaves().build();
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                assertNotNull(world1.tile(i, j));
            }
        }
    }

}