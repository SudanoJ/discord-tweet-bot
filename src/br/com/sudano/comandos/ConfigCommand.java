package br.com.sudano.comandos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import br.com.sudano.utils.BlockedWords;
import br.com.sudano.utils.Emojis;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * 
 * @author sudan
 *
 */
public class ConfigCommand extends ListenerAdapter {

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		if (!event.getMessage().getContentRaw().startsWith("p!config"))
			return;
		if (event.getMessage().getMember() == null)
			return;

		List<String> contentList = Arrays.asList(event.getMessage().getContentRaw().split(" "));
		String argsString = String.join(" ", contentList.subList(1, contentList.size()));
		String[] args = argsString.split(" ");

		if (argsString.length() == 0) {
			event.getMessage()
					.reply(Emojis.PENTAGRAMA.getEmoji()
							+ " | Comando incorreto, utilize ``p!config [refresh, add, list, remove]``")
					.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
			return;
		}

		if (args[0].toLowerCase().equalsIgnoreCase("refresh")) {
			try {
				BlockedWords.refresh();
				event.getMessage()
						.reply(Emojis.NIKE.getEmoji() + " | Lista ``blocked_words.json`` atualizada com sucesso!")
						.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
			} catch (FileNotFoundException exception) {
				event.getMessage()
						.reply(Emojis.PENTAGRAMA.getEmoji() + " | Ocorreu um erro ao atualizar a lista! " + "("
								+ exception.getMessage() + ")")
						.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
				exception.printStackTrace();
			}
		}

		else if (args[0].toLowerCase().equalsIgnoreCase("add")) {
			if (args.length < 2) {
				event.getMessage()
						.reply(Emojis.PENTAGRAMA.getEmoji()
								+ " | Comando incorreto, utilize ``p!config add <palavra>``")
						.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
				return;
			}
			String add = args[1].toLowerCase();

			if (BlockedWords.getList().contains(add.toLowerCase())) {
				event.getMessage().reply(Emojis.PENTAGRAMA.getEmoji() + " | Essa palavra já está bloqueada!")
						.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
			} else {
				try {
					BlockedWords.addWord(add.toLowerCase());
					event.getMessage()
							.reply(Emojis.NIKE.getEmoji() + " | Você adicionou a palavra ``" + args[1]
									+ "`` na lista de bloqueio!")
							.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
				} catch (IOException exception) {
					event.getMessage()
							.reply(Emojis.PENTAGRAMA.getEmoji() + " | Ocorreu um erro ao atualizar a lista! " + "("
									+ exception.getMessage() + ")")
							.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
					exception.printStackTrace();
				}
			}
		}

		else if (args[0].toLowerCase().equalsIgnoreCase("list")) {
			event.getMessage()
					.reply(Emojis.NIKE.getEmoji() + " | Lista de palavras bloqueadas: "
							+ BlockedWords.getList().toString().replace("[", "``").replace("]", "``") + "" + "\n"
							+ Emojis.NIKE.getEmoji() + " | Para adicionar ou remover utilize o comando ``p!config``")
					.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));

		}

		else if (args[0].toLowerCase().equalsIgnoreCase("remove")) {
			if (args.length < 2) {
				event.getMessage()
						.reply(Emojis.PENTAGRAMA.getEmoji()
								+ " | Comando incorreto, utilize ``p!config remove <palavra>``")
						.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
				return;
			}
			String remove = args[1].toLowerCase();
			if (BlockedWords.getList().contains(remove.toLowerCase())) {
				try {
					BlockedWords.removeWord(remove);
					event.getMessage()
							.reply(Emojis.NIKE.getEmoji() + " | Você removeu a palavra ``" + args[1]
									+ "`` da lista de bloqueio!")
							.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
				} catch (IOException exception) {
					event.getMessage()
							.reply(Emojis.PENTAGRAMA.getEmoji() + " | Ocorreu um erro ao atualizar a lista! " + "("
									+ exception.getMessage() + ")")
							.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
					exception.printStackTrace();
				}
			} else {
				event.getMessage()
						.reply(Emojis.PENTAGRAMA.getEmoji() + " | A palavra ``" + args[1] + "`` não está bloqueada!"
								+ "\n" + Emojis.NIKE.getEmoji()
								+ " | Para adicionar utilize o comando ``p!config add <palavra>``")
						.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
			}
		}

		else {
			event.getMessage()
					.reply(Emojis.PENTAGRAMA.getEmoji()
							+ " | Comando incorreto, utilize ``p!config [refresh, add, list, remove]``")
					.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
		}
	}

}
