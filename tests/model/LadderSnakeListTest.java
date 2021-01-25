package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LadderSnakeListTest {

//    GameController game = new GameController();
    LadderSnakeList list = new LadderSnakeList();

    @BeforeEach
    public void addLaddersSnakes() {
        list.add(3, 5);
        list.add(10, 8);
    }

    @Test
    public void ladderOrSnakeExistAndReturnsCorrectEndPosition() {
        assertEquals(5, list.checkLadderSnake(3));
        assertEquals(8, list.checkLadderSnake(10));
    }

    @Test
    public void ladderOrSnakeNotExistReturnsNegative1() {
        assertEquals(-1, list.checkLadderSnake(5));
    }

}
