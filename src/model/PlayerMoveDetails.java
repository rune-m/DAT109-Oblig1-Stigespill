package model;

public class PlayerMoveDetails {

    private PlayerStates state = PlayerStates.UNKNOWN;
    private final int rolledDice;
    private final int startPos;
    private int endPos;
    private int ladderSnakeStart;
    private int playerFinishedNumber;

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

    public int getRolledDice() {
        return rolledDice;
    }

    public boolean oneMoreTurn() {
        boolean returnValue = oneMoreTurn;
        oneMoreTurn = !oneMoreTurn;
        return returnValue;
    }

    public void setOneMoreTurn(boolean oneMoreTurn) {
        this.oneMoreTurn = oneMoreTurn;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    public int getLadderSnakeStart() {
        return ladderSnakeStart;
    }

    public void setLadderSnakeStart(int ladderSnakeStart) {
        this.ladderSnakeStart = ladderSnakeStart;
    }

    @Override
    public String toString() {
        return "PlayerMoveDetails{" +
                "state=" + state +
                ", rolledDice=" + rolledDice +
                ", startPos=" + startPos +
                ", endPos=" + endPos +
                ", ladderSnakeStart=" + ladderSnakeStart +
                ", playerFinishedNumber=" + playerFinishedNumber +
                '}';
    }
}
