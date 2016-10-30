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

       mNoteFragment = bundle.getString(NOTE_KEY);
       mPhotoFragment = bundle.getString(PHOTO_KEY);


        if (mPhotoFragment != null) {

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.activity_fragment_viewer);

            if (fragment == null) {

                Bundle bundleNote = new Bundle();
                bundleNote.putString(PHOTO_KEY, mPhotoFragment);
                fragment = new PhotoFragment();
                fragment.setArguments(bundleNote);

                fm.beginTransaction()
                        .add(R.id.activity_fragment_viewer, fragment)
                        .addToBackStack(null)
                        .commit();

            }

            // Sets the result so the List can be updated on the Main screen when the fragments close.
            fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {

                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if (backStackCount == 0) {

                        setResult(RESULT_OK);
                        finish();
                    }


                }
            });


        }



        // Launches the note fragment if the user selected a note item.
        if (mNoteFragment != null) {

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.activity_fragment_viewer);

            if (fragment == null) {

                Bundle bundleNote = new Bundle();
                bundleNote.putString(NOTE_KEY, mNoteFragment);
                fragment = new NoteFragment();
                fragment.setArguments(bundleNote);

                fm.beginTransaction()
                        .add(R.id.activity_fragment_viewer, fragment)
                        .addToBackStack(null)
                        .commit();

            }

            // Sets the result so the List can be updated on the Main screen when the fragments close.
            fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {

                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if (backStackCount == 0) {

                        setResult(RESULT_OK);
                        finish();
                    }


                }
            });


        }



    }



    @Override
    protected void onResume() {
        super.onResume();


    }


}
