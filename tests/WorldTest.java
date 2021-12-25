package tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import myruguelike.Items;
import myruguelike.World;
import myruguelike.WorldBuilder;
    public class WorldTest {
        World sample;
        Items a;
        @Before
        public void init()
        {
            sample=new WorldBuilder(5, 5).makeCaves().build();
            a=Items.GOURD;
        }
        @Test
        public void test1()
        {
            sample.addAtEmptyLocation(a);
            for(int i=0;i<5;i++)
            {
                for(int j=0;j<5;j++)
                {
                    if(sample.item(i, j)!=null)
                    {
                        assertSame(a, sample.item(i, j));
                    }
                }
            }
        }
    }