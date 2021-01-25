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

    /**
     * Moves a player from old to new position and saves details about the move in a PlayerMoveDetails object.
     * Deals with the different scenarios a move can have. Landing on ladder/snake, rolling six, reaching goal line etc.
     *
     * @param player to be moved
     * @return Details about a players move
     */
    public PlayerMoveDetails movePlayer(int rolledDice, GameBoard board) {

        int oldPosition = position;
        int newPosition = position + rolledDice;
        PlayerMoveDetails move;

        if (locked) {
            if (rolledDice != 6) {
                move = new PlayerMoveDetails(PlayerStates.LOCKED, rolledDice, oldPosition, 0, -1);
                return move;
            } else {
                locked = false;
            }
        }

        // Check for one more turn, if not lock
        if (rolledDice == 6) {
            incrementTurnsInARow();
            if (turnsInARow == 3) {
                position = 0;
                move = new PlayerMoveDetails(PlayerStates.LOCKED, rolledDice, oldPosition, position, -1);
                return move;
            }
        } else {
            resetTurnsInARow();
        }

        int newPosAfterSnakeLadder = board.checkLadderSnake(newPosition);

        if (newPosAfterSnakeLadder != -1) { // Check for ladder/snake at the new position
            position = newPosAfterSnakeLadder;
            PlayerStates state = newPosAfterSnakeLadder - newPosition > 0 ? PlayerStates.LADDER : PlayerStates.SNAKE;
            move = new PlayerMoveDetails(state, rolledDice, oldPosition, position, newPosition);
        } else if (newPosition < board.getTiles()) { // Traditional move
            position = newPosition;
            move = new PlayerMoveDetails(PlayerStates.MOVED, rolledDice, oldPosition, position, -1);
        } else if (newPosition > board.getTiles()) { // Outside the table, go back
            move = new PlayerMoveDetails(PlayerStates.SAME_POS, rolledDice, oldPosition, position, -1);
        } else { // Goal
            position = newPosition;
            move = new PlayerMoveDetails(PlayerStates.FINISHED, rolledDice, oldPosition, position, -1);
        }

        if (rolledDice == 6) {
            move.setOneMoreTurn(true);
        }

        return move;
    }

}
