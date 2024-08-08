// HTTP Request and Handling
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// Parsing and Handling JSON Data
import org.json.JSONArray;
import org.json.JSONObject;

// Handling IO Exceptions
import java.io.IOException;

// Encoding URLs and Handling Character Encoders
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

// UserInput
import java.util.Scanner;

// List
import java.util.List;

// HTML Parsing
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ApiRequest {
    private static final String ACCESS_TOKEN = "ASK_ME_FOR_TOKEN"; // Shhhhh secret code ASK ME ITS IN CONFLUX
    private static final String BASE_URL = "https://api.genius.com"; // Base url for Genius API

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter song name: ");
        String songName = scanner.nextLine();

        try {
            String songId = searchSong(songName);
            if (songId != null) {
                printSongLyrics(songId);
            } else {
                System.out.println("Song not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String searchSong(String songName) throws IOException {
        OkHttpClient client = new OkHttpClient(); // Begin communication

        String encodedQuery = URLEncoder.encode(songName, StandardCharsets.UTF_8); // Makes sure it's safe for URL
        String url = BASE_URL + "/search?q=" + encodedQuery; // Append onto url for request

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            System.out.println("Response JSON: " + responseBody);  // Print full response

            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONObject responseObject = jsonResponse.getJSONObject("response");
            JSONArray hits = responseObject.getJSONArray("hits");

            if (hits.length() > 0) {
                JSONObject firstHit = hits.getJSONObject(0).getJSONObject("result");
                Object idObject = firstHit.get("id"); // Use Object to handle different data types

                String songId;
                if (idObject instanceof Integer) {
                    songId = String.valueOf(idObject);
                } else if (idObject instanceof String) {
                    songId = (String) idObject;
                } else {
                    throw new IOException("Unexpected ID type: " + idObject.getClass().getName());
                }

                System.out.println("Found song: " + firstHit.getString("full_title"));
                return songId;
            }
        }
        return null;
    }

    private static void printSongLyrics(String songId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = BASE_URL + "/songs/" + songId;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            JSONObject jsonResponse = new JSONObject(response.body().string());
            JSONObject songData = jsonResponse.getJSONObject("response").getJSONObject("song");

            // Get the lyrics path
            String lyricsPath = songData.getString("path");
            String lyricsUrl = "https://genius.com" + lyricsPath;
            System.out.println("Lyrics URL: " + lyricsUrl);

            // Fetch the lyrics from the page
            String lyrics = fetchLyricsFromPage(lyricsUrl);
            System.out.println("Lyrics: \n" + lyrics);
        }
    }

    private static String fetchLyricsFromPage(String pageUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(pageUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String htmlResponse = response.body().string();
            return extractLyricsFromHtml(htmlResponse);
        }
    }

    private static String extractLyricsFromHtml(String html) {
        Document doc = Jsoup.parse(html);
        Elements lyricsContainers = doc.select("div.Lyrics__Container-sc-1ynbvzw-1"); // found from scraping this <div> contains the lyrics

        if (lyricsContainers != null && !lyricsContainers.isEmpty()) {
            StringBuilder lyrics = new StringBuilder();
            for (Element container : lyricsContainers) {
                extractTextFromBrSeparatedElements(container, lyrics);
            }
            return lyrics.toString().trim();
        }

        return "Lyrics not found.";
    }

    private static void extractTextFromBrSeparatedElements(Element element, StringBuilder lyrics) {
        List<org.jsoup.nodes.Node> nodes = element.childNodes();  // Get all types of nodes

        for (org.jsoup.nodes.Node node : nodes) {
            if (node.nodeName().equals("#text")) {
                String text = node.toString().trim();
                if (!text.isEmpty()) {
                    lyrics.append(Jsoup.parse(text).text());  // Decode HTML entities
                }
            } else if (node.nodeName().equals("br")) {
                lyrics.append("\n");  // Add newline to separate lines
            } else if (node instanceof Element) {
                Element childElement = (Element) node;
                if (childElement.tagName().equals("a")) {
                    // Process <a> elements that might contain additional text
                    extractTextFromBrSeparatedElements(childElement, lyrics);
                } else {
                    // Process other elements
                    extractTextFromBrSeparatedElements(childElement, lyrics);
                }
            }
        }
    }
}
