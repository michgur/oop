import java.util.*;

/**
 * A ChatterBot is a bot that can reply to requests. Legal requests start with
 * REQUEST_PREFIX.
 * 
 * @author Michael Gur
 */
class ChatterBot {
	/**
	 * All legal requests should start with this prefix.
	 */
	static final String REQUEST_PREFIX = "say ";
	/**
	 * Placeholder for the requested phrase in replies to legal requests.
	 */
	static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";
	/**
	 * Placeholder for the request in replies to illegal requests.
	 */
	static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";

	Random rand = new Random();
	String name;
	String[] legalRequestsReplies, repliesToIllegalRequest;

	/**
	 * Creates a new ChatterBot.
	 * 
	 * @param name                    The name of the bot.
	 * @param legalRequestsReplies    An array of patterns for replies to legal
	 *                                requests.
	 * @param repliesToIllegalRequest An array of patterns for replies to illegal
	 *                                requests.
	 */
	ChatterBot(String name, String[] legalRequestsReplies, String[] repliesToIllegalRequest) {
		this.name = name;
		this.legalRequestsReplies = new String[legalRequestsReplies.length];
		for (int i = 0; i < legalRequestsReplies.length; i = i + 1) {
			this.legalRequestsReplies[i] = legalRequestsReplies[i];
		}
		this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
		for (int i = 0; i < repliesToIllegalRequest.length; i = i + 1) {
			this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
		}
	}

	/**
	 * @return The name of the bot.
	 */
	String getName() {
		return name;
	}

	/**
	 * @param statement The statement to reply to.
	 * @return The bot's reply; If the statement is legal (i.e. prefixed with
	 *         REQUEST_PREFIX), then the reply is taken from
	 *         legalRequestsReplies, otherwise, the reply is taken from
	 *         repliesToIllegalRequest.
	 */
	String replyTo(String statement) {
		if (statement.startsWith(REQUEST_PREFIX)) {
			return replyToLegalRequest(statement);
		}
		return replyToIllegalRequest(statement);
	}

	/**
	 * @param statement The statement to reply to, assumed to be legal.
	 * @return The bot's reply to a legal request. Every occurrence of
	 *         PLACEHOLDER_FOR_REQUESTED_PHRASE in the replies is replaced
	 *         with the requested phrase.
	 */
	String replyToLegalRequest(String statement) {
		String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
		return replacePlaceholderInARandomPattern(legalRequestsReplies, PLACEHOLDER_FOR_REQUESTED_PHRASE, phrase);
	}

	/**
	 * @param statement The statement to reply to.
	 * @return The bot's reply to an illegal request. Every occurrence of
	 *         PLACEHOLDER_FOR_ILLEGAL_REQUEST in the replies is replaced with the
	 *         request.
	 */
	String replyToIllegalRequest(String statement) {
		return replacePlaceholderInARandomPattern(repliesToIllegalRequest, PLACEHOLDER_FOR_ILLEGAL_REQUEST, statement);
	}

	/**
	 * @param patterns    An array of patterns to randomly choose from.
	 * @param placeholder The placeholder to replace in the selected pattern.
	 * @param replacement The replacement for the placeholder.
	 * @return
	 */
	String replacePlaceholderInARandomPattern(String[] patterns, String placeholder, String replacement) {
		int randomIndex = rand.nextInt(patterns.length);
		String reply = patterns[randomIndex];
		return reply.replaceAll(placeholder, replacement);
	}
}
