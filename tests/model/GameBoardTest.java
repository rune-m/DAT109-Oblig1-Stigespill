package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameBoardTest {

    GameController game = new GameController();
    GameBoard board = game.getBoard();

    @BeforeEach
    public void addLaddersSnakes() {
        board.removeLaddersAndSnakes();

        board.addLadderSnake(3, 5);
        board.addLadderSnake(10,8);
    }

    @Test
    public void ladderOrSnakeExistAndReturnsCorrectEndPosition() {
        assertEquals(5, board.checkLadderSnake(3));
        assertEquals(8, board.checkLadderSnake(10));
    }

    @Test
    public void ladderOrSnakeNotExistReturnsNegative1() {
        assertEquals(-1, board.checkLadderSnake(5));
    }

}
