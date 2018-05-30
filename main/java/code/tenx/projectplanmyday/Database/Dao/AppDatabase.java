package code.tenx.projectplanmyday.Database.Dao;


import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import code.tenx.projectplanmyday.Objects.Goal;
import code.tenx.projectplanmyday.Objects.Task;

@Database(entities = {Goal.class, Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract GoalDao goalDao();
    public abstract TaskDao taskDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Application application){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(application, AppDatabase.class, "main_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }


}
