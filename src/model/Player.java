package model;

public class Player {

    private final int playerNr;
    private final String name;
    private int position;
    private int turnsInARow; // Count of a players turns in row. Increases if dice = 6
    private boolean locked;

    public Player(int playerNr, String name) {
        this.playerNr = playerNr;
        this.name = name;
        this.position = 0;
        locked = false;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    public String getName() {
        return name;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void resetTurnsInARow() {
        turnsInARow = 0;
    }

    public int getTurnsInARow() {
        return turnsInARow;
    }

    public void incrementTurnsInARow() {
        turnsInARow++;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
