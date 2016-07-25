package hackerrank.cli;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Lists the languages used by a user.
 */
public class Languages {
	
	private static final Gson gson = new GsonBuilder().create();
	private static final String PREFIX = "https://www.hackerrank.com/rest/contests/master/hackers/";
	private static final String SUFFIX = "/profile";

	public static void main(String[] args) throws MalformedURLException, IOException {
		for (String username : args) {
			display(username);
		}
	}

	private static void display(String username) throws MalformedURLException, IOException {
		System.out.println(username);
		URL url = new URL(PREFIX + username + SUFFIX);
		Json json = gson.fromJson(IOUtils.toString(url), Json.class);
		System.out.println("hacker_iq : " + json.model.hacker_iq);
		displayLanguages(json.model.languages);
		
		System.out.println();
	}

	private static void displayLanguages(List<List> languages) {
		int total = 0;
		for (List list : languages) {
			total += Integer.parseInt((String) list.get(1));
		}
		for (List list : languages) {
			System.out.println(list.get(0) + " - " + list.get(1) + "/" + total 
					+ " (" + percent(Integer.parseInt((String) list.get(1)), total) +"%)");
		}
		System.out.println("Total : " + total);
	}
	
	private static String percent(int num, int total) {
		return String.format("%.2f", ((float) num / total * 100));
	}
	
	private static final class Json {
		Model model;
	}
	
	private static final class Model {
		long id;
		String username;
		String country;
		String school;
		List<List> languages;
		String created_at;
		String level;
		String is_admin;
		String is_support_admin;
		String avatar;
		String website;
		String short_bio;
		String username_change_count;
		String name;
		String personal_first_name;
		String personal_last_name;
		String company;
		String local_language;
		String has_avatar_url;
		String hide_account_checklist;
		String spam_user;
		String job_title;
		double hacker_iq;
		String self;
		String title;
		String event_count;
		String online;
		String is_following;
		String is_followed;
		String followers_count;
	}
}
