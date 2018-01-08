package by.bstu.fit.kaa.notesapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import by.bstu.fit.kaa.notesapp.Adapters.NotesPageAdapter;
import by.bstu.fit.kaa.notesapp.Helpers.DBHelper;
import by.bstu.fit.kaa.notesapp.Model.Note;

public class MainActivity extends AppCompatActivity {

    ArrayList<Note> notes = new ArrayList<>();
    ArrayList<Integer> noteIDs = new ArrayList<>();
    NotesPageAdapter pagerAdapter;
    int currentPosition = 0;
    String sortingField = "_id";

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sortingField = prefs.getString("sortingField", "_id");

        getDataSet();

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new NotesPageAdapter(getSupportFragmentManager(), notes);
        pager.setAdapter(pagerAdapter);
        pager.setSaveFromParentEnabled(false);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        switch(id){
            case R.id.action_sortDate:
                prefs.edit().putString("sortingField", "_id").apply();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                return true;
            case R.id.action_sortImportance:
                prefs.edit().putString("sortingField", DBHelper.IMPORTANCE).apply();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onAddNoteClick(View view){
        Intent intent = new Intent(this, AddNote.class);
        startActivity(intent);
    }

    public void onDeleteClick(View view){
        AlertDialog.Builder confirmDialog;
        confirmDialog = new AlertDialog.Builder(this);
        confirmDialog.setTitle("Deleting");
        confirmDialog.setMessage("Confirm deleting?");
        confirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                db.delete(DBHelper.TABLE_NAME, "_id = ?", new String[]{(noteIDs.get(currentPosition) + "")});
                dbHelper.close();

                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
        confirmDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        confirmDialog.show();
    }

    public void getDataSet(){
        notes.clear();

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c;
        if(sortingField.equalsIgnoreCase(DBHelper.IMPORTANCE)){
            c = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, DBHelper.IMPORTANCE + " DESC");
        }
        else {
            c = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        }

        if(c.moveToFirst()) {
            int idId = c.getColumnIndex("_id");
            int titleId = c.getColumnIndex(DBHelper.TITLE);
            int importanceId = c.getColumnIndex(DBHelper.IMPORTANCE);
            int textId = c.getColumnIndex(DBHelper.TEXT);

            do {
                Note note = new Note(c.getString(titleId), c.getInt(importanceId), c.getString(textId));
                notes.add(note);
                noteIDs.add(c.getInt(idId));
            } while (c.moveToNext());
        } else Toast.makeText(this, "0 rows in table", Toast.LENGTH_SHORT).show();
        c.close();
    }
}
