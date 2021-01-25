package model;

public class PlayerMoveDetails {

    // The state of the player move
    private PlayerStates state = PlayerStates.UNKNOWN;
    private final int rolledDice;
    private final int startPos;
    private int endPos;
    // The start of a ladder/snake if used
    private int ladderSnakeStart;
    private int playerFinishedNumber;
    // True if the players rolls six
    private boolean oneMoreTurn;

    public PlayerMoveDetails(int rolledDice, int startPos) {
        this.rolledDice = rolledDice;
        this.startPos = startPos;
    }

    public PlayerStates getState() {
        return state;
    }

    public void setState(PlayerStates state) {
        this.state = state;
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

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    public int getRolledDice() {
        return rolledDice;
    }

    /**
     * Checks and returns the value of oneMoreTurn then swaps the boolean
     *
     * @return if the player should roll the dice once more
     */
    public boolean oneMoreTurn() {
//        boolean returnValue = oneMoreTurn;
//        oneMoreTurn = !oneMoreTurn;
//        return returnValue;
        return oneMoreTurn;
    }

    public void setOneMoreTurn(boolean oneMoreTurn) {
        this.oneMoreTurn = oneMoreTurn;
    }

    public int getLadderSnakeStart() {
        return ladderSnakeStart;
    }

    public void setLadderSnakeStart(int ladderSnakeStart) {
        this.ladderSnakeStart = ladderSnakeStart;
    }

}
