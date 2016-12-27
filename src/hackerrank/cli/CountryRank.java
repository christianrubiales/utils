package hackerrank.cli;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Returns the ranking of the given usernames in the given country 
 * for all applicable domains.
 * Limited to top 20 per domain only.
 */
public class CountryRank {

	private static final Gson gson = new GsonBuilder().create();
	
	private static final String PART1 = "https://www.hackerrank.com/rest/contests/master/tracks/";
	private static final String PART2 = "/leaderboard/filter?offset=0&limit=20&country=";
	private static final String PART3 = "&type=practice&level=1&version3=false";
	
	private static final String[] DOMAINS = new String[]{"algorithms","data-structures","mathematics","ai",
			"cpp","java","python","ruby","sql","databases","distributed-systems","shell","fp","regex",
			"security","tutorials"};
	
	private static final Map<String, String> domainToStringResponse = new HashMap<>();
	private static final Map<String, Json> domainToJson = new HashMap<>();

	/**
	 * @param args first argument is the name of country (ex: Philippines)
	 * succeeding arguments are usernames
	 */
	public static void main(String[] args) throws JsonSyntaxException, IOException {
		System.out.println(args[0]);
		System.out.println();
		
		// pre-populate
		for (String domain : DOMAINS) {
			URL url = new URL(PART1 + domain + PART2 + args[0] + PART3);
			String string = IOUtils.toString(url);
			domainToStringResponse.put(domain, string);
			
			Json json = gson.fromJson(string, Json.class);
			domainToJson.put(domain, json);
		}
		
		// get ranks
		for (int i = 1; i < args.length; i++) {
			String username = args[i];
			System.out.println(username);

			for (String domain : DOMAINS) {
				if (domainToStringResponse.get(domain).contains(username)) {
					Json json = domainToJson.get(domain);
					
					for (Model model : json.models) {
						if (model.hacker.equals(username)) {
							System.out.println(domain + " : " + model.rank);
							break;
						}
					}
				}
			}
			System.out.println();
		}
	}
	
	private static final class Json {
		List<Model> models;
		int total;
		int page;
		boolean available;
		boolean self_display;
	}
	
	private static final class Model {
		String hacker_id;
		double score;
		int rank;
		String avatar;
		String hacker;
		String country;
		String school;
		String timestamp;
		String submitted_at;
	}
}
