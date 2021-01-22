package view.console;

import java.util.Scanner;

public class ReadFromConsole {

    private final Scanner in = new Scanner(System.in);

    /**
     * Print a message to the console and then read the next line.
     *
     * @param message initial message to print prior to the read from console
     * @return the input from the console
     */
    public String readString(String message) {
        System.out.println(message);
        return in.nextLine();
    }

    /**
     * Print a message to the console and then read the next line of type Integer.
     * The input must be a positive integer.
     * @param message initial message to print prior to the read from console
     * @return the Integer input from the console
     */
    public int readInt(String message) {
        System.out.println(message);
        int input = -1;
        while (input < 0) {
            try {
                // Using parseInt() as I have bad experience with Scanner.class's nextInt()
                input = Math.abs(Integer.parseInt(in.nextLine()));
            } catch (NumberFormatException e) {
                System.out.println("...Your input must be a positive integer!");
            }
        }
        return input;
    }

}
