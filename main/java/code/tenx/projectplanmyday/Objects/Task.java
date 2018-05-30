package code.tenx.projectplanmyday.Objects;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.support.annotation.NonNull;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "_id")
    public Integer id;
    @NonNull
    @ColumnInfo(name = "todo")
    public String todo;
    @NonNull
    @ColumnInfo(name = "start_time")
    public String startTime;
    @ColumnInfo(name = "end_time")
    public String endTime;

    public Task(String task, String startTime, String endTime) {
        this.todo = task;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Task(){

    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public String getTodo() {
        return todo;
    }

    @NonNull
    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }


}
