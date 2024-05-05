public class Game {
    private Player playerX, playerO;
    private Board board;
    private int winStreak;
    private Renderer renderer;

    private Mark winner = null;

    public Game(Player playerX, Player playerO, Renderer renderer) {
        this(playerX, playerO, new Board().getSize(), 0, renderer);
    }

    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.board = new Board(size);

        this.winStreak = validateWinStreak(winStreak, size) ? winStreak : size;
        this.renderer = renderer;
    }

    public int getWinStreak() {
        return winStreak;
    }

    Mark run() {
        // only run game once
        if (winner != null) {
            return winner;
        }
        boolean isXTurn = true; // used to select player - X starts
        // run until there's a winner
        while (winner == null) {
            renderer.renderBoard(board);
            // make a turn
            Player player = isXTurn ? playerX : playerO;
            Mark mark = isXTurn ? Mark.X : Mark.O;
            player.playTurn(board, mark);
            // check for win (check entire board as we don't know where the mark was placed)
            winner = checkForWin(); // winner is null if game is not over
            // switch turns
            isXTurn = !isXTurn;
        }
        // game over - render winning board and return winner
        renderer.renderBoard(board);
        return winner;
    }

    /**
     * Check if game is over. Assumes only one player can win.
     * 
     * @return mark of winner if exists, `Mark.BLANK` if board is full, `null`
     *         otherwise (game is not over)
     */
    private Mark checkForWin() {
        boolean isFull = true;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Mark mark = board.getMark(row, col);
                boolean isOccupied = mark != Mark.BLANK;
                // if a cell is occupied, check if it is top-left corner of a winning streak
                if (isOccupied && checkCellForWin(mark, row, col)) {
                    return mark;
                }
                // if a cell is blank, board is not full
                isFull &= isOccupied;
            }
        }
        return isFull ? Mark.BLANK : null;
    }

    /**
     * Check if cell is top-left corner of a winning streak
     * 
     * @param Mark
     *            mark to check for (assumed to be mark at given cell)
     * @param row
     * @param col
     * @return whether cell is top-left corner of a winning streak for given mark
     */
    private boolean checkCellForWin(Mark mark, int row, int col) {
        // check horizontal, vertical, diagonal, anti-diagonal streaks
        boolean hStreak = true, vStreak = true, dStreak = true, adStreak = true;

        // check `winStreak - 1` cells to the right, down, down-right, down-left
        for (int i = 1; i < winStreak; i++) {
            hStreak &= board.getMark(row, col + i) == mark;
            vStreak &= board.getMark(row + i, col) == mark;
            dStreak &= board.getMark(row + i, col + i) == mark;
            adStreak &= board.getMark(row + i, col - i) == mark;
        }

        // return true if any streak is found
        return hStreak || vStreak || dStreak || adStreak;
    }

    private static boolean validateWinStreak(int winStreak, int size) {
        // valid winStreak is between 2 and `size`
        return winStreak <= size && winStreak >= 2;
    }
}
