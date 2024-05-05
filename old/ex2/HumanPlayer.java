import java.util.Scanner;

public class HumanPlayer implements Player {
    private Scanner scanner = new Scanner(System.in);

    public HumanPlayer() {
    }

    public void playTurn(Board board, Mark mark) {
        int coordinates;
        boolean firstTime = true; // first time - different message
        do {
            // ask user for coordinates
            coordinates = askForCoordinates(mark, firstTime);
            firstTime = false;
            // try to put the mark;
            // `putMark` handles invalid coordinates and occupied cells
        } while (!board.putMark(mark, coordinates / 10, coordinates % 10));
        // mark was put successfully, we can return
    }

    /**
     * Ask the user for coordinates.
     * 
     * @param mark
     *            the mark of the player
     * @param firstTime
     *            true if this is the first time asking for coordinates
     * @return the coordinates as XY (e.g. X * 10 + Y)
     */
    private int askForCoordinates(
            Mark mark,
            boolean firstTime) {
        // ask for coordinates
        String message = firstTime ? "Player " + mark + ", type coordinates: "
                : "Invalid coordinates, type again: ";
        System.out.println(message);
        return scanner.nextInt();
    }
}
