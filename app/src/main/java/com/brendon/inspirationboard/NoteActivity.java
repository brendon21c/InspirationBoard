package com.brendon.inspirationboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class NoteActivity extends AppCompatActivity {

    InspirationDatabase mInspirationDatabase;

    TextView mNoteTitle;
    EditText mNoteEntry;
    Button mNoteAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mInspirationDatabase = new InspirationDatabase(this);

        mNoteTitle = (TextView) findViewById(R.id.note_title);
        mNoteEntry = (EditText) findViewById(R.id.note_entry);
        mNoteAdd = (Button) findViewById(R.id.add_note_button);


        mNoteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = mNoteEntry.getText().toString();

                long time = Calendar.getInstance().getTimeInMillis() / 1000; // creates a timestamp

                mInspirationDatabase.addNewNote(text, time); // adds to database.

                setResult(RESULT_OK);
                finish();


            }
        });




    }
}
