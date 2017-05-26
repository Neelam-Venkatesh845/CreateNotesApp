package com.neelam.venkatesh.createnote;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by SKamasala2 on 2/16/2017.
 */

public class NoteDatabase extends SQLiteOpenHelper {

    public NoteDatabase(Context context) {
        super(context, "NoteDatabase", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { // only once in life...
        sqLiteDatabase.execSQL("create table SavedNotes(title TEXT, notes TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //backup start here
         ArrayList<String> listTitle = new ArrayList<>();
        final ArrayList<String> notes = new ArrayList<>();
        Cursor cursor = db.query("SavedNotes", null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                String title = cursor.getString(0);
                listTitle.add(title);

                String note = cursor.getString(1);
                notes.add(note);

            } while (cursor.moveToNext());
        }
        //backup eneded here....

        db.delete("SavedNotes",null,null);//table deetelde..

        db.execSQL("create table SavedNotes(title TEXT, notes TEXT, date TEXT)");


    }
}
