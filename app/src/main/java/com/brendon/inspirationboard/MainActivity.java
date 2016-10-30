package com.brendon.inspirationboard;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    InspirationDatabase mDatabaseManager;
    DatabaseListAdapter mDatabaseListAdapter;

    Cursor mCursor;

    private TextView mListTitle;
    private Button mPictureButton;
    private Button mNoteButton;
    private ListView mFullList;

    private static final int NOTE_CODE = 1;
    private static final int PIC_CODE = 2;
    private static final int VIEWER_CODE = 3;

    private static final String NOTE_KEY = "note key";
    private static final String PHOTO_KEY = "photo key";

    public void updateList() {

        mCursor = mDatabaseManager.getAllData();
        mDatabaseListAdapter = new DatabaseListAdapter(this, mCursor, false);
        mFullList.setAdapter(mDatabaseListAdapter);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseManager = new InspirationDatabase(this);

        mListTitle = (TextView) findViewById(R.id.title_TV);
        mPictureButton = (Button) findViewById(R.id.picture_button);
        mNoteButton = (Button) findViewById(R.id.note_button);
        mFullList = (ListView) findViewById(R.id.full_list_view);


        mCursor = mDatabaseManager.getAllData();
        mDatabaseListAdapter = new DatabaseListAdapter(this, mCursor, false);
        mFullList.setAdapter(mDatabaseListAdapter);


        //updateList();


        // Starts the note activity
        mNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NoteActivity.class);

                startActivityForResult(intent, NOTE_CODE);


            }
        });

        // starts the picture activity
        mPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, PhotoActivity.class);

                startActivityForResult(intent, PIC_CODE);

            }
        });


        // User clicks to start viewr activity.
        mFullList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final TextView noteText = (TextView) view.findViewById(R.id.notes_text);
                final TextView hashText = (TextView) view.findViewById(R.id.photo_hash);

                String text = noteText.getText().toString();
                String hash = hashText.getText().toString();

                Intent intent = new Intent(MainActivity.this, FragmentActivityViewer.class);

                intent.putExtra(NOTE_KEY, text);

                startActivityForResult(intent, VIEWER_CODE);

            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // this will ensure that the list is updated every time the user goes to the main screen.

        if (resultCode == RESULT_OK) {

            mDatabaseManager = new InspirationDatabase(this);
            updateList();

        }



    }

    @Override
    protected void onPause() {
        super.onPause();

        mDatabaseManager.close();
    }






}
