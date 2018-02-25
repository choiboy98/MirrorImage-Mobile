package com.example.danielchoi.mirrorimage;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.GzipSource;
import okio.Okio;

import static java.sql.Types.NULL;

/**
 * Created by danielchoi on 2/24/18.
 */

public class MessagelistAsyncTask extends AsyncTask<String, String, String> {

    private MessageListAdapter messageListAdapter;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public List<Message> messages;

    public MessagelistAsyncTask(MessageListAdapter messageListAdapter) {
        this.messageListAdapter = messageListAdapter;
        messages = new ArrayList<>();
    }


    @Override
    protected String doInBackground(String... strings) {
        try {
            System.out.println("User input: " + strings[0]);

            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            String json = "{\"message\":\"" + strings[0] + "\"}";

            Request request = new Request.Builder()
                    .url("https://mirror-image.herokuapp.com")
                    .post(RequestBody.create(JSON, json)).build();

            System.out.println("Request body:" + request.body().toString());
            try {
                Response response = okHttpClient.newCall(request).execute();
                return decodeBody(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String output) {
        if (output == null || output.equals("")) {
            return;
        }
        Message robot = new Message(0, output);
        messages.add(robot);
        messageListAdapter.addList(messages);
        messageListAdapter.notifyDataSetChanged();
        System.out.println("Response message: " + output);
    }



    private String decodeBody(Response response) throws IOException {
        final ResponseBody body = response.body();
        if (body == null) return null;

        if (isZipped(response)) {
            return unzip(response.body());
        } else {
            return body.string();
        }
    }

    private boolean isZipped(Response response) {
        return "gzip".equalsIgnoreCase(response.header("Content-Encoding"));
    }

    private String unzip(ResponseBody body) {
        try {
            GzipSource responseBody = new GzipSource(body.source());
            return Okio.buffer(responseBody).readUtf8();
        } catch (IOException e) {
            return null;
        }
    }
}
