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
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE1_NAME+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "goals TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE1_NAME);
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

    public Cursor getData(){
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

    public void deleteData(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE1_NAME, "goals=?", new String[] {text});

    }


}
