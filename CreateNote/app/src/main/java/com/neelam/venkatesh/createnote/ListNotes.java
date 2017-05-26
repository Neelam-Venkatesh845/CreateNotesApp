package com.neelam.venkatesh.createnote;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("NewNote").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(ListNotes.this, MainActivity.class);
                startActivity(intent);

                return false;
            }
        });
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListView();
    }

    private void loadListView() {
        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayList<String> listTitle = new ArrayList<>();
        final ArrayList<String> notes = new ArrayList<>();
        NoteDatabase database = new NoteDatabase(ListNotes.this);
        SQLiteDatabase db = database.getWritableDatabase();

        Cursor cursor = db.query("SavedNotes", null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                String title = cursor.getString(0);
                listTitle.add(title);

                String note = cursor.getString(1);
                notes.add(note);

            } while (cursor.moveToNext());

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listTitle);

            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView textView = (TextView) findViewById(R.id.notesDisplay);
                    textView.setText(notes.get(i));

                }
            });
        } else {
            Toast.makeText(ListNotes.this, "Notes are Empty", Toast.LENGTH_LONG).show();
        }

    }
}
