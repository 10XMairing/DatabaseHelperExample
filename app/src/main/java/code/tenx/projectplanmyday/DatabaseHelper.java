package code.tenx.projectplanmyday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "database.db";
    private static final String TABLE1_NAME = "goals_list";
    private static final String  COL_1= "_id";
    private static final String  COL_2 = "goals";

    private static final String TABLE2_NAME = "todo_list";
    private static final String COL_2_1 = "_id";
    private static final String COL_2_2 = "task";
    private static final String COL_2_3 = "start_time";
    private static final String COL_2_4 = "end_time";




    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE1_NAME+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "goals TEXT NOT NULL)");

        db.execSQL("CREATE TABLE "+TABLE2_NAME+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "task TEXT NOT NULL," +
                "start_time TEXT NOT NULL," +
                "end_time TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE1_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE2_NAME);
            onCreate(db);
    }

    public boolean addGoals(String goal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, goal);
        Long result = db.insert(TABLE1_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getGoalsData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE1_NAME, null);
        return res;
    }

    public void updateGoals(String text, String newGoal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, newGoal);
        int i = db.update(TABLE1_NAME, contentValues, "goals=?", new String[] {text});
    }

    public void deleteGoalsData(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE1_NAME, "goals=?", new String[] {text});

    }


    public void deleteToDoTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE2_NAME, "task=?", new String[] {task});

    }

    public void updateTaskToDo(String oldTask, String newTask,String newStartTime, String newEndTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_2, newTask);
        contentValues.put(COL_2_3, newStartTime);
        contentValues.put(COL_2_4, newEndTime);
        db.update(TABLE2_NAME, contentValues, "task=?", new String[] {oldTask});
    }

    public Cursor getTasksToDoData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE2_NAME, null);
        return res;
    }

    public boolean addTasksToDo(String newTask, String newStartTime, String newEndTIme){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_2, newTask);
        contentValues.put(COL_2_3, newStartTime);
        contentValues.put(COL_2_4, newEndTIme);
        Long result = db.insert(TABLE2_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

}
