package com.brendon.inspirationboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;

public class PhotoActivity extends AppCompatActivity {

    InspirationDatabase mInspirationDatabase;

    private Context mContext;
    private File mImageFile;
    private Uri mFileUri;

    TextView mPhotoHashTitle;
    EditText mPhotoHashEntry;
    ImageButton mPhotoButton;

    private static final int TAKE_PICTURE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        mInspirationDatabase = new InspirationDatabase(this);

        mPhotoHashTitle = (TextView) findViewById(R.id.hashtag_title);
        mPhotoHashEntry = (EditText) findViewById(R.id.hastag_entry);
        mPhotoButton = (ImageButton) findViewById(R.id.take_picture_button);

        // Takes the picture.
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // checks if your phone has a camera app.
                if (intent.resolveActivity(getPackageManager()) != null) {

                    // The book uses this to handle permission issues.
                    mImageFile = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                    mFileUri = Uri.fromFile(mImageFile);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);

                    startActivityForResult(intent, TAKE_PICTURE);

                }



            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE) {


        }
        //TODO I need to be able to get the photo as a Bitmap and store it in the database,
        //TODO I'm stuck on how to do this.
    }


}
