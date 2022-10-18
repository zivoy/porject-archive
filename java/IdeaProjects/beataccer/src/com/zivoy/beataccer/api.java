package com.zivoy.beataccer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

/**
 * api abstract class for making the http requests easier
 * and for parsing json
 */
public abstract class api {
    private final OkHttpClient httpClient = new OkHttpClient();

    public String getString(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        // get response body
        return response.body().string();

    }

    public JSONObject getJson(String url) throws Exception {
        JSONParser jsonParser = new JSONParser();

        JSONObject obj = (JSONObject) jsonParser.parse(getString(url));

        // specified for the scoresaber api
        if (obj.containsKey("error"))
            throw new Exception();
        return obj;
    }
}
