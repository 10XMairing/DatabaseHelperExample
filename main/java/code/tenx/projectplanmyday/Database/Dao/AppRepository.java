package code.tenx.projectplanmyday.Database.Dao;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import code.tenx.projectplanmyday.Objects.Goal;
import code.tenx.projectplanmyday.Objects.Task;

public class AppRepository {
    private static GoalDao mGoalDao;
    private static TaskDao mTaskDao;
    private static final String LOG_TAG = "REPOSITORY";
    private static final int ARG1_ACTION_DELETE_ALL = 10;
    private static final int ARG1_ACTION_INSERT = 20;
    private static final int ARG1_ACTION_DELETE_SINGLE = 30;
    private static final int ARG1_ACTION_UPDATE = 40;
    private static final int ARG0_ACTION_GOAL_DAO = 50;
    private static final int ARG0_ACTION_TASK_DAO = 60;
    private AppDatabase mAppDatabase;


    public AppRepository(Application application) {
        mAppDatabase = AppDatabase.getDatabase(application);
        mGoalDao = mAppDatabase.goalDao();
        mTaskDao = mAppDatabase.taskDao();
    }

    public LiveData<List<Goal>> getAllGoals(){
        return mGoalDao.getAllGoals();
    }

    public LiveData<List<Task>> getAllTasks(){
        return mTaskDao.getAllTasks();
    }

    public void insertGoal(final Goal goal){
        Log.d(LOG_TAG, "insertGoal called");
       new WorkerAsync(ARG0_ACTION_GOAL_DAO, ARG1_ACTION_INSERT,mAppDatabase).execute(goal);
    }
    public void insertTask(final Task task){
        new WorkerAsync(ARG0_ACTION_TASK_DAO, ARG1_ACTION_INSERT,mAppDatabase).execute(task);
    }
    public void deleteGoal(final Goal goal){
        new WorkerAsync(ARG0_ACTION_GOAL_DAO, ARG1_ACTION_DELETE_SINGLE,mAppDatabase).execute(goal);
    }
    public void deleteTask(final Task task){
        new WorkerAsync(ARG0_ACTION_TASK_DAO, ARG1_ACTION_DELETE_SINGLE,mAppDatabase).execute(task);
    }
    public void updateGoal(final Goal goal){
        new WorkerAsync(ARG0_ACTION_GOAL_DAO, ARG1_ACTION_UPDATE,mAppDatabase).execute(goal);
    }
    public void updateTask(final Task task){
        new WorkerAsync(ARG0_ACTION_TASK_DAO, ARG1_ACTION_UPDATE,mAppDatabase).execute(task);
    }

    public void deleteAllGoals(){
        new WorkerAsync(ARG0_ACTION_GOAL_DAO, ARG1_ACTION_DELETE_ALL,mAppDatabase).execute();
    }
    public void deleteAllTasks(){
        new WorkerAsync(ARG0_ACTION_TASK_DAO, ARG1_ACTION_DELETE_ALL,mAppDatabase).execute();
    }

    static  class WorkerAsync extends AsyncTask<Object, Void, Void>{

        private int DAO;
        private int ACTION;
        private AppDatabase database;
        WorkerAsync(int DAO, int ACTION, AppDatabase database){
            this.database =database;
            this.DAO = DAO;
            this.ACTION = ACTION;
        }


        @Override
        protected Void doInBackground(Object... objects) {

            if(DAO == ARG0_ACTION_GOAL_DAO){
                GoalDao dao = database.goalDao();
                if(ACTION== ARG1_ACTION_DELETE_ALL)
                    dao.deleteAllGoals();
                if(ACTION==ARG1_ACTION_DELETE_SINGLE)
                    dao.deleteGoal((Goal) objects[0]);
                if(ACTION==ARG1_ACTION_INSERT)
                    dao.insertGoal((Goal) objects[0]);
                if(ACTION==ARG1_ACTION_UPDATE)
                    dao.updateGoal((Goal) objects[0]);

            }
            if (DAO == ARG0_ACTION_TASK_DAO){
                TaskDao dao = database.taskDao();
                if(ACTION== ARG1_ACTION_DELETE_ALL)
                    dao.deleteAllTasks();
                if(ACTION==ARG1_ACTION_DELETE_SINGLE)
                    dao.deleteTask((Task) objects[0]);
                if(ACTION==ARG1_ACTION_INSERT)
                    dao.insertTask((Task) objects[0]);
                if(ACTION==ARG1_ACTION_UPDATE)
                    dao.updateTask((Task) objects[0]);
            }
            return null;
        }
    }





}
