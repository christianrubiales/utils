package hackerrank.cli;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;

/**
 * Displays the scores of a user per domain.
 */
public class Ratings {

	private static final Gson gson = new GsonBuilder().create();
	private static final String PREFIX = "https://www.hackerrank.com/rest/hackers/";
	private static final String SUFFIX = "/scores";
//	private static final String SUFFIX = "/scores2";
	private static final DecimalFormat format = new DecimalFormat("#,###");
	
	public static void main(String[] args) throws JsonSyntaxException, IOException {
		for (String username : args) {
			display(username);
		}
	}

	private static void display(String username) throws MalformedURLException, IOException {
		System.out.println(username);
		URL url = new URL(PREFIX + username + SUFFIX);
		List list = gson.fromJson(IOUtils.toString(url), List.class);
		for (Object o : list) {
			LinkedTreeMap treemap = (LinkedTreeMap) o;
			for (Object key : treemap.keySet()) {
				if ("name".equals(key)) {
					System.out.print(treemap.get(key) + " - ");
				} else if ("practice".equals(key)) {
					double rank = (double) ((LinkedTreeMap) treemap.get("practice")).get("rank");
					System.out.print("score : " + ((LinkedTreeMap) treemap.get("practice")).get("score"));
					System.out.println(", rank : " + format.format(rank));
				}
			}
		}
		System.out.println();
	}

}
