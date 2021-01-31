package model;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {

    private final int TILES = 100;
    private Map<Integer, Integer> laddersAndSnakes;

    public GameBoard() {
        this.laddersAndSnakes = new HashMap<>();
        setLaddersAndSnakes();
    }

    public int getTiles() {
        return TILES;
    }

    /**
     * Add a ladder/snake key/value to the HashMap.
     * Key of the map instance is the start of the ladder/snake.
     * Value of the map instance is the end of the ladder/snake.
     *
     * @param from Key
     * @param to   Value
     */
    public void addLadderSnake(int from, int to) {
        laddersAndSnakes.put(from, to);
    }

    /**
     * Check whether of not the position given contains the start of a ladder/snake.
     * If true, return the end of ladder/snake (Value in HashMap).
     * If false, return -1.
     *
     * @param pos the tile the players moves to
     * @return end of ladder/snake or -1
     */
    public int checkLadderSnake(int pos) {
        return laddersAndSnakes.getOrDefault(pos, -1);
    }

    /**
     * Assig the predefined ladders and snakes to the ladderAndSnakes data structure.
     * Placed in a function for cleaner constructor
     */
    private void setLaddersAndSnakes() {
        // Ladders
        laddersAndSnakes.put(1, 38);
        laddersAndSnakes.put(4, 14);
        laddersAndSnakes.put(9, 31);
        laddersAndSnakes.put(21, 42);
        laddersAndSnakes.put(28, 84);
        laddersAndSnakes.put(51, 67);
        laddersAndSnakes.put(71, 91);
        laddersAndSnakes.put(80, 99);
        // Snakes
        laddersAndSnakes.put(17, 7);
        laddersAndSnakes.put(54, 34);
        laddersAndSnakes.put(62, 19);
        laddersAndSnakes.put(64, 60);
        laddersAndSnakes.put(87, 24);
        laddersAndSnakes.put(93, 73);
        laddersAndSnakes.put(95, 75);
        laddersAndSnakes.put(98, 79);
    }

    /**
     * Remove all ladders and snakes from the board. Used for testing purposes.
     */
    public void removeLaddersAndSnakes() {
        laddersAndSnakes = new HashMap<>();
    }

}
