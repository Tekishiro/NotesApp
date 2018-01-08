package by.bstu.fit.kaa.notesapp.Adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import by.bstu.fit.kaa.notesapp.Model.Note;
import by.bstu.fit.kaa.notesapp.PageFragment;

/**
 * Created by Shiro on 08.01.2018.
 */

public class NotesPageAdapter extends FragmentPagerAdapter {

    ArrayList<Note> notes;

    public NotesPageAdapter(FragmentManager fm, ArrayList<Note> objects){
        super(fm);
        notes = objects;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(notes.get(position));
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return notes.get(position).getTitle();
    }
}
