package com.brendon.inspirationboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FragmentActivityViewer extends FragmentActivity {


    private String mPhotoFragment = "photo";
    private String mNoteFragment = "note";

    private String NOTE_KEY = "note key";
    private String PHOTO_KEY = "photo key";


    private InspirationDatabase mInspirationDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_viewer);

        Bundle bundle = getIntent().getExtras();

        String check = bundle.getString(NOTE_KEY);


        // Launches the note fragment if the user selected a note item.
        if (check != "") {

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.activity_fragment_viewer);

            if (fragment == null) {

                Bundle bundleNote = new Bundle();
                bundleNote.putString(NOTE_KEY, check);
                fragment = new NoteFragment();
                fragment.setArguments(bundleNote);

                fm.beginTransaction()
                        .add(R.id.activity_fragment_viewer, fragment)
                        .addToBackStack(null)
                        .commit();

            }


        }



    }
}
