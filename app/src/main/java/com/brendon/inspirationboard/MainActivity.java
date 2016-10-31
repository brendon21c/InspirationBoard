package com.brendon.inspirationboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    InspirationDatabase mDatabaseManager;
    DatabaseListAdapter mDatabaseListAdapter;

    Cursor mCursor;

    Context mContext;

    private TextView mListTitle;
    private ImageButton mPictureButton;
    private ImageButton mNoteButton;
    private ImageButton mSearchButton;
    private EditText mSearchField;
    private ListView mFullList;

    private static final int NOTE_CODE = 1;
    private static final int PIC_CODE = 2;
    private static final int VIEWER_CODE = 3; // Code for the fragment viewer

    private static final String NOTE_KEY = "note key";
    private static final String PHOTO_KEY = "photo key";

    public void updateList() {

        mCursor = mDatabaseManager.getAllData();
        mDatabaseListAdapter = new DatabaseListAdapter(this, mCursor, false);
        mFullList.setAdapter(mDatabaseListAdapter);


    }


    public void updateListSearch(String search) {

        mCursor = mDatabaseManager.getSearchData(search);
        mDatabaseListAdapter = new DatabaseListAdapter(this, mCursor, false);
        mFullList.setAdapter(mDatabaseListAdapter);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseManager = new InspirationDatabase(this);


        mListTitle = (TextView) findViewById(R.id.title_TV);
        mPictureButton = (ImageButton) findViewById(R.id.camera_button);
        mNoteButton = (ImageButton) findViewById(R.id.note_button);
        mSearchButton = (ImageButton) findViewById(R.id.find_results_button);
        mFullList = (ListView) findViewById(R.id.full_list_view);
        mSearchField = (EditText) findViewById(R.id.search_field_entry);


        mCursor = mDatabaseManager.getAllData();
        mDatabaseListAdapter = new DatabaseListAdapter(this, mCursor, false);
        mFullList.setAdapter(mDatabaseListAdapter);


        //updateList();


        // Runs the search query.
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchEntry = mSearchField.getText().toString();
                updateListSearch(searchEntry);
                mSearchField.setText("");

            }
        });

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

                if (text != "") {


                    Intent intent = new Intent(MainActivity.this, FragmentActivityViewer.class);

                    intent.putExtra(NOTE_KEY, text);

                    startActivityForResult(intent, VIEWER_CODE);

                } else {

                    Intent intent = new Intent(MainActivity.this, FragmentActivityViewer.class);

                    intent.putExtra(PHOTO_KEY, hash);

                    startActivityForResult(intent, VIEWER_CODE);

                }




            }
        });

        // Deletes the records from the database.
        mFullList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                final TextView noteText = (TextView) view.findViewById(R.id.notes_text);
                final TextView hashText = (TextView) view.findViewById(R.id.photo_hash);

                String text = noteText.getText().toString();
                String hash = hashText.getText().toString();


                if (text != "") {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Delete this entry?");
                   // builder.setCancelable(false);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            String text = noteText.getText().toString();
                            mDatabaseManager.deletNoteEntry(text);
                            updateList();
                            //dialog.dismiss();

                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            dialog.dismiss();

                        }
                    });


                    AlertDialog builder1 = builder.create();
                    builder1.show();
                    return true;


                } else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Delete this entry?");
                    builder.setCancelable(false);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            String hash = hashText.getText().toString();
                            mDatabaseManager.deletePhoto(hash);
                            updateList();
                            //dialog.dismiss();

                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            dialog.cancel();



                        }
                    });

                    AlertDialog builder1 = builder.create();
                    builder1.show();
                    return true;


                }


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


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        mDatabaseManager = new InspirationDatabase(this);
        updateList();
    }
}
