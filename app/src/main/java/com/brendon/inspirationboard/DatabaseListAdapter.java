package com.brendon.inspirationboard;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class DatabaseListAdapter extends CursorAdapter {


    Context context;

    private static final int PICTURE_COL = 1;
    private static final int HASTAG_COL = 2;
    private static final int NOTES_COL = 3;
    private static final int DATE_COL = 4;


    private byte[] byteArrayTemp;
    private Bitmap photo;

    private String picHash = "";

    private String noteShort = "";




    public DatabaseListAdapter(Context context, Cursor cursor, boolean autoRequery) {

        super(context,cursor,autoRequery);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_view_layout, parent, false);

        return view;
    }


    // Puts data into the listview.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView photoHashTV = (TextView) view.findViewById(R.id.photo_hash);
        TextView noteField = (TextView) view.findViewById(R.id.notes_text);
        ImageView imageThumbnail = (ImageView) view.findViewById(R.id.photo_image);


        byteArrayTemp = cursor.getBlob(PICTURE_COL);



        if (byteArrayTemp != null) {


            photo = getRawImage(byteArrayTemp);
            picHash = cursor.getString(HASTAG_COL);

            photoHashTV.setText(cursor.getString(HASTAG_COL));
            imageThumbnail.setImageBitmap(photo);

            photo = null;
            picHash = "";


        }


        noteShort = cursor.getString(NOTES_COL);


        if (noteShort != null) {


            if (!noteShort.isEmpty()) {


                if (noteShort.length() > 100) {

                    noteShort = noteShort.substring(0, 99);
                    noteField.setText(noteShort);


                } else {


                    noteField.setText(cursor.getString(NOTES_COL));

                }


                noteShort = "";


            }
        }




    }


    public static Bitmap getRawImage(byte[] bytes) {

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

    }

}

