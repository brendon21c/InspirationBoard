package com.brendon.inspirationboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class PhotoActivity extends AppCompatActivity {

    InspirationDatabase mInspirationDatabase;
    PictureUtils mPictureUtils;

    private Context mContext;
    private File mImageFile;
    private String mImageFileName;
    private Uri mFileUri;
    private Bitmap mBitmap;

    TextView mPhotoHashTitle;
    EditText mPhotoHashEntry;
    ImageButton mPhotoButton;

    private static final int TAKE_PICTURE = 0;
    private static final int REQUEST_SAVE_IMAGE_PERMISSION_REQUEST_CODE = 123;

    private static final String TAG = "error";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        mInspirationDatabase = new InspirationDatabase(this);
        //mImageFile = new File(Environment.DIRECTORY_PICTURES, mImageFileName);


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

                    String imageFilename = "temp_file_name";

                    // The book uses this to handle permission issues.
                    File tempDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                    try {

                        mImageFile = File.createTempFile(imageFilename, ".jpg", tempDir);

                        mImageFileName = mImageFile.getAbsolutePath();

                        mFileUri = Uri.fromFile(mImageFile);

                    } catch (IOException ioe) {

                        Log.d(TAG, "error creating file", ioe);

                    }

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);

                    startActivityForResult(intent, TAKE_PICTURE);

                }



            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {

            BitmapFactory.Options bOptions = new BitmapFactory.Options();
            bOptions.inJustDecodeBounds = false;
            bOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;

            BitmapFactory.decodeFile(mImageFileName, bOptions);

            Bitmap bmp = BitmapFactory.decodeFile(mImageFileName, bOptions);

            mBitmap = bmp;

            byte[] compressed = getBytes(bmp);

            if (compressed != null) {

                String hashEntry = mPhotoHashEntry.getText().toString();

                long date = Calendar.getInstance().getTimeInMillis() / 1000;

                mInspirationDatabase.addNewPhoto(compressed, hashEntry, date);

                setResult(RESULT_OK);
                finish();

            }






        }
        //TODO I need to be able to get the photo as a Bitmap and store it in the database,
        //TODO I'm stuck on how to do this.
    }

    public static byte[] getBytes(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);

        return stream.toByteArray();

    }

    public static Bitmap getBitmap(String path) {

        BitmapFactory.Options bOptions = new BitmapFactory.Options();
        bOptions.inJustDecodeBounds = true;

        int picWidth = bOptions.outWidth;
        int picHeight = bOptions.outHeight;

        Bitmap bitmap =  BitmapFactory.decodeFile(path, bOptions);

        return bitmap;

    }


}
