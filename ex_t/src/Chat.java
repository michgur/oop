import java.util.Scanner;
import danogl.GameManager;
import danogl.util.Vector2;

class Chat extends GameManager {
  public Chat() {
    super("Chat", new Vector2(100, 100));
  }

  public static void main(String[] args) {
    new Chat().run();
    // Scanner scanner = new Scanner(System.in);
    //
    // ChatterBot[] bots = {
    // new ChatterBot("Sammy", new String[] { "Saying " +
    // ChatterBot.REQUESTED_PHRASE_PLACEHOLDER },
    // new String[] { "what", "say I should say " +
    // ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER }),
    // new ChatterBot("Ruthy", new String[] { "Shouting " +
    // ChatterBot.REQUESTED_PHRASE_PLACEHOLDER },
    // new String[] { "whaaat is " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER, "say
    // say" })
    // };
    //
    // String statement = "hi";
    //
    // for (int i = 0;; i++) {
    // ChatterBot bot = bots[i % bots.length];
    //
    // statement = bot.replyTo(statement);
    // System.out.print(bot.getName() + ": ");
    // System.out.print(statement);
    // scanner.nextLine();
    // }
  }
}
