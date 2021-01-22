package model;

public class Dice {

    private final int sides;

    public Dice() {
        this.sides = 6;
    }

    /**
     * Simulate rolling a dice with n sides
     * @return result of the dice
     */
    public int roll() {
        return (int) (Math.random() * sides + 1);
    }

}
