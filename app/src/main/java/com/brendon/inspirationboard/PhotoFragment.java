package com.brendon.inspirationboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;


public class PhotoFragment extends Fragment {

    private ImageView mFullImage;


    private InspirationDatabase mInspirationDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.photo_fragment, container, false);

        mFullImage = (ImageView) view.findViewById(R.id.full_image_view);

        mInspirationDatabase = new InspirationDatabase(getActivity());

        String photoKey = getArguments().getString("photo key");

        byte[] photoArray = mInspirationDatabase.getPhoto(photoKey);

        Bitmap photo = getRawImage(photoArray);

        mFullImage.setImageBitmap(photo);



        return view;

    }


    public static Bitmap getRawImage(byte[] bytes) {

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

    }


}
