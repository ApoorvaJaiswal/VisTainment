package com.vistar.flight.vistarainflightservices;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class chat extends ActionBarActivity {

    String name, num,pnr, mypnr, mynum, numkey;
    TextView textView;
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory = new ArrayList<ChatMessage>();
    private ArrayList<ChatMessage> uchatHistory = new ArrayList<ChatMessage>();
    private DatabaseReference myRef;
    public final String TAG = "abc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        name = getIntent().getExtras().getString("name");
        num = getIntent().getExtras().getString("num");
        pnr = getIntent().getExtras().getString("pnr");
        textView = (TextView) findViewById(R.id.friendLabel);
        textView.setText(name);
        load();
        numkey = "" + (Long.parseLong(num) + Long.parseLong(mynum));
        initControls();
        loadDummyHistory();
        update();
    }

    private void update() {
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                chatHistory.clear();
                String mesg = dataSnapshot.child("chat").child(numkey).child(pnr).getValue(String.class);
                if(!mesg.isEmpty()) {
                    ChatMessage msg = new ChatMessage();
                    msg.setId(1);
                    msg.setMe(false);
                    msg.setMessage(mesg);
                    msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                    chatHistory.add(msg);
                    ChatMessage message = chatHistory.get(chatHistory.size() - 1);
                    displayMessage(message);
                }
                myRef.child("chat").child(numkey).child(pnr).setValue("");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public void load() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        mynum = sharedPreferences.getString("Contact", mynum);
        mypnr = sharedPreferences.getString("pnr", mypnr);
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString().trim();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

                messageET.setText("");

                displayMessage(chatMessage);
                save(messageText,122);
                myRef.child("chat").child(numkey).child(mypnr).setValue(messageText);
            }
        });


    }

    private void save(String msg,int id) {

    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory() {



        /*ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        //msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        //msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);*/

        adapter = new ChatAdapter(chat.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        /*for (int i = 0; i < chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }*/

    }
}
