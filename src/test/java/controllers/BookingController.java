package controllers;
import okhttp3.HttpUrl;

import java.util.*;

public class BookingController extends ControllerSetup {

    public String authenticate(String json) {
        HttpUrl url = restFulBookerUrl.newBuilder()
                .addPathSegment("auth")
                .build();
        return httpClientHelper.post(url, "", json, Collections.emptyMap(), false, 200);
    }

    public String getBookings(String token) {
        HttpUrl url = restFulBookerUrl.newBuilder()
                .addPathSegment("booking")
                .build();
        return httpClientHelper.get(url, token, Collections.emptyMap(), 200);
    }

    public String getBookingById(String token, Long id) {
        HttpUrl url = restFulBookerUrl.newBuilder()
                .addPathSegment("booking")
                .addPathSegment(id.toString())
                .build();
        Map<String, String> headers = new HashMap<>() {
            {
                put("Accept", "application/json");
            }
        };
        return httpClientHelper.get(url, token, headers, 200);
    }

    public String createBooking(String token, String body) {
        HttpUrl url = restFulBookerUrl.newBuilder()
                .addPathSegment("booking")
                .build();
        Map<String, String> headers = new HashMap<>() {
            {
                put("Accept", "application/json");
            }
        };
        return httpClientHelper.post(url, token, body, headers, false, 200);
    }

    public String updateBooking(String token, String body, Long id) {
        HttpUrl url = restFulBookerUrl.newBuilder()
                .addPathSegment("booking")
                .addPathSegment(id.toString())
                .build();
        return httpClientHelper.put(url, token, body, Collections.emptyMap(), false, 203);
    }

    public String deleteBooking(String token, Long id) {
        HttpUrl url = restFulBookerUrl.newBuilder()
                .addPathSegment("booking")
                .addPathSegment(id.toString())
                .build();
        return httpClientHelper.delete(url, token, 203);
    }

}
