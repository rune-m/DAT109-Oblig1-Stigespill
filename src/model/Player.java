package model;

public class Player {

    private final int playerNr;
    private final String name;
    private int position;
    private int turnsInARow; // Count of a players turn in row if dice = 6

    public Player(int playerNr, String name) {
        this.playerNr = playerNr;
        this.name = name;
        this.position = 0;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    public String getName() {
        return name;
    }

    public boolean isLocked() {
        return this.turnsInARow == 3;
    }

    public void removeLock() {
        this.turnsInARow = 0;
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

    @Override
    public String toString() {
        return name;
    }
}
