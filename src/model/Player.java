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

//        int rolledDice = dice.roll();
//        rolledDice = 6;
        int oldPosition = position;
        int newPosition = position + rolledDice;

        // Creating new PlayerMoveDetails-object with parameters rolledDice and startPos for the move
        PlayerMoveDetails move = new PlayerMoveDetails(rolledDice, oldPosition);

        if (locked) {
            if (rolledDice != 6) {
                move.setState(PlayerStates.LOCKED);
                move.setEndPos(0);
                return move;
            } else {
                locked = false;
            }
        }

        // Check for one more turn or locked
        if (rolledDice == 6) {
            incrementTurnsInARow();
            if (turnsInARow == 3) {
                move.setState(PlayerStates.LOCKED);
                position = 0;
                move.setEndPos(0);
                return move;
            }
            move.setOneMoreTurn(true);
        } else {
            resetTurnsInARow();
        }


        int newPosAfterSnakeLadder = board.checkLadderSnake(newPosition);

        if (newPosAfterSnakeLadder != -1) { // Check for ladder/snake at the new position
            position = newPosAfterSnakeLadder;

            PlayerStates state = newPosAfterSnakeLadder - newPosition > 0 ? PlayerStates.LADDER : PlayerStates.SNAKE;
            move.setState(state);
            move.setLadderSnakeStart(newPosition);
            move.setEndPos(newPosAfterSnakeLadder);
        } else if (newPosition < board.getTILES()) { // Traditional move
            position = newPosition;

            move.setState(PlayerStates.MOVED);
            move.setEndPos(newPosition);
        } else if (newPosition > board.getTILES()) { // Outside the table, go back
            move.setState(PlayerStates.SAME_POS);
            move.setEndPos(oldPosition);
        } else { // Goal
            position = newPosition;
            move.setEndPos(newPosition);
            playerFinished(player);

            move.setState(PlayerStates.FINISHED);
            move.setPlayerFinishedNumber(playersFinished.size());

        }

        return move;
    }

}
