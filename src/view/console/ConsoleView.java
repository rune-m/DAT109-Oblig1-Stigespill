package view.console;

import controller.GameController;
import model.Player;
import model.PlayerMoveDetails;

import java.util.ArrayList;
import java.util.List;

public class ConsoleView {

    private final ReadFromConsole in = new ReadFromConsole();
    private final GameController game = new GameController();

    public ConsoleView() {
    }

    public void start() {
        createPlayers();
//        startGame(game.getPlayers());
        game.startGame(this::playersTurn, this::nextMove);
    }

    /**
     * Ask for number of players and create an List of players with names from console input
     */
    public void createPlayers() {
        int numberOfPlayers = readBoundedNumber("Antall spillere: ", 2, 4);

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            Player p = new Player(i + 1, in.readString("Skriv inn navn til spiller nr " + (i + 1) + "..."));
            players.add(p);
        }
        System.out.println("\n(Trykk enter for å fortsette...)");
        game.createPlayers(players);
    }

    // Lambda inn i playGame() i Controller
    public void playersTurn(Player player, PlayerMoveDetails move) {

        switch (move.getState()) {
            case FINISHED -> System.out.println("  - Trilte " + move.getRolledDice() + " og er i mål som nummer " + move.getPlayerFinishedNumber() + "!");
            case MOVED -> System.out.println("  - Trilte " + move.getRolledDice() + " og flyttet fra " + move.getStartPos() + " til " + move.getEndPos() + ".");
            case SAME_POS -> System.out.println("  - Blir stående i ro på " + move.getEndPos() + " etter å ha trilt " + move.getRolledDice() + ". Må trille " + (game.getTiles() - move.getEndPos()) + " for å nå mål...");
            case LADDER -> System.out.println("  - Trilte " + move.getRolledDice() + " og flyttet fra " + move.getStartPos() + " til en STIGE på rute " + move.getLadderSnakeStart() + ". Klatrer til " + move.getEndPos() + "!");
            case SNAKE -> System.out.println("  - Trilte " + move.getRolledDice() + " og flyttet fra " + move.getStartPos() + " til en SLANGE på rute " + move.getLadderSnakeStart() + ". Sklir ned til " + move.getEndPos() + "!");
            case LOCKED -> System.out.println("  - Trilte " + move.getRolledDice() + ". Du har trilt tre 6'ere på rad og er låst på start (rute 0) frem til du triller en 6'er...");
            default -> System.out.println("ERR: Couldn't print current move");
        }

        if (move.oneMoreTurn()) {
            System.out.print("      - Du får trille en gang til siden du fikk en 6'er!");
        }

    }

    public void nextMove() {
        in.readString("");
    }

    public int readBoundedNumber(String message, int min, int max) {
        int value;
        boolean valid;

        do {
            value = in.readInt(message);

            valid = value <= max && value >= min;
            if (!valid) {
                System.out.println("Tallet må være mellom " + min + " og " + max);
            }

        } while (!valid);

        return value;
    }


//    public void startGame(List<Player> players) {
//
//        List<Player> remainingPlayers = players;
//
//        while (remainingPlayers.size() > 1) {
//
//            System.out.println(remainingPlayers.size());
//            remainingPlayers.forEach(this::playersTurn);
//
//            System.out.println("-------------------");
//
//            remainingPlayers = remainingPlayers.stream()
//                    .filter(p -> p.getPosition() < 100)
//                    .collect(Collectors.toList());
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//        System.out.println("DONE");
//    }

}
