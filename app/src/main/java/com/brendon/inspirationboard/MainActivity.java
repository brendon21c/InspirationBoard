package com.brendon.inspirationboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.security.Timestamp;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    InspirationDatabase mInspirationDatabase;

    private TextView mListTitle;
    private Button mPictureButton;
    private Button mNoteButton;
    private ListView mFullList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInspirationDatabase = new InspirationDatabase(this);

        mListTitle = (TextView) findViewById(R.id.title_TV);
        mPictureButton = (Button) findViewById(R.id.picture_button);
        mNoteButton = (Button) findViewById(R.id.note_button);
        mFullList = (ListView) findViewById(R.id.full_list_view);







    }
}
