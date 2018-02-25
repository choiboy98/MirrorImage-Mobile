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

/**
 * This program runs the majority of the app. It has a RecyclerView and a MessageListAdapter that
 * inflates the screen with a Linear Layout. This program will also call the Async task that will
 * deal with the internet and calling the chatbot.
 */
public class MessageListActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // recycler view for the chat
    private MessageListAdapter messageListAdapter; // used to inflate the screen
    public static List<Message> messages; // different messages from both the user and the bot

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        // widgets in the current screen
        final EditText edit =  (EditText) findViewById(R.id.edittext_chatbox);
        final Button sendButton = (Button) findViewById(R.id.button_chatbox_send);
        messages = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        messageListAdapter = new MessageListAdapter(this, messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageListAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userText = edit.getText().toString();
                Message userMessage = new Message(1, userText);

                edit.setText("");
                messages.add(userMessage);
                messageListAdapter.notifyDataSetChanged();

                // The Async task must be called every time the button is clicked
                final MessagelistAsyncTask messagelistAsyncTask = new MessagelistAsyncTask(messageListAdapter);

                messagelistAsyncTask.execute(userText);
            }
        });
    }

}
