import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 * 
 * @author Dan Nirel
 */
class ChatterBot {
  static final String REQUEST_PREFIX = "say ";
  static final String REQUESTED_PHRASE_PLACEHOLDER = "<phrase>";
  static final String ILLEGAL_REQUEST_PLACEHOLDER = "<request>";

  Random rand = new Random();
  String[] repliesToLegalRequest, repliesToIllegalRequest;

  String name;

  ChatterBot(
      String name,
      String[] repliesToLegalRequest,
      String[] repliesToIllegalRequest) {
    this.name = name;
    this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
    this.repliesToLegalRequest = new String[repliesToLegalRequest.length];
    for (int i = 0; i < repliesToLegalRequest.length; i = i + 1) {
      this.repliesToLegalRequest[i] = repliesToLegalRequest[i];
    }
    for (int i = 0; i < repliesToIllegalRequest.length; i = i + 1) {
      this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
    }
  }

  String replyTo(String statement) {
    if (statement.startsWith(REQUEST_PREFIX)) {
      // we don’t repeat the request prefix, so delete it from the reply
      String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
      return replacePlaceholderInARandomPattern(phrase, repliesToLegalRequest, REQUESTED_PHRASE_PLACEHOLDER);
    }
    return replacePlaceholderInARandomPattern(statement, repliesToIllegalRequest, ILLEGAL_REQUEST_PLACEHOLDER);
  }

  String replacePlaceholderInARandomPattern(
      String phrase,
      String[] patterns,
      String placeholder) {
    int randomIndex = rand.nextInt(patterns.length);
    String pattern = patterns[randomIndex];
    return pattern.replaceAll(placeholder, phrase);
  }

  String getName() {
    return name;
  }
}
