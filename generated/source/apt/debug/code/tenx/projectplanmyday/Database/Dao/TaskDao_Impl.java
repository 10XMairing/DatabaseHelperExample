package code.tenx.projectplanmyday.Database.Dao;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import android.support.annotation.NonNull;
import code.tenx.projectplanmyday.Objects.Task;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfTask;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllTasks;

  public TaskDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTask = new EntityInsertionAdapter<Task>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `task_table`(`_id`,`todo`,`start_time`,`end_time`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Task value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
        if (value.todo == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.todo);
        }
        if (value.startTime == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.startTime);
        }
        if (value.endTime == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.endTime);
        }
      }
    };
    this.__deletionAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `task_table` WHERE `_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Task value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
      }
    };
    this.__updateAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `task_table` SET `_id` = ?,`todo` = ?,`start_time` = ?,`end_time` = ? WHERE `_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Task value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
        if (value.todo == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.todo);
        }
        if (value.startTime == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.startTime);
        }
        if (value.endTime == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.endTime);
        }
        if (value.id == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.id);
        }
      }
    };
    this.__preparedStmtOfDeleteAllTasks = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM task_table";
        return _query;
      }
    };
  }

  @Override
  public void insertTask(Task task) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfTask.insert(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTask(Task task) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfTask.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateTask(Task task) {
    __db.beginTransaction();
    try {
      __updateAdapterOfTask.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllTasks() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTasks.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllTasks.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Task>> getAllTasks() {
    final String _sql = "SELECT * FROM task_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Task>>() {
      private Observer _observer;

      @Override
      protected List<Task> compute() {
        if (_observer == null) {
          _observer = new Observer("task_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("_id");
          final int _cursorIndexOfTodo = _cursor.getColumnIndexOrThrow("todo");
          final int _cursorIndexOfStartTime = _cursor.getColumnIndexOrThrow("start_time");
          final int _cursorIndexOfEndTime = _cursor.getColumnIndexOrThrow("end_time");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Task _item;
            _item = new Task();
            if (_cursor.isNull(_cursorIndexOfId)) {
              _item.id = null;
            } else {
              _item.id = _cursor.getInt(_cursorIndexOfId);
            }
            _item.todo = _cursor.getString(_cursorIndexOfTodo);
            _item.startTime = _cursor.getString(_cursorIndexOfStartTime);
            _item.endTime = _cursor.getString(_cursorIndexOfEndTime);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
