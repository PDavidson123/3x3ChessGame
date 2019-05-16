package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayTest {

    private Play play = new Play();

    @Test
    void testCheckGoodness() {
        assertTrue(play.checkGoodness(6));
        assertFalse(play.checkGoodness(1));
        assertFalse(play.checkGoodness(2));
        assertFalse(play.checkGoodness(3));
        assertFalse(play.checkGoodness(4));
        assertFalse(play.checkGoodness(5));
    }
}