package com.example.danielchoi.mirrorimage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by danielchoi on 2/24/18.
 * This program initializes the message content to the recycler view view group. If the bot is
 * messaging, this will create the message on the left side of the screen. When the user is
 * messaging, this adapter will create the message on the right side of the screen.
 */

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1; // determines which chat bubble to display
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private static final int HUMAN_SENDER = 1; // human code

    private Context mContext;
    private List<Message> mMessageList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    /**
     * This adds a new list to the existing list.
     *
     * @param addition : New list to add to the current one
     */
    public void addList(List<Message> addition) {
        for (int i =0; i < addition.size(); i++) {
            mMessageList.add(addition.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Message message = mMessageList.get(position);

        if (message.getSender() == HUMAN_SENDER) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    /**
     * This method inflates the appropriate layout according to the ViewType.
     * 0 if it is the bot (MESSAGE RECEIVED)
     * 1 if it is the person (MESSAGE SENT)
     *
     * @param parent : The parent viewgroup
     * @param viewType : 0 for bot. 1 for person
     * @return : Depending on the value, returns the message sent layout or message received layout
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    /**
     * This method passes the message object to a ViewHolder, so that contents can be bound to UI.
     * Similar as the method above
     * @param holder : Message sent or messaged received layout
     * @param position : Which message to pull out from the list
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    /**
     * This method inflates the user's chat
     */
    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getCreatedAt().toString());
        }
    }

    /**
     * This method inflates the bot's chat
     */
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        TextView nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getCreatedAt().toString());

            nameText.setText("Bot");
        }
    }
}