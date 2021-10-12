package br.com.sudano.utils;

public enum Emojis {

	PENTAGRAMA("<a:pentagrama:891979892458131476>"), NIKE("<a:nike:866028366712537129>");

	private String emoji;

	private Emojis(String emoji) {
		this.emoji = emoji;
	}

	public String getEmoji() {
		return emoji;
	}

}
