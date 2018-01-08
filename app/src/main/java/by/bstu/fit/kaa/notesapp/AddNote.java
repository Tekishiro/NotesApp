package by.bstu.fit.kaa.notesapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import by.bstu.fit.kaa.notesapp.Helpers.DBHelper;
import by.bstu.fit.kaa.notesapp.Model.Note;

public class AddNote extends AppCompatActivity {

    Integer[] data = {1, 2, 3, 4, 5};
    AsyncAdd addToDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerImpLv);
        spinner.setAdapter(adapter);
        spinner.setPrompt("ImpLv");
        spinner.setSelection(1);
    }

    public void onAddClick(View view){
        EditText noteTitle = (EditText) findViewById(R.id.noteTitleInput);
        EditText noteText = (EditText) findViewById(R.id.noteTextInput);
        Spinner noteImp = (Spinner) findViewById(R.id.spinnerImpLv);

        String newNoteTitle = noteTitle.getText().toString();
        String newNoteText = noteText.getText().toString();
        int newNoteImportance = Integer.parseInt(noteImp.getSelectedItem() + "");

        addToDB = new AsyncAdd();
        addToDB.execute(new Note(newNoteTitle, newNoteImportance, newNoteText));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onCancelClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }





    class AsyncAdd extends AsyncTask<Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... params){
            try{
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                for(Note note : params){
                    ContentValues cv = new ContentValues();

                    cv.put(DBHelper.TITLE, note.getTitle());
                    cv.put(DBHelper.IMPORTANCE, note.getImportance());
                    cv.put(DBHelper.TEXT, note.getText());

                    long rowID = db.insert(DBHelper.TABLE_NAME, null, cv);
                }
                dbHelper.close();
            }
            catch (Exception exc){
                Toast.makeText(getApplicationContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }
}
