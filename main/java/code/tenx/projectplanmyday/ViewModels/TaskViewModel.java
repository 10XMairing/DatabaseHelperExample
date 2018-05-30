package code.tenx.projectplanmyday.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import code.tenx.projectplanmyday.Database.Dao.AppRepository;
import code.tenx.projectplanmyday.Objects.Task;

public class TaskViewModel extends AndroidViewModel {

    private static AppRepository appRepository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);
    }

    public LiveData<List<Task>> getAllTasks() { return appRepository.getAllTasks(); }
    public void deleteAllTasks() { appRepository.deleteAllTasks(); }
    public void deleteTask(Task task) { appRepository.deleteTask(task); }
    public void updateTask(Task task) { appRepository.updateTask(task); }
    public void insertTask(Task task) { appRepository.insertTask(task); }
}
