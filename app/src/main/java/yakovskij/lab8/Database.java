package yakovskij.lab8;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE notes (id INT not null unique primary key, title TEXT, txt TEXT);";
        db.execSQL(sql);
//        this.addNote("title", "texts!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public int getMaxId() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT MAX(id) FROM notes;";

        Cursor cur = db.rawQuery(sql, null);

        if (cur.moveToFirst()) {
            int a = cur.getInt(0);
            cur.close();
            return a;
        }

        return 0;
    }


    public void addNote(String title, String txt) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO notes VALUES ("+(getMaxId()+1)+", '" + title + "', '" + txt + "');";
        db.execSQL(sql);
    }

    public Note getNote(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, title, txt FROM notes WHERE id = " + id + ";";

        Cursor cur = db.rawQuery(sql, null);

        if (cur.moveToFirst()) {
            Note note = new Note();
            note.id = cur.getInt(0);
            note.title = cur.getString(1);
            note.content = cur.getString(2);
            cur.close();
            return note;
        }
        return null;
    }


    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> noteList = new ArrayList<Note>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, title, txt FROM notes;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()) {
            do {
                Note note = new Note();
                note.id = cur.getInt(0);
                note.title = cur.getString(1);
                note.content = cur.getString(2);
                noteList.add(note);
            } while (cur.moveToNext());

        }
        cur.close();
        return noteList;
    }

    public void alterNote(int noteIndex, String title, String text) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE notes SET title='"+title+"',txt='"+text+"' where id="+noteIndex+";";
        db.execSQL(sql);
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Delete from notes where id="+id+";";
        db.execSQL(sql);
    }
}
