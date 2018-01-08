package by.bstu.fit.kaa.notesapp.Model;

import java.io.Serializable;

/**
 * Created by Shiro on 08.01.2018.
 */

public class Note implements Serializable {
    String title;
    int importance;
    String text;

    public Note(String _title, int _importance, String _text){
        title = _title;
        importance = _importance;
        text = _text;
    }

    public Note(){
        title = "Dummy title";
        importance = 1;
        text = "Dummy text";
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String _title){
        title = _title;
    }

    public int getImportance(){
        return importance;
    }

    public void setImportance(int _importance){
        importance = _importance;
    }

    public String getText(){
        return text;
    }

    public void setText(String _text){
        text = _text;
    }
}
