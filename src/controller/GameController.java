package controller;

import model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class GameController {

    private final Dice dice = new Dice();
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
//                        move = movePlayer(p, dice.roll());
                        move = p.movePlayer(dice.roll(), board);

                        if (move.getState() == PlayerStates.FINISHED) {
                            playerFinished(p);
                            move.setPlayerFinishedNumber(playersFinished.size());
                        }

                        view.accept(p, move);
                    } while (move.hasOneMoreTurn());
                    p.resetTurnsInARow();
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
     * Moving a player from players list to finishedPlayers list
     *
     * @param player that is going to be moved
     */
    private int playerFinished(Player player) {
        players = players.stream()
                .filter(p -> p.getPlayerNr() != player.getPlayerNr())
                .collect(Collectors.toList());
        playersFinished.add(player);

        return playersFinished.size();
    }

    /**
     * Used when the game is over (only one player remaining). Moves the last remaining player to the
     * finishedPlayers list for easily displaying a results list at the end of the game.
     */
    private void addLastPlayer() {
        Optional<Player> first = players.stream().findFirst();
        first.ifPresent(this::playerFinished);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> getPlayersFinished() {
        return playersFinished;
    }

    public GameBoard getBoard() {
        return board;
    }
}
