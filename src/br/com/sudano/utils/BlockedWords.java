package br.com.sudano.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * 
 * @author sudan
 *
 */
public class BlockedWords {

	private static List<String> list = null;

	public static void init() throws FileNotFoundException {
		JsonReader jsonReader = new JsonReader(
				new FileReader(System.getProperty("user.dir") + "\\src\\blocked_words.json"));
		list = new LinkedList<>(Arrays.asList(new Gson().fromJson(jsonReader, String[].class)));
	}

	public static boolean containsBadword(String input) {
		for (String word : list) {
			if (input.toLowerCase().contains(word.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static void genJson() throws IOException {
		String json = new Gson().toJson(list);

		System.out.println(json);

		FileWriter file = new FileWriter(System.getProperty("user.dir") + "\\src\\blocked_words.json");
		file.write(json);
		file.flush();
		file.close();
	}

	public static void refresh() throws FileNotFoundException {
		list.clear();
		init();
	}

	public static void addWord(String word) throws IOException {
		list.add(word);
		String json = new Gson().toJson(list);

		FileWriter file = new FileWriter(System.getProperty("user.dir") + "\\src\\blocked_words.json");

		file.write(json);
		file.flush();
		file.close();
		refresh();
	}

	public static void removeWord(String word) throws IOException {
		list.remove(word);
		String json = new Gson().toJson(list);

		FileWriter file = new FileWriter(System.getProperty("user.dir") + "\\src\\blocked_words.json");

		file.write(json);
		file.flush();
		file.close();
		refresh();
	}

	public static List<String> getList() {
		return list;
	}
}
