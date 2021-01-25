package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class GameController {

    // Player starts at tile 0 and wins when reaching tile 100
    private final int TILES = 100;

    private final Dice dice = new Dice();
//    private LadderSnakeList laddersAndSnakes;
    private List<Player> players;
    private final List<Player> playersFinished = new ArrayList<>();

    private GameBoard board = new GameBoard();

    public GameController() {
    }

    /**
     * Initialize players list to the given @param
     *
     * @param players list of players generated from the ui
     */
    public void createPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Moving a player from players list to finishedPlayers list
     *
     * @param player that is going to be moved
     */
    public void playerFinished(Player player) {
        players = players.stream()
                .filter(p -> p.getPlayerNr() != player.getPlayerNr())
                .collect(Collectors.toList());
        playersFinished.add(player);
    }

    /**
     * Starting the game. Looping through all players. Running until all except one player is finished.
     *
     * @param view           the view displaying the result about the move
     * @param signalNextTurn method that stops the code until user input
     */
    public void startGame(BiConsumer<Player, PlayerMoveDetails> view, Runnable signalNextTurn) {

        while (players.size() > 1) {
            // New round
            System.out.print("\n-----------------------------------------------------------");
            // Each players turn
            players.forEach(p -> {
                if (players.size() > 1) {
                    System.out.print("\n" + p.getName() + " sin tur:");
                    PlayerMoveDetails move;
                    do {
                        signalNextTurn.run();
                        move = movePlayer(p, dice.roll());
                        view.accept(p, move);
                    } while (move.oneMoreTurn());
                }
            });
        }
        addLastPlayer();

        System.out.println("\nResultatliste:");
        for (int i = 1; i <= playersFinished.size(); i++) {
            System.out.println(i + ". " + playersFinished.get(i - 1).getName());
        }
    }


    /**
     * Moves a player from old to new position and saves details about the move in a PlayerMoveDetails object.
     * Deals with the different scenarios a move can have. Landing on ladder/snake, rolling six, reaching goal line etc.
     *
     * @param player to be moved
     * @return Details about a players move
     */
    public PlayerMoveDetails movePlayer(Player player, int rolledDice) {

//        int rolledDice = dice.roll();
//        rolledDice = 6;
        int oldPosition = player.getPosition();
        int newPosition = player.getPosition() + rolledDice;

        // Creating new PlayerMoveDetails-object with parameters rolledDice and startPos for the move
        PlayerMoveDetails move = new PlayerMoveDetails(rolledDice, oldPosition);

        if (player.isLocked()) {
            if (rolledDice != 6) {
                move.setState(PlayerStates.LOCKED);
                move.setEndPos(0);
                return move;
            } else {
                player.setLocked(false);
            }
        }

        // Check for one more turn or locked
        if (rolledDice == 6) {
            player.incrementTurnsInARow();
            if (player.getTurnsInARow() == 3) {
                move.setState(PlayerStates.LOCKED);
                player.setPosition(0);
                move.setEndPos(0);
                return move;
            }
            move.setOneMoreTurn(true);
        } else {
            player.resetTurnsInARow();
        }


        int newPosAfterSnakeLadder = board.checkLadderSnake(newPosition);

        if (newPosAfterSnakeLadder != -1) { // Check for ladder/snake at the new position
            player.setPosition(newPosAfterSnakeLadder);

            PlayerStates state = newPosAfterSnakeLadder - newPosition > 0 ? PlayerStates.LADDER : PlayerStates.SNAKE;
            move.setState(state);
            move.setLadderSnakeStart(newPosition);
            move.setEndPos(newPosAfterSnakeLadder);
        } else if (newPosition < TILES) { // Traditional move
            player.setPosition(newPosition);

            move.setState(PlayerStates.MOVED);
            move.setEndPos(newPosition);
        } else if (newPosition > TILES) { // Outside the table, go back
            move.setState(PlayerStates.SAME_POS);
            move.setEndPos(oldPosition);
        } else { // Goal
            player.setPosition(newPosition);
            move.setEndPos(newPosition);
            playerFinished(player);

            move.setState(PlayerStates.FINISHED);
            move.setPlayerFinishedNumber(playersFinished.size());

        }

        return move;
    }

    /**
     * Used when the game is over (only one player remaining). Moves the last remaining player to the
     * finishedPlayers list for easily displaying a results list at the end of the game.
     */
    public void addLastPlayer() {
        Optional<Player> first = players.stream().findFirst();
        first.ifPresent(this::playerFinished);
    }

    public int getTiles() {
        return TILES;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> getPlayersFinished() {
        return playersFinished;
    }
}
