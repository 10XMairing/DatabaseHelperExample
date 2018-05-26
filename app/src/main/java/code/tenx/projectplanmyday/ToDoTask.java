package code.tenx.projectplanmyday;

public class ToDoTask {

    private String task;
    private String startTime;
    private String endTime;

    public ToDoTask(String task, String startTime, String endTime) {
        this.task = task;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ToDoTask(String task, String startTime) {
        this.task = task;
        this.startTime = startTime;
        this.endTime = "~";
    }

    public String getTask() {
        return task;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
