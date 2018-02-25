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

/**
 * Created by danielchoi on 2/24/18.
 * This Async task runs the internet side in the background. It uses OKHttp to contact the server
 * and retrieve information.
 */

public class MessagelistAsyncTask extends AsyncTask<String, String, String> {

    private MessageListAdapter messageListAdapter;

    //MediaType to be used for the OKHttp client
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public List<Message> messages;

    public MessagelistAsyncTask(MessageListAdapter messageListAdapter) {
        this.messageListAdapter = messageListAdapter;
        messages = new ArrayList<>();
    }


    /**
     * This method calls the okHttpClient in the background and does a POST Request. It sends the
     * user's typed message as an input and retrieves the bot's reply as an output.
     *
     * @param strings : The user's input
     * @return : The bot's encoded response
     */
    @Override
    protected String doInBackground(String... strings) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            String json = "{\"message\":\"" + strings[0] + "\"}";

            Request request = new Request.Builder()
                    .url("https://mirror-image.herokuapp.com")
                    .post(RequestBody.create(JSON, json)).build();

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

    /**
     * This method adds the robot's message to the List and to the Adapter.
     *
     * @param output : Decoded version of the robot's message
     */
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


    // retrieved from: https://gist.github.com/vovkab/b9c8ffa86d97e685d3cb
    // Decodes the responses from OKHttp client

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
