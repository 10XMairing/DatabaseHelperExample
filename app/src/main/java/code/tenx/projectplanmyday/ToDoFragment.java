package code.tenx.projectplanmyday;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ToDoFragment extends Fragment {

    static List<ToDoTask> taskList;
    DatabaseHelper myTaskData;
    ToDoTaskAdapter taskAdapter;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.to_do_fragment, container, false);
        ListView lview = view.findViewById(R.id.toDoListView);
        FloatingActionButton fab = view.findViewById(R.id.fabToDo);


        taskList = new ArrayList<>();
        myTaskData = new DatabaseHelper(getActivity());

        taskAdapter = new ToDoTaskAdapter(Objects.requireNonNull(getActivity()), taskList);
        lview.setAdapter(taskAdapter);

        readTaskStorage();

        lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final ToDoTask currentTask = taskList.get(position);
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                @SuppressLint("InflateParams") View inputView = LayoutInflater.from(getActivity()).inflate(R.layout.todo_edit_layout, null);
                final EditText task,start, end;
                final ImageView del;
                final ToggleButton togbtn;
                task = inputView.findViewById(R.id.etToDoTask);
                start = inputView.findViewById(R.id.etToDoStartTime);
                end = inputView.findViewById(R.id.etToDoEndTime);
                togbtn = inputView.findViewById(R.id.todoToggle);
                del = inputView.findViewById(R.id.todoDelete);
                start.setEnabled(false);
                end.setEnabled(false);
                task.setEnabled(false);
                task.setText(currentTask.getTask());
                start.setText(currentTask.getStartTime());
                end.setText(currentTask.getEndTime());
                builder.setView(inputView);
                builder.setCancelable(true);
                final android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteTask(currentTask);
                        dialog.dismiss();
                    }
                });

                togbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            task.setEnabled(true);
                            start.setEnabled(true);
                            end.setEnabled(true);
                            togbtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                        }else{
                            String work = task.getText().toString();
                            String  stime= start.getText().toString();
                            String etime = end.getText().toString();
                            //update data

                            updateTask(currentTask, work, stime, etime);
                            togbtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_edit_black_24dp));
                            dialog.dismiss();
                        }
                    }
                });
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "FAB CLICKED!", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter Your Task!");
                View inputView = LayoutInflater.from(getActivity()).inflate(R.layout.todo_input_layout, null);
                builder.setView(inputView);
                final AlertDialog dialog = builder.create();
                dialog.show();
                final EditText etTask, etStart, etEnd;
                ImageView iAddTask;
                etTask = inputView.findViewById(R.id.etToDoTask);
                etStart = inputView.findViewById(R.id.etToDoStartTime);
                etEnd = inputView.findViewById(R.id.etToDoEndTime);
                iAddTask = inputView.findViewById(R.id.iAddTask);
                iAddTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String task = etTask.getText().toString();
                        String start = etStart.getText().toString();
                        if(start.equals("")){
                            Toast.makeText(getActivity(), "Enter a start time", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String end = etEnd.getText().toString();
                        if(end.equals("")){
                            end = "~";
                        }
                        addTask(task, start, end);
                        dialog.dismiss();
                    }
                });



            }
        });


        return view;
    }


    public void readTaskStorage(){
        Cursor res = myTaskData.getTasksToDoData();
        if(res == null){
            return;
        }

        while(res.moveToNext()){
            String currentTask = res.getString(1);
            String currentStartTime = res.getString(2);
            String currentEndTime = res.getString(3);
            taskList.add(new ToDoTask(currentTask,currentStartTime, currentEndTime));

        }
        taskAdapter.notifyDataSetChanged();

    }

    public void addTask(String task, String startTime, String endTime){
        taskList.add(new ToDoTask(task, startTime, endTime));
        myTaskData.addTasksToDo(task, startTime, endTime);
        taskAdapter.notifyDataSetChanged();
    }

    public void updateTask(ToDoTask todo , String newTask, String newStartTime, String newEndTime){
        myTaskData.updateTaskToDo(todo.getTask(), newTask, newStartTime, newEndTime);
        int i = taskList.indexOf(todo);
        taskList.set(i, new ToDoTask(newTask, newStartTime, newEndTime));
        taskAdapter.notifyDataSetChanged();
    }

    public void deleteTask(ToDoTask todo){
        myTaskData.deleteToDoTask(todo.getTask());
        taskList.remove(todo);
        taskAdapter.notifyDataSetChanged();
    }
}
