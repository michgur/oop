public class Tournament {
    private int rounds;
    private Renderer renderer;
    private Player[] players;

    /**
     * Create a new tournament
     * 
     * @param rounds
     *            number of rounds to play
     * @param renderer
     *            renderer to use
     * @param players
     *            players to play (must be 2)
     */
    public Tournament(int rounds, Renderer renderer, Player[] players) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.players = players;
    }

    /**
     * Play a tournament and print the results
     * 
     * @param size
     *            board size
     * @param winStreak
     *            win streak to win
     * @param playerNames
     *            names of players
     */
    public void playTournament(int size, int winStreak, String[] playerNames) {
        // score per player, and ties
        int[] results = new int[players.length + 1];

        for (int i = 0; i < rounds; i++) {
            // indices of X, O players
            int x = i % 2, o = (i + 1) % 2;

            // run a game
            Game game = new Game(players[x], players[o], size, winStreak, renderer);
            Mark winner = game.run();

            // update results
            int winnerIndex = winner == Mark.BLANK ? players.length : winner == Mark.X ? x : o;
            results[winnerIndex]++;
        }

        printResults(playerNames, results);
    }

    /**
     * Print the results of the tournament
     * 
     * @param playerNames
     * @param results
     *            scores per player, and ties (last element)
     */
    static private void printResults(String[] playerNames, int[] results) {
        System.out.println("######### Results #########");
        // print results
        for (int i = 0; i < playerNames.length; i++) {
            System.out.println("Player " + (i + 1) + ", " + playerNames[i] + " won: " + results[i] + " rounds");
        }
        System.out.println("Ties: " + results[playerNames.length]);
    }

    /**
     * Main method - run a tournament
     * 
     * @param args
     *            0 - number of rounds
     *            1 - board size
     *            2 - win streak
     *            3 - render target [console, none]
     *            4 - player 1 type [human, whatever, clever, genius]
     *            5 - player 2 type [human, whatever, clever, genius]
     */
    public static void main(String[] args) {
        // initialize factories
        RendererFactory rendererFactory = new RendererFactory();
        PlayerFactory playerFactory = new PlayerFactory();

        // parse arguments
        int rounds = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);
        Renderer renderer = rendererFactory.buildRenderer(args[3], size);
        Player[] players = {
                playerFactory.buildPlayer(args[4]),
                playerFactory.buildPlayer(args[5])
        };
        // run tournament
        Tournament tournament = new Tournament(rounds, renderer, players);
        tournament.playTournament(size, winStreak, new String[] { args[4], args[5] });
    }
}
