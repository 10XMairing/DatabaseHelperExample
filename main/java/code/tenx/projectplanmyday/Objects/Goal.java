package code.tenx.projectplanmyday.Objects;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "goal_table")
public class Goal {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "_id")
    public Integer id;
    @NonNull
    public String goal;

    public Goal(@NonNull String goal) {
        this.goal = goal;
    }

    public Goal(){
    }
    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public String getGoal() {
        return goal;
    }


}
