package code.tenx.projectplanmyday.Database.Dao;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class AppDatabase_Impl extends AppDatabase {
  private volatile GoalDao _goalDao;

  private volatile TaskDao _taskDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `goal_table` (`_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `goal` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `task_table` (`_id` INTEGER NOT NULL, `todo` TEXT NOT NULL, `start_time` TEXT NOT NULL, `end_time` TEXT, PRIMARY KEY(`_id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"109836c8e3a451acb607ff6230f8e327\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `goal_table`");
        _db.execSQL("DROP TABLE IF EXISTS `task_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsGoalTable = new HashMap<String, TableInfo.Column>(2);
        _columnsGoalTable.put("_id", new TableInfo.Column("_id", "INTEGER", true, 1));
        _columnsGoalTable.put("goal", new TableInfo.Column("goal", "TEXT", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGoalTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGoalTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGoalTable = new TableInfo("goal_table", _columnsGoalTable, _foreignKeysGoalTable, _indicesGoalTable);
        final TableInfo _existingGoalTable = TableInfo.read(_db, "goal_table");
        if (! _infoGoalTable.equals(_existingGoalTable)) {
          throw new IllegalStateException("Migration didn't properly handle goal_table(code.tenx.projectplanmyday.Objects.Goal).\n"
                  + " Expected:\n" + _infoGoalTable + "\n"
                  + " Found:\n" + _existingGoalTable);
        }
        final HashMap<String, TableInfo.Column> _columnsTaskTable = new HashMap<String, TableInfo.Column>(4);
        _columnsTaskTable.put("_id", new TableInfo.Column("_id", "INTEGER", true, 1));
        _columnsTaskTable.put("todo", new TableInfo.Column("todo", "TEXT", true, 0));
        _columnsTaskTable.put("start_time", new TableInfo.Column("start_time", "TEXT", true, 0));
        _columnsTaskTable.put("end_time", new TableInfo.Column("end_time", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTaskTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTaskTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTaskTable = new TableInfo("task_table", _columnsTaskTable, _foreignKeysTaskTable, _indicesTaskTable);
        final TableInfo _existingTaskTable = TableInfo.read(_db, "task_table");
        if (! _infoTaskTable.equals(_existingTaskTable)) {
          throw new IllegalStateException("Migration didn't properly handle task_table(code.tenx.projectplanmyday.Objects.Task).\n"
                  + " Expected:\n" + _infoTaskTable + "\n"
                  + " Found:\n" + _existingTaskTable);
        }
      }
    }, "109836c8e3a451acb607ff6230f8e327", "92622906151ac3eb496181f4cd6d82a6");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "goal_table","task_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `goal_table`");
      _db.execSQL("DELETE FROM `task_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public GoalDao goalDao() {
    if (_goalDao != null) {
      return _goalDao;
    } else {
      synchronized(this) {
        if(_goalDao == null) {
          _goalDao = new GoalDao_Impl(this);
        }
        return _goalDao;
      }
    }
  }

  @Override
  public TaskDao taskDao() {
    if (_taskDao != null) {
      return _taskDao;
    } else {
      synchronized(this) {
        if(_taskDao == null) {
          _taskDao = new TaskDao_Impl(this);
        }
        return _taskDao;
      }
    }
  }
}
