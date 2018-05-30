package code.tenx.projectplanmyday.UI;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import code.tenx.projectplanmyday.ViewModels.TaskViewModel;
import code.tenx.projectplanmyday.UI.Adapters.TaskAdapter;
import code.tenx.projectplanmyday.Objects.Task;
import code.tenx.projectplanmyday.R;


public class TaskFragment extends Fragment {

    private static List<Task> taskList;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private TaskViewModel viewModel;
    private static final String LOG_TAG = "TaskFragment";



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_task, container, false);

        recyclerView = view.findViewById(R.id.recyclerviewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewModel = ViewModelProviders.of(TaskFragment.this).get(TaskViewModel.class);
        viewModel.getAllTasks().observe(TaskFragment.this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if(taskAdapter==null){
                    taskAdapter = new TaskAdapter(getActivity(), tasks);
                    recyclerView.setAdapter(taskAdapter);
                    Log.d(LOG_TAG, "new adapter created");
                }else{
                taskAdapter.insertAndRefresh(tasks);}
            }
        });

        /*lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Task currentTask = taskList.get(position);
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                @SuppressLint("InflateParams") View inputView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_task_edit, null);
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
                task.setText(currentTask.getTodo());
                start.setText(currentTask.getStartTime());
                end.setText(currentTask.getEndTime());
                builder.setView(inputView);
                builder.setCancelable(true);
                final android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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


                            togbtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_edit_black_24dp));
                            dialog.dismiss();
                        }
                    }
                });
                return true;
            }
        });*/


        FloatingActionButton fab = view.findViewById(R.id.fabToDo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter Your Task!");
                View inputView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_task_input, null);
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

                        viewModel.insertTask(new Task(task, start, end));
                        dialog.dismiss();
                    }
                });



            }
        });


        return view;
    }





}
