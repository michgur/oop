import java.util.Random;

public class WhateverPlayer implements Player {
    private Random random = new Random();

    public WhateverPlayer() {
    }

    public void playTurn(Board board, Mark mark) {
        int size = board.getSize();
        int row, col;
        do {
            // choose a random cell
            row = random.nextInt(size);
            col = random.nextInt(size);
            // try to put the mark
        } while (!board.putMark(mark, row, col));
        // mark was put successfully, we can return
    }
}
