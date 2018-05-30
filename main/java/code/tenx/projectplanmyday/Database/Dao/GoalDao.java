package code.tenx.projectplanmyday.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import code.tenx.projectplanmyday.Objects.Goal;
import code.tenx.projectplanmyday.Objects.Task;

@Dao
public interface GoalDao {
    @Query("SELECT * FROM goal_table")
    LiveData<List<Goal>> getAllGoals();
    @Query("DELETE FROM goal_table")
    void deleteAllGoals();
    @Delete
    void deleteGoal(Goal goal);
    @Update
    void updateGoal(Goal goal);
    @Insert
    void insertGoal(Goal goal);
}
