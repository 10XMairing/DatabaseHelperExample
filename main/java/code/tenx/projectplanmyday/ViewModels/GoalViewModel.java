package code.tenx.projectplanmyday.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import code.tenx.projectplanmyday.Database.Dao.AppRepository;
import code.tenx.projectplanmyday.Objects.Goal;
import code.tenx.projectplanmyday.Objects.Task;

public class GoalViewModel extends AndroidViewModel {

    private static AppRepository appRepository;

    public GoalViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);
    }

    public LiveData<List<Goal>> getAllGoals() {
        return appRepository.getAllGoals();
    }
    public void deleteAllGoals() {
        appRepository.deleteAllGoals();
    }
    public void insertGoal(Goal goal) {
        appRepository.insertGoal(goal);
    }
    public void deleteGoal(Goal goal) {
        appRepository.deleteGoal(goal);
    }
    public void updateGoal(Goal goal) {
        appRepository.updateGoal(goal);
    }

}
