package controllers;

import base.BaseRestFulBooker;
import com.fasterxml.jackson.databind.ObjectMapper;
import clients.HttpClientHelper;
import okhttp3.HttpUrl;
import utils.JacksonUtils;

public class ControllerSetup extends BaseRestFulBooker {

    protected HttpClientHelper httpClientHelper = new HttpClientHelper();
    protected JacksonUtils jacksonUtils = new JacksonUtils();
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected HttpUrl restFulBookerUrl = new HttpClientHelper().createRestFulBookerUrl();
}
