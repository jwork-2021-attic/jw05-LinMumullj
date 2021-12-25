package tests;

import org.junit.Before;
import org.junit.Test;

import myruguelike.screens.PlayScreen;

import static org.junit.Assert.*;
public class PlayScreenTest {
    PlayScreen sample;
    @Before
    public void init()
    {
        sample=new PlayScreen();
    }
    @Test(expected = Exception.class)
    public void test1()throws Exception
    {
        sample.save("wrong path");
    }
}