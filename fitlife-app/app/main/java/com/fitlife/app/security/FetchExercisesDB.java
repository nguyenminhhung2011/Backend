package com.fitlife.app.Security;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class FetchExercisesDB {
    void fetch() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://exercisedb.p.rapidapi.com/exercises?limit=10")
                .get()
                .addHeader("X-RapidAPI-Key", "88e6e1a3cdmshde82ace8b4e5613p110cabjsn58d14ad01a23")
                .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body());
    }
}
