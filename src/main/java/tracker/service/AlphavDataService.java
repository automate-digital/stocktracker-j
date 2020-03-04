package tracker.service;

import org.json.JSONObject;
import tracker.domain.PricingService;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AlphavDataService implements PricingService {

    String AV_API_ENDPOINT = "https://www.alphavantage.co/";
    String API_KEY_ENVIRONMENT_VAR_NAME = "ALPHAVANTAGE_APIKEY";

    @Override
    public int getPrice(String ticker) throws Exception {
        String uri = AV_API_ENDPOINT + getPath(ticker);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return parsePrice(response.body());
    }

    private String getPath(String ticker) {
        return "query?"
                + "function=TIME_SERIES_DAILY&"
                + "symbol=" + ticker + "&"
                + "apikey=" + getApiKey();
    }

    private String getApiKey() {
        String apiKey = System.getenv(API_KEY_ENVIRONMENT_VAR_NAME);
        if (apiKey == null) {
            System.out.println("AlphaVantage key not found.\n" +
                    "Please set " + API_KEY_ENVIRONMENT_VAR_NAME + " environment variable.");
            System.exit(0);
        }
        return apiKey;
    }

    private int parsePrice(String apiResponse) {
        JSONObject json = new JSONObject(apiResponse);
        JSONObject metaData = json.getJSONObject("Meta Data");
        JSONObject priceHistory = json.getJSONObject("Time Series (Daily)");
        String lastRefreshDay = ((String) metaData.get("3. Last Refreshed")).split("\\s+")[0];
        String latestPriceStr = (String) priceHistory.getJSONObject(lastRefreshDay).get("4. close");

        double priceInPounds = Double.parseDouble(latestPriceStr);
        return (int) (100 * priceInPounds); // converting to pence
    }
}
