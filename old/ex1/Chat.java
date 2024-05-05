import java.util.Scanner;

class Chat {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    ChatterBot[] bots = {
        new ChatterBot("Sammy", new String[] { "Saying " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER },
            new String[] { "what", "say I should say " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER }),
        new ChatterBot("Ruthy", new String[] { "Shouting " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER },
            new String[] { "whaaat is " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER, "say say" })
    };

    String statement = "hi";

    for (int i = 0;; i++) {
      ChatterBot bot = bots[i % bots.length];

      statement = bot.replyTo(statement);
      System.out.print(bot.getName() + ": ");
      System.out.print(statement);
      scanner.nextLine();
    }
    scanner.close();
  }
}
