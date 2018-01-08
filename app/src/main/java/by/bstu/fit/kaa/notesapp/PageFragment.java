package by.bstu.fit.kaa.notesapp;

import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.bstu.fit.kaa.notesapp.Model.Note;

/**
 * Created by Shiro on 08.01.2018.
 */

public class PageFragment extends Fragment {

    Note currentNote;
    int backColor;

    @SuppressLint("ValidFragment")
    public PageFragment(Note newNote){
        currentNote = newNote;
    }

    public PageFragment(){
        currentNote = new Note();
    }

    public static PageFragment newInstance(Note newNote){
        PageFragment pageFragment = new PageFragment(newNote);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_fragment, null);

        TextView tvPage = (TextView) view.findViewById(R.id.tvPage);
        tvPage.setText(currentNote.getText());
        tvPage.setBackgroundColor(backColor);

        TextView tvImportance = (TextView) view.findViewById(R.id.tvImportance);
        tvImportance.setText("Importance level:  " + currentNote.getImportance());
        tvImportance.setBackgroundColor(backColor);

        return view;
    }

}
