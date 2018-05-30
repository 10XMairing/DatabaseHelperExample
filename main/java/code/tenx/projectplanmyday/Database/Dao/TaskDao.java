package code.tenx.projectplanmyday.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import code.tenx.projectplanmyday.Objects.Task;

@Dao
public interface TaskDao {
@Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTasks();
@Query("DELETE FROM task_table")
    void deleteAllTasks();
@Delete
    void deleteTask(Task task);
@Update
    void updateTask(Task task);
@Insert
    void insertTask(Task task);
}
