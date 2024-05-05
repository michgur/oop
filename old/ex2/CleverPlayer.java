public class CleverPlayer implements Player {
    private boolean playedTurn = false;

    public CleverPlayer() {
    }

    public void playTurn(Board board, Mark mark) {
        /*
         * Strategy:
         * - first turn - play randomly (using WhateverPlayer)
         * - if there's a cell adjacent to a mark of the same type, select it
         * - otherwise, select last available cell
         */

        if (!playedTurn) {
            // first turn - play randomly
            new WhateverPlayer().playTurn(board, mark);
            playedTurn = true;
            return;
        }

        int size = board.getSize();
        int row = 0, col = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getMark(i, j) == Mark.BLANK) {
                    // cell is available
                    row = i;
                    col = j;
                    // if it's also strategic, don't look further
                    if (isStrategic(board, mark, i, j)) {
                        break;
                    }
                }
            }
        }
        // put mark
        board.putMark(mark, row, col);
    }

    private boolean isStrategic(Board board, Mark mark, int row, int col) {
        int maxPos = board.getSize() - 1; // max valid index (row / col)
        // select cell if it's adjacent to a mark of the same type
        for (int i = Math.max(0, row - 1); i <= Math.min(maxPos, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(maxPos, col + 1); j++) {
                // skip current cell
                if (i == row && j == col) {
                    continue;
                }
                // check if adjacent cell is of the same type
                if (board.getMark(i, j) == mark) {
                    return true;
                }
            }
        }
        return false;
    }
}
