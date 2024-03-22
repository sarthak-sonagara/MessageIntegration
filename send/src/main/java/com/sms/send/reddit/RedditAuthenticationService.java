package com.sms.send.reddit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RedditAuthenticationService {
    private final String STATE = "checkIt";
    private final String CLIENT_ID = "Iz4Wzi_wrTN-7u78BuO0Nw";
    private final String CLIENT_SECRET = "Rbn1YYCLRGzbkEXLRhj6E187cTsWqw";
    private final String REDIRECT_URI = "http://localhost:8081/Gradle___com_sms_send___send_1_0_SNAPSHOT_war__exploded_/reddit/callback";
    public String getUrl(){
        String RESPONSE_TYPE = "code";
        Duration DURATION = Duration.temporary;
        List<Scope> SCOPE_LIST = List.of(Scope.read, Scope.identity);
        return "https://www.reddit.com/api/v1/authorize" +
                "?client_id=" + CLIENT_ID +
                "&response_type=" + RESPONSE_TYPE +
                "&state=" + STATE +
                "&redirect_uri=" + REDIRECT_URI +
                "&duration=" + DURATION +
                "&scope=" + SCOPE_LIST.stream().map(Enum::name).collect(Collectors.joining(","));
    }

    public boolean verifyState(String state){
        return this.STATE.equals(state);
    }

    public String getAccessToken(String code) throws IOException, InterruptedException, StatusCodeException {
        String redditAuthenticationUrl = "https://www.reddit.com/api/v1/access_token";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(redditAuthenticationUrl))
                .header("Authorization",getAuthenticationHeader())
                .POST(HttpRequest.BodyPublishers.ofString(getRequestBody(code)))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        if(response.statusCode()!=200){
            throw new StatusCodeException();
        }
        return (String) new ObjectMapper().readValue(response.body(), Map.class).get("access_token");
    }

    private String getRequestBody(String code) {
        return ("grant_type=authorization_code") + "&" + ("code="+code) + "&" + ("redirect_uri="+REDIRECT_URI);
    }

    private String getAuthenticationHeader() {
        String authString = CLIENT_ID + ":" + CLIENT_SECRET;
        return "Basic " + Base64.getEncoder().encodeToString(authString.getBytes());
    }

    public String getMyDetails(String accessToken) throws IOException, InterruptedException, StatusCodeException {
        String apiUrl = "https://oauth.reddit.com/api/v1/me";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + accessToken)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        if (statusCode == 200) {
            // Request successful, return response body
            return response.body();
        } else {
            // Request failed, handle error
            throw new StatusCodeException();
        }
    }
}
