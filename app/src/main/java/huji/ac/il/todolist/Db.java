package huji.ac.il.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Vector;

public class Db extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private DbListeners serviceListener;


    public Db(Context context, DbListeners serviceListener) {
        super(context, "todo_db", null, 1);
        this.serviceListener = serviceListener;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        Vector<String> todolistColumns = new Vector<String>();
        todolistColumns.add("`_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL");
        todolistColumns.add("`title` TEXT");
        todolistColumns.add("`due` LONG");
        createTable("todolist", todolistColumns);

        serviceListener.onDbOpened();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.db = db;
        switch(oldVersion) {
            case 1:

            case 2:

                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown oldVersion" + oldVersion);
        }
        serviceListener.onDbOpened();
    }

    @Override
    public void onOpen (SQLiteDatabase db) {
        this.db = db;
        serviceListener.onDbOpened();
    }
	
	public void addRow(Row row) {
        db.execSQL("INSERT INTO todolist (title, due) VALUES ('" + row.toDo + "', '" + row.date + "')");
	}
	
	public Row getRow(int id) {
        Row row = null;
        Cursor cursor = db.rawQuery("SELECT * FROM todolist WHERE `id` = '"+id+"'", null);
        if (cursor.moveToFirst()) {
            row = new Row(cursor.getString(cursor.getColumnIndex("title")), cursor.getLong(cursor.getColumnIndex("due")), id);
        }

		return row;
	}

    public ArrayList<Row> getAllRows(){
        ArrayList<Row> rows = new ArrayList<Row>();
        Cursor cursor = db.rawQuery("SELECT * from todolist", null);
        if (cursor.moveToFirst()) {
            do {
                rows.add(new Row(cursor.getString(cursor.getColumnIndex("title")), cursor.getLong(cursor.getColumnIndex("due")), cursor.getInt(cursor.getColumnIndex("_id"))));
            } while (cursor.moveToNext());
        }

		return rows;
	}
	
	public void removeRow(int id){
        db.execSQL("DELETE FROM todolist WHERE `_id`="+id);
	}
	
	private void createTable(String name, Vector<String> columns) {
        String createTable = "CREATE TABLE IF NOT EXISTS "+name+"(";
        for (String column : columns) {
            createTable+= column+", ";
        }
        createTable = createTable.substring(0, createTable.length()-2);
        createTable+=");";
        db.execSQL(createTable);
	}

    public void dropTable(String name) {
        db.execSQL("DROP TABLE IF EXISTS "+name+"");
    }
}