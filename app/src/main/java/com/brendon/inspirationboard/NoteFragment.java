package com.brendon.inspirationboard;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NoteFragment extends Fragment {


    TextView mDateTitle;
    TextView mDate;
    EditText mNoteDisplay;
    Button mUpdateButton;

    //private Bundle mUpdateButton;

    private String mDateFormat;

    private long mDateLong;



    InspirationDatabase mDatabase;



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note, container, false);


        mDateTitle = (TextView) view.findViewById(R.id.date_title);
        mDate = (TextView) view.findViewById(R.id.date_display);
        mNoteDisplay = (EditText) view.findViewById(R.id.note_display);
        mUpdateButton = (Button) view.findViewById(R.id.update_note_button);


        mDatabase = new InspirationDatabase(getActivity());

        String noteKey = getArguments().getString("note key");


        List noteList = mDatabase.getNote(noteKey);

        String fullNote = noteList.get(0).toString();

        mDateLong = (long) noteList.get(1);

        mDateFormat = getDateFormat(mDateLong);

        mDate.setText(mDateFormat);

        mNoteDisplay.setText(fullNote);


        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newNote = mNoteDisplay.getText().toString();

                mDatabase.updateNote(newNote, mDateLong);
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });



        return view;


    }


    private String getDateFormat(long timeStamp) {

        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(timeStamp * 1000);


        return dateString;



    }
}
