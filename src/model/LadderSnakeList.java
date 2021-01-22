package model;

import java.util.HashMap;
import java.util.Map;

public class LadderSnakeList {

    private final Map<Integer, Integer> map;

    public LadderSnakeList() {
        this.map = new HashMap<>();
    }

    /**
     * Add a ladder/snake key/value to the map.
     * Key of the map instance is the start of the ladder/snake.
     * Value of the map instance is the end of the ladder/snake.
     * @param from Key
     * @param to Value
     */
    public void add(int from, int to) {
        map.put(from, to);
    }

    /**
     * Check whether of not the position given contains the start of a ladder/snake.
     * If true, return the end of ladder/snake (value in map).
     * If false, return -1.
     * @param pos the tile the players moves to
     * @return end of ladder/snake or -1
     */
    public int checkLadderSnake(int pos) {
        return map.getOrDefault(pos, -1);
    }

}
