public class Board {
    private static final int DEFAULT_SIZE = 3;

    private int size;
    private Mark[][] board;

    public Board() {
        this(DEFAULT_SIZE);
    }

    public Board(int size) {
        this.size = size;
        board = new Mark[size][size];

        // clear the board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = Mark.BLANK;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Mark[][] getBoard() {
        return board;
    }

    public boolean putMark(Mark mark, int row, int col) {
        // if out of bounds or occupied, return false
        if (!validateCoordinates(row, col) || getMark(row, col) != Mark.BLANK) {
            return false;
        }

        // otherwise, put the mark and return true
        board[row][col] = mark;
        return true;
    }

    public Mark getMark(int row, int col) {
        // if out of bounds, return BLANK
        if (!validateCoordinates(row, col)) {
            return Mark.BLANK;
        }

        // otherwise, return the mark
        return board[row][col];
    }

    /**
     * Check that the coordinates are in range
     * 
     * @return true if the coordinates are valid
     */
    private boolean validateCoordinates(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board.length;
    }
}
