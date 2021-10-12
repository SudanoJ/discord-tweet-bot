package br.com.sudano.comandos;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.sudano.Bot;
import br.com.sudano.utils.BlockedWords;
import br.com.sudano.utils.DiscordWebhook;
import br.com.sudano.utils.Emojis;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * 
 * @author sudan
 *
 */
public class TweetCommand extends ListenerAdapter {

	// URL do Webhook para enviar as logs dos tweet's feitos
	public final static String WEBHOOK_URL = "https://discord.com/api/webhooks/WEBHOOK_CODE";

	// boolean simples para o uso do coldown do comando
	public static boolean canUseCommand = true;

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		if (!event.getMessage().getContentRaw().startsWith("p!tweet"))
			return;
		if (event.getMessage().getMember() == null)
			return;

		if (canUseCommand == true) {
			if (event.getMessage().getContentRaw().length() > 400) {
				event.getMessage()
						.reply(Emojis.PENTAGRAMA.getEmoji() + " | Essa mensagem é muito grande para ser processada!")
						.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
				return;
			}

			List<String> args = Arrays.asList(event.getMessage().getContentRaw().split(" "));
			String tweet = String.join(" ", args.subList(1, args.size()));

			for (String blocked_words : BlockedWords.getList()) {
				if (tweet.toLowerCase().contains(blocked_words)) {
					event.getMessage()
							.reply(Emojis.PENTAGRAMA.getEmoji()
									+ " | Uma ou mais palavras digitadas estão na lista de palavras bloqueadas!")
							.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
					return;
				}
			}

			// 7 = "p!tweet " || 281 = limite máximo de caractere do twitter.
			if (tweet.length() >= 281) {
				event.getMessage()
						.reply(Emojis.PENTAGRAMA.getEmoji() + " | O tweet não pode ultrapassar de 280 caracteres! ``("
								+ tweet.length() + "/280)``")
						.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
				return;
			}

//			TODO: Suportar imagens
//			if (event.getMessage().getAttachments().size() > 0) {}

			if (tweet.length() <= 3) {
				event.getMessage()
						.reply(Emojis.PENTAGRAMA.getEmoji() + " | O tweet deve ser maior de 3 caracteres! ``("
								+ tweet.length() + "/280)``")
						.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
				return;
			}

			try {
				canUseCommand = false;
				long ms = System.currentTimeMillis();
				Bot.tweet(tweet);
				event.getMessage()
						.reply(Emojis.NIKE.getEmoji() + " | Tweet feito com sucesso! ("
								+ (System.currentTimeMillis() - ms) + "ms)")
						.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));

			} catch (IOException exceptionTweet) {
				event.getMessage().reply(Emojis.PENTAGRAMA.getEmoji() + " | Não foi possível tweetar a sua mensagem!")
						.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
			}

			DiscordWebhook webhook = new DiscordWebhook(WEBHOOK_URL);
			webhook.setContent("> " + event.getAuthor().getName() + " acabou de fazer um tweet. ("
					+ event.getAuthor().getId() + ")");

			// Método bem gambiarra de timer em async, mas que funciona perfeitamente.
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					canUseCommand = true;
				}
			}, 60 * 1000);

			try {
				webhook.execute();
			} catch (IOException exceptionWebhook) {
				event.getMessage().getTextChannel().sendMessage("Ocorreu um erro ao anunciar a log do ultimo tweet.")
						.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
			}

		} else {
			event.getMessage()
					.reply(Emojis.PENTAGRAMA.getEmoji()
							+ " | O comando está em cooldown, aguarde **1 minuto** para usar novamente!")
					.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
			return;
		}
	}

}
