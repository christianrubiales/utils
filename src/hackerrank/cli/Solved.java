package hackerrank.cli;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Displays the number of solved problems in HackerRank given the usernames.
 */
public class Solved {

	private static final Gson gson = new GsonBuilder().create();
	private static final String PREFIX = "https://www.hackerrank.com/rest/hackers/";
	private static final String SUFFIX = "/recent_challenges?offset=0&limit=100000";
	
	/**
	 * @param args contains a list of usernames
	 */
	public static void main(String[] args) throws JsonSyntaxException, IOException {
		for (String username : args) {
			display(username);
		}
	}

	public static void display(String username) throws JsonSyntaxException, IOException {
		URL url = new URL(PREFIX + username + SUFFIX);
		Json json = gson.fromJson(IOUtils.toString(url), Json.class);
		System.out.println(username + " : " + json.models.size());
	}
	
	private static final class Json {
		List<Model> models;
	}
	
	private static final class Model {
		String name;
		String ch_slug;
		String created_at;
		int dynamic;
		String con_slug;
		String id;
		String url;
	}
}
