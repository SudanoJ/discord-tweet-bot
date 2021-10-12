package br.com.sudano.comandos;

import br.com.sudano.utils.Emojis;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpCommand extends ListenerAdapter {

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		if (!event.getMessage().getContentRaw().startsWith("p!help"))
			return;
		if (event.getMessage().getMember() == null)
			return;

		event.getMessage().reply(Emojis.NIKE.getEmoji() + " | Comandos: ``p!tweet <mensagem>`` e ``p!config``.")
				.queue(null, err -> System.out.println("Ocorreu um erro ao enviar uma mensagem!"));
	}

}
