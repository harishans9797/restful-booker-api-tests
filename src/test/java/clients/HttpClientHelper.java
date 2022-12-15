package clients;

import base.BaseRestFulBooker;
import okhttp3.*;
import org.testng.Assert;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpClientHelper extends BaseRestFulBooker {
    /**
     * Builds the client and sets basic timeout parameters.
    */
    public final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);

    private final OkHttpClient client = clientBuilder.build();

    /**
    * Makes GET request to the given [Url] using given [token]. [headers] and [expectedCode]
    */
    public String get(HttpUrl Url, String token, Map<String, String> headers, int expectedCode) {
        Request.Builder request = new Request.Builder().url(Url);
        if (token != null && !token.equals("")) {
            request.addHeader("Authorization", "Basic" + token);
        }

        if (!headers.isEmpty()) {
            for (var header : headers.entrySet()) {
                request.addHeader(header.getKey(), header.getValue());
            }
        }

        return executeCall(client, request.build(), expectedCode);
    }

    /**
     * Makes a POST request of the [body] to the given [Url] using given [token], and [expectedCode].
     */

    public String post(HttpUrl Url, String token, String json, Map<String, String> headers, Boolean isPlainText, int expectedCode) {
        MediaType mediaType = (!isPlainText) ? MediaType.parse("application/json; charset=utf-8")
                : MediaType.parse("text/plain; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, mediaType);
        Request.Builder request = new Request.Builder().url(Url);
        request.post(requestBody);


        if (token != null && !token.equals("")) {
            request.addHeader("Authorization", "Basic" + token);
        }

        if (!headers.isEmpty()) {
            for (var header : headers.entrySet()) {
                request.addHeader(header.getKey(), header.getValue());
            }
        }

        return executeCall(client, request.build(), expectedCode);
    }

    /**
     * Makes a PUT request of the [json] to the given [Url] using given [token], and [expectedCode].
     */
    public String put(HttpUrl Url, String token, String json, Map<String, String> headers, Boolean isPlainText, int expectedCode) {
        MediaType mediaType = (!isPlainText) ? MediaType.parse("application/json; charset=utf-8")
                : MediaType.parse("text/plain; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, mediaType);
        Request.Builder request = new Request.Builder().url(Url);
        request.put(requestBody);


        if (token != null && !token.equals("")) {
            request.addHeader("Authorization", "Bearer" + token);
        }

        if (!headers.isEmpty()) {
            for (var header : headers.entrySet()) {
                request.addHeader(header.getKey(), header.getValue());
            }
        }

        return executeCall(client, request.build(), expectedCode);
    }

    /**
     * Makes a DELETE request to the given [Url] using given [token], and [expectedCode].
     */
    public String delete(HttpUrl Url, String token, int expectedCode) {
        Request.Builder request = new Request.Builder().url(Url);
        if (token != null && !token.equals("")) {
            request.addHeader("Authorization", "Basic" + token);
        }

        return executeCall(client, request.build(), expectedCode);
    }

    /**
     * Executes the given [request] call using the [client] build in this class, and verifies the code matches the [expectedCode]
     */
    private String executeCall(OkHttpClient client, Request request, int expectedCode) {
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            Assert.assertEquals(response.code(), expectedCode);
            if (responseBody == null) {
                return "";
            } else {
                return responseBody.string();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public HttpUrl createRestFulBookerUrl() {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("restful-booker.herokuapp.com")
                .build();
    }
}
