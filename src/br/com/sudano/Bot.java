package br.com.sudano;

import java.io.IOException;
import java.net.URL;

import javax.security.auth.login.LoginException;

import br.com.sudano.comandos.ConfigCommand;
import br.com.sudano.comandos.HelpCommand;
import br.com.sudano.comandos.TweetCommand;
import br.com.sudano.utils.BlockedWords;
import br.com.sudano.utils.ResourceUtil;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

/**
 * 
 * @author sudan
 * @source https://github.com/SudanoJ/discord-tweet-bot
 *
 */
public class Bot {

	public static final String TOKEN = "INSERT_YOUR_TOKEN";

	private static JDA jdaBuilder;

	private static final String JSON_CREDENCIAIS = "config.json";
	private static final TwitterCredentials TWITTER_CREDENTIALS;
	private static final TwitterClient TWITTER_CLIENT;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		login();	
		BlockedWords.init();
	}
	
	static {
		try {
			URL json = ResourceUtil.getResource(JSON_CREDENCIAIS, Bot.class);

			TWITTER_CREDENTIALS = TwitterClient.OBJECT_MAPPER.readValue(json, TwitterCredentials.class);
			TWITTER_CLIENT = new TwitterClient(TWITTER_CREDENTIALS);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void tweet(String tweet) throws IOException {
		if (tweet.length() >= 280) {
			System.out.println("> O tweet digitado ultrapassa de 280 caracteres. (" + tweet.length() + ")");
		} else {
			TWITTER_CLIENT.postTweet(tweet);
		}
	}

	public static void login() {
		try {
			jdaBuilder = JDABuilder.createDefault(TOKEN).setBulkDeleteSplittingEnabled(true)
					.setStatus(OnlineStatus.DO_NOT_DISTURB).setActivity(Activity.playing("p!tweet"))
					.addEventListeners(new TweetCommand())
					.addEventListeners(new ConfigCommand())
					.addEventListeners(new HelpCommand())
					.build();
		} catch (LoginException loginException) {
			System.out.println("Ocorreu um erro ao logar. (Token inválido?)");
		}
	}

}
