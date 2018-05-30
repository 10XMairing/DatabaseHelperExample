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
import code.tenx.projectplanmyday.Objects.Goal;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class GoalDao_Impl implements GoalDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfGoal;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfGoal;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfGoal;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllGoals;

  public GoalDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGoal = new EntityInsertionAdapter<Goal>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `goal_table`(`_id`,`goal`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Goal value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
        if (value.goal == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.goal);
        }
      }
    };
    this.__deletionAdapterOfGoal = new EntityDeletionOrUpdateAdapter<Goal>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `goal_table` WHERE `_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Goal value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
      }
    };
    this.__updateAdapterOfGoal = new EntityDeletionOrUpdateAdapter<Goal>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `goal_table` SET `_id` = ?,`goal` = ? WHERE `_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Goal value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
        if (value.goal == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.goal);
        }
        if (value.id == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.id);
        }
      }
    };
    this.__preparedStmtOfDeleteAllGoals = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM goal_table";
        return _query;
      }
    };
  }

  @Override
  public void insertGoal(Goal goal) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfGoal.insert(goal);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteGoal(Goal goal) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfGoal.handle(goal);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateGoal(Goal goal) {
    __db.beginTransaction();
    try {
      __updateAdapterOfGoal.handle(goal);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllGoals() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllGoals.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllGoals.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Goal>> getAllGoals() {
    final String _sql = "SELECT * FROM goal_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Goal>>() {
      private Observer _observer;

      @Override
      protected List<Goal> compute() {
        if (_observer == null) {
          _observer = new Observer("goal_table") {
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
          final int _cursorIndexOfGoal = _cursor.getColumnIndexOrThrow("goal");
          final List<Goal> _result = new ArrayList<Goal>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Goal _item;
            _item = new Goal();
            if (_cursor.isNull(_cursorIndexOfId)) {
              _item.id = null;
            } else {
              _item.id = _cursor.getInt(_cursorIndexOfId);
            }
            _item.goal = _cursor.getString(_cursorIndexOfGoal);
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
