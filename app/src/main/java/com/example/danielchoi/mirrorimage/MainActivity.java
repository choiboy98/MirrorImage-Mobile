package com.example.danielchoi.mirrorimage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by danielchoi on 2/24/18.
 * This program starts the application and prompts the user to click anywhere on the screen to
 * proceed.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button chatButton = (Button) findViewById(R.id.button);
        final Intent intent = new Intent(this, MessageListActivity.class);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
}

