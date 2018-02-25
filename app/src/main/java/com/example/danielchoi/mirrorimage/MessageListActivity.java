package com.example.danielchoi.mirrorimage;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class MessageListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageListAdapter messageListAdapter;
    public static List<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        final EditText edit =  (EditText) findViewById(R.id.edittext_chatbox);
        final Button sendButton = (Button) findViewById(R.id.button_chatbox_send);
        messages = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        messageListAdapter = new MessageListAdapter(this, messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageListAdapter);

        //client = new OkHttpClient();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userText = edit.getText().toString();
                System.out.println(userText);
                Message userMessage = new Message(1, userText);
                edit.setText("");
                messages.add(userMessage);
                messageListAdapter.notifyDataSetChanged();
                final MessagelistAsyncTask messagelistAsyncTask = new MessagelistAsyncTask(messageListAdapter);

                messagelistAsyncTask.execute(userText);
            }
        });
    }

}
