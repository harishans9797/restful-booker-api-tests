package controllers;
import okhttp3.HttpUrl;

import java.util.*;

import static utils.Constants.HEADER_ACCEPT_JSON;
import static utils.Constants.TOKEN;

public class BookingController extends ControllerSetup {

    public String authenticate(String json) {
        HttpUrl url = restFulBookerUrl.newBuilder()
                .addPathSegment("auth")
                .build();
        return httpClientHelper.post(url, "", json, Collections.emptyMap(),false, 200);
    }

    public String getBookingById(Long id, int expectedCode) {
        HttpUrl url = restFulBookerUrl.newBuilder()
                .addPathSegment("booking")
                .addPathSegment(id.toString())
                .build();
        return httpClientHelper.get(url, TOKEN, HEADER_ACCEPT_JSON,expectedCode);
    }

    public String createBooking(String body) {
        HttpUrl url = restFulBookerUrl.newBuilder()
                .addPathSegment("booking")
                .build();
        return httpClientHelper.post(url, TOKEN, body, HEADER_ACCEPT_JSON,false, 200);
    }

    public String updateBooking(String body, Long id) {
        HttpUrl url = restFulBookerUrl.newBuilder()
                .addPathSegment("booking")
                .addPathSegment(id.toString())
                .build();
        return httpClientHelper.put(url, TOKEN, body, HEADER_ACCEPT_JSON, false, 200);
    }

    public String deleteBooking(Long id) {
        HttpUrl url = restFulBookerUrl.newBuilder()
                .addPathSegment("booking")
                .addPathSegment(id.toString())
                .build();
        return httpClientHelper.delete(url, TOKEN, HEADER_ACCEPT_JSON, 201);
    }

}
