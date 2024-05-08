import java.util.Scanner;

/**
 * Main file for EX0. Runs a chat with an array of ChatterBots.
 */
class Chat {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		ChatterBot[] bots = {
				new ChatterBot("Sammy", new String[] { "Nice " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE },
						new String[] { "what " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST, "say I should say" }),
				new ChatterBot("Ruthy", new String[] { ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + " - Amazing thought" },
						new String[] { "whaaat", "say say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST }),
		};

		String statement = "hi";

		for (int i = 0;; i++) {
			ChatterBot bot = bots[i % bots.length];

			statement = bot.replyTo(statement);
			System.out.print(bot.getName() + ": ");
			System.out.print(statement);
			scanner.nextLine();
		}
	}
}
