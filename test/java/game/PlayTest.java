package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayTest {

    private Play play = new Play();

    private int[][] grnd = { {0,0,0},{0,0,0},{0,0,0} };
    private int[] frstClckW = { 0,0 };
    private int[] frstClckW1 = { 0,1 };
    private int[] frstClckB = { 2,0 };

    @Test
    void testCheckGoodness() {
        assertTrue(play.checkGoodness(6));
        assertFalse(play.checkGoodness(1));
        assertFalse(play.checkGoodness(2));
        assertFalse(play.checkGoodness(3));
        assertFalse(play.checkGoodness(4));
        assertFalse(play.checkGoodness(5));
    }

    @Test
    void canWhiteMove() {
        assertTrue(play.canWhiteMove(1,2,grnd, frstClckW));
        assertTrue(play.canWhiteMove(2,1,grnd, frstClckW));
        assertFalse(play.canWhiteMove(0,0,grnd, frstClckW));
        assertFalse(play.canWhiteMove(0,1,grnd, frstClckW));
        assertFalse(play.canWhiteMove(0,2,grnd, frstClckW));
        assertFalse(play.canWhiteMove(1,1,grnd, frstClckW));
        assertFalse(play.canWhiteMove(1,0,grnd, frstClckW));
        assertFalse(play.canWhiteMove(2,0,grnd, frstClckW));
        assertFalse(play.canWhiteMove(2,2,grnd, frstClckW));
    }

    @Test
    void canWhiteMove2() {
        assertTrue(play.canWhiteMove(2,0,grnd, frstClckW1));
        assertTrue(play.canWhiteMove(2,2,grnd, frstClckW1));
        assertFalse(play.canWhiteMove(0,0,grnd, frstClckW1));
        assertFalse(play.canWhiteMove(0,1,grnd, frstClckW1));
        assertFalse(play.canWhiteMove(0,2,grnd, frstClckW1));
        assertFalse(play.canWhiteMove(1,0,grnd, frstClckW1));
        assertFalse(play.canWhiteMove(1,1,grnd, frstClckW1));
        assertFalse(play.canWhiteMove(1,2,grnd, frstClckW1));
        assertFalse(play.canWhiteMove(2,1,grnd, frstClckW1));
    }

    @Test
    void canBlackMove() {
        assertTrue(play.canBlackMove(0,1,grnd, frstClckB));
        assertTrue(play.canBlackMove(1,2,grnd, frstClckB));
        assertFalse(play.canBlackMove(0,0,grnd, frstClckB));
        assertFalse(play.canBlackMove(0,2,grnd, frstClckB));
        assertFalse(play.canBlackMove(1,1,grnd, frstClckB));
        assertFalse(play.canBlackMove(1,0,grnd, frstClckB));
        assertFalse(play.canBlackMove(2,0,grnd, frstClckB));
        assertFalse(play.canBlackMove(2,1,grnd, frstClckB));
        assertFalse(play.canBlackMove(2,2,grnd, frstClckB));
    }
}