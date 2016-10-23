package com.brendon.inspirationboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    private static final int NOTE_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInspirationDatabase = new InspirationDatabase(this);

        mListTitle = (TextView) findViewById(R.id.title_TV);
        mPictureButton = (Button) findViewById(R.id.picture_button);
        mNoteButton = (Button) findViewById(R.id.note_button);
        mFullList = (ListView) findViewById(R.id.full_list_view);


        mNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NoteActivity.class);

                startActivityForResult(intent, NOTE_CODE);



            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
