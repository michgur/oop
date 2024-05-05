public class PlayerFactory {
    /**
     * Build a player of the given type.
     * 
     * @param type
     * @return player of the given type
     */
    public Player buildPlayer(String type) {
        switch (type.toLowerCase()) {
            case "human":
                return new HumanPlayer();
            case "whatever":
                return new WhateverPlayer();
            case "clever":
                return new CleverPlayer();
            case "genius":
                return new GeniusPlayer();
            default:
                throw new IllegalArgumentException(
                        "Choose a player, and start again\nThe players: [human, clever, whatever, genius]");
        }
    }
}
