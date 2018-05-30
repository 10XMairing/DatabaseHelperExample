package code.tenx.projectplanmyday.UI;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import code.tenx.projectplanmyday.ViewModels.GoalViewModel;
import code.tenx.projectplanmyday.Objects.Goal;
import code.tenx.projectplanmyday.UI.Adapters.GoalsAdapter;
import code.tenx.projectplanmyday.R;



public class MainFragment extends Fragment {

    private static List<String> goals_list;


    ToggleButton toggleButton;
    EditText inputGoal,editGoal;
    GoalViewModel viewModel;
    RecyclerView recyclerView;
    GoalsAdapter adapter;
    private static List<Goal> mlist;
    private static String LOG_TAG = "MainFragment";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //instantiate the viewModel

    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = view.findViewById(R.id.recyclerviewGoals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //get the GoalViewModel.class
        viewModel = ViewModelProviders.of(MainFragment.this).get(GoalViewModel.class);
        //place an observer on Livedata<List<Goal>> for change
        viewModel.getAllGoals().observe(MainFragment.this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(@Nullable final List<Goal> goals) {

                if(adapter == null){
                    adapter = new GoalsAdapter(viewModel);
                    recyclerView.setAdapter(adapter);
                    Log.d(LOG_TAG, "onChanged: new adapter created");
                }else{
                    adapter.insertAndRefresh(goals);
                }
                Log.d(LOG_TAG, "onChange called size -> "+goals.size());
                adapter.insertAndRefresh(goals);
            }
        });


        //take input for Goal by clicking fab
        FloatingActionButton fab = view.findViewById(R.id.fabHome);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //build a dialog with a custom view
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View inputView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_goal_input, null);
                builder.setView(inputView);
                builder.setTitle("Add Goals!");
                final AlertDialog dialog = builder.create();
                dialog.show();
                final EditText input = inputView.findViewById(R.id.etGoalInput);
                ImageView btn = inputView.findViewById(R.id.iAdd);
                //set response when user clicks the add button on the dialog
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = input.getText().toString();
                        viewModel.insertGoal(new Goal(text));
                        dialog.dismiss();
                    }
                });
            }
        });
        //delete all by clicking fab delete
        FloatingActionButton fabDelete = view.findViewById(R.id.fabhomeDelete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteAllGoals();
            }
        });

        return view;

    }






        //edit goals by clicking recycler view item and send this listener to adapter constructor





}
