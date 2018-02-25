package com.example.danielchoi.mirrorimage;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by danielchoi on 2/24/18.
 * This program has the sender's information, the message, and the time the sender sent the message.
 */

public class Message {

    private int sender; // 0 if the sender is a bot; 1 if the sender is a person.
    private String message; // the actual message
    private Date time; // Timestamp of when the messages were sent

    public Message(int sender, String message) {
        this.sender = sender;
        this.message = message;
        time = Calendar.getInstance().getTime();

    }

    public int getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Date getCreatedAt() {
        return time;
    }
}
