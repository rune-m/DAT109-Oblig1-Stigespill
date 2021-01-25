package controller;

import model.Player;
import model.PlayerMoveDetails;
import model.PlayerStates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameControllerTest {

    GameController game;
    Player player1;
    Player player2;
    int tiles;

    int positionCloseToGoal;

    @BeforeEach
    public void createNewPlayer() {
        game = new GameController();
        player1 = new Player(1, "TestPlayer1");
        player2 = new Player(2, "TestPlayer2");

        // All functionality inside this method is tested in LadderSnakeListTest.class
        game.setLaddersAndSnakes();

        tiles = game.getTiles();
        positionCloseToGoal = tiles - 3;

        game.createPlayers(Arrays.asList(player1, player2));
    }

    @Test
    public void playerMoveToNormalTile() {
        player1.setPosition(0);

        int rolledDice = 5;
        PlayerMoveDetails move = game.movePlayer(player1, rolledDice);

        assertEquals(5, move.getEndPos());
        assertEquals(5, player1.getPosition());
        assertEquals(PlayerStates.MOVED, move.getState());
    }

    @Test
    public void playerFinished() {
        // Starts at position 3 tiles from goal (preferable with no ladders/snakes starting here)
        player1.setPosition(positionCloseToGoal);

        int rolledDice = tiles - positionCloseToGoal;
        PlayerMoveDetails move = game.movePlayer(player1, rolledDice);

        assertEquals(tiles, move.getEndPos());
        assertEquals(tiles, player1.getPosition());
        assertEquals(PlayerStates.FINISHED, move.getState());

        // Only player2 should be in list of players
        assertEquals(Arrays.asList(player2), game.getPlayers());

        // Only player1 should be in list of finished players
        assertEquals(Arrays.asList(player1), game.getPlayersFinished());

    }

    @Test
    public void playerFinishedAndListsUpdated() {
        player1.setPosition(positionCloseToGoal);
        int rolledDice = tiles - positionCloseToGoal;
        PlayerMoveDetails move = game.movePlayer(player1, rolledDice);

        // Only player2 should be in list of players
        assertEquals(Arrays.asList(player2), game.getPlayers());

        // Only player1 should be in list of finished players
        assertEquals(Arrays.asList(player1), game.getPlayersFinished());
    }

    @Test
    public void playerMovedPastGoalAndShouldMoveBack() {
        player1.setPosition(positionCloseToGoal);
        int rolledDice = tiles - positionCloseToGoal + 1;
        PlayerMoveDetails move = game.movePlayer(player1, rolledDice);

        assertEquals(positionCloseToGoal, move.getEndPos());
        assertEquals(positionCloseToGoal, player1.getPosition());
        assertEquals(PlayerStates.SAME_POS, move.getState());
    }

    @Test
    public void playerRolls3x6AndGetsLockedAtStart() {

        player1.setPosition(0);
        int rolledDice = 6;

        game.movePlayer(player1, rolledDice);
        game.movePlayer(player1, rolledDice);
        PlayerMoveDetails lastMove = game.movePlayer(player1, rolledDice);

        assertEquals(3, player1.getTurnsInARow());

        assertEquals(0, lastMove.getEndPos());
        assertEquals(0, player1.getPosition());
        assertEquals(PlayerStates.LOCKED, lastMove.getState());

    }

    @Test
    public void playerIsLockedRolls5AndThenStillLocked() {

        player1.setPosition(0);
        player1.setLocked(true);

        int rolledDice = 5;

        PlayerMoveDetails move = game.movePlayer(player1, rolledDice);

        assertEquals(0, move.getEndPos());
        assertEquals(0, player1.getPosition());
        assertEquals(PlayerStates.LOCKED, move.getState());

    }

    @Test
    public void playerIsLockedRolls6AndThenNotLocked() {

        player1.setPosition(0);
        player1.setLocked(true);

        int rolledDice = 6;

        PlayerMoveDetails move = game.movePlayer(player1, rolledDice);

        assertEquals(6, move.getEndPos());
        assertEquals(6, player1.getPosition());
        assertEquals(PlayerStates.MOVED, move.getState());

        assertEquals(true, move.oneMoreTurn());

    }

    @Test
    public void playerGetsUnlockedAndRolls3x6AndGetsLockedAgain() {

//        playerIsLockedRolls6AndThenNotLocked();

        player1.setPosition(0);

        int rolledDice = 6;

        PlayerMoveDetails moveFromStart = game.movePlayer(player1, rolledDice);
        game.movePlayer(player1, rolledDice);
        PlayerMoveDetails moveThatsLocks = game.movePlayer(player1, rolledDice);

//        playerRolls3x6AndGetsLockedAtStart();

        assertEquals(3, player1.getTurnsInARow());

        assertEquals(0, moveThatsLocks.getEndPos());
        assertEquals(0, player1.getPosition());
        assertEquals(PlayerStates.LOCKED, moveThatsLocks.getState());


        // Tweak Dice class to give 3x6 at first then random
        // To test locks



    }

    @Test
    public void playerMovedToALadder() {

        player1.setPosition(0);
        int rolledDice = 1; // There is a ladder at pos 1

        PlayerMoveDetails move = game.movePlayer(player1, rolledDice);

        assertEquals(38, move.getEndPos());
        assertEquals(38, player1.getPosition());
        assertEquals(1, move.getLadderSnakeStart());
        assertEquals(PlayerStates.LADDER, move.getState());

    }

    @Test
    public void playerMovedToASnake() {

        player1.setPosition(15);
        int rolledDice = 2; // There is a snake at pos 17

        PlayerMoveDetails move = game.movePlayer(player1, rolledDice);

        assertEquals(7, move.getEndPos());
        assertEquals(7, player1.getPosition());
        assertEquals(17, move.getLadderSnakeStart());
        assertEquals(PlayerStates.SNAKE, move.getState());

    }


}
