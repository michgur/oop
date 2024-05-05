public class GeniusPlayer implements Player {
    /** Directions to check for streaks. */
    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, -1 } };
    /** Signs to go in each direction. */
    private static final int[] SIGNS = { 1, -1 };

    /** Streak multipliers for self and opponent */
    private static final int STREAK_MULTIPLIER_SELF = 3, STREAK_MULTIPLIER_OPPONENT = 2;

    public GeniusPlayer() {
    }

    public void playTurn(Board board, Mark mark) {
        /*
         * Strategy:
         * - for each cell, we calculate the max streak that can be achieved by placing
         * a mark in it, for both players
         * - we then assign a score using the following formula:
         * score =
         * (streakSelf * STREAK_MULTIPLIER_SELF) +
         * (streakOpponent * STREAK_MULTIPLIER_OPPONENT)
         * - we select the cell with the highest score
         */

        int size = board.getSize();
        int maxScore = -1; // scores are non-negative
        int row = -1, col = -1; // coordinates of cell with max score
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getMark(i, j) != Mark.BLANK) {
                    // cell is occupied
                    continue;
                }
                // calculate score
                int score = cellScore(board, mark, i, j);
                if (score > maxScore) {
                    // found a better cell
                    maxScore = score;
                    row = i;
                    col = j;
                }
            }
        }
        // put mark
        board.putMark(mark, row, col);
    }

    private int cellScore(Board board, Mark mark, int row, int col) {
        Mark opponent = (mark == Mark.X) ? Mark.O : Mark.X;
        int streakSelf = maxStreak(board, mark, row, col);
        int streakOpponent = maxStreak(board, opponent, row, col);
        return (streakSelf * STREAK_MULTIPLIER_SELF) + (streakOpponent * STREAK_MULTIPLIER_OPPONENT);
    }

    /**
     * Get the max streak that can be achieved by placing a mark in the given cell.
     * 
     * @return max streak that can be achieved by placing a mark in the given cell
     */
    private int maxStreak(Board board, Mark mark, int row, int col) {
        // horizontal, vertical, diagonal, anti-diagonal streaks
        int[] streaks = { 1, 1, 1, 1 };

        for (int i = 0; i < DIRECTIONS.length; i++) {
            int[] direction = DIRECTIONS[i];
            for (int sign : SIGNS) {
                for (int steps = 1; steps < board.getSize(); steps++) {
                    // go `steps` steps in `sign * direction` from (row, col)
                    int r = row + (sign * steps * direction[0]);
                    int c = col + (sign * steps * direction[1]);

                    if (board.getMark(r, c) != mark) {
                        break;
                    }

                    streaks[i]++;
                }
            }
        }

        // return max streak
        return Math.max(Math.max(streaks[0], streaks[1]), Math.max(streaks[2], streaks[3]));
    }
}
