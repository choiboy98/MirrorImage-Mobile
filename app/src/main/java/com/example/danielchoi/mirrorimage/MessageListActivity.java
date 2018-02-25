package com.example.danielchoi.mirrorimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

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
