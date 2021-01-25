package model;

public class PlayerMoveDetails {

    // The state of the player move
    private PlayerStates state;

    private int rolledDice;
    private int startPos;
    private int endPos;

    // The start of a ladder/snake if used
    private int ladderSnakeStart;

    // Players finish number
    private int playerFinishedNumber;

    // True if the players rolls six
    private boolean oneMoreTurn;

    public PlayerMoveDetails(PlayerStates state, int rolledDice, int startPos, int endPos, int ladderSnakeStart) {
        this.state = state;
        this.rolledDice = rolledDice;
        this.startPos = startPos;
        this.endPos = endPos;
        this.ladderSnakeStart = ladderSnakeStart;
    }

    public PlayerStates getState() {
        return state;
    }

    public int getStartPos() {
        return startPos;
    }

    public int getPlayerFinishedNumber() {
        return playerFinishedNumber;
    }

    public void setPlayerFinishedNumber(int playerFinishedNumber) {
        this.playerFinishedNumber = playerFinishedNumber;
    }

    public int getEndPos() {
        return endPos;
    }

    public int getRolledDice() {
        return rolledDice;
    }

    public boolean hasOneMoreTurn() {
        return oneMoreTurn;
    }

    public void setOneMoreTurn(boolean oneMoreTurn) {
        this.oneMoreTurn = oneMoreTurn;
    }

    public int getLadderSnakeStart() {
        return ladderSnakeStart;
    }

}
