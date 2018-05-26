package code.tenx.projectplanmyday;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainFragment extends Fragment {

    private static List<String> goals_list;
    GoalsAdapter goalsAdapter;
    ListView listView;
    ImageView image1,image2,image3,image4, iAdd,iDelete;
    ToggleButton toggleButton;
    TextView ivGoalsHeader;
    EditText inputGoal,editGoal;
    DatabaseHelper myDb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fabHome);
        listView = view.findViewById(R.id.listGolas);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        image4 = view.findViewById(R.id.image4);
        ivGoalsHeader = view.findViewById(R.id.tvGoalsHeader);
        myDb = new DatabaseHelper(getActivity());
        goals_list = new ArrayList<>();
        goalsAdapter = new GoalsAdapter(Objects.requireNonNull(getActivity()), goals_list);
        listView.setAdapter(goalsAdapter);
        readGoalsData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View inputView = LayoutInflater.from(getActivity()).inflate(R.layout.goal_input_layout, null);
                builder.setView(inputView);
                builder.setTitle("Add Goals!");
                final AlertDialog dialog = builder.create();
                dialog.show();
                final EditText input = inputView.findViewById(R.id.etGoalInput);
                ImageView btn = inputView.findViewById(R.id.iAdd);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = input.getText().toString();
                        goals_list.add(text);
                        myDb.addGoals(text);
                        goalsAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String currentGoal = goals_list.get(position);
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                @SuppressLint("InflateParams") View inputView = LayoutInflater.from(getActivity()).inflate(R.layout.goal_edit_delete_field, null);
                editGoal = inputView.findViewById(R.id.etGoalEdit);
                editGoal.setEnabled(false);
                editGoal.setText(currentGoal);
                iDelete = inputView.findViewById(R.id.iDelete);
                toggleButton = inputView.findViewById(R.id.toggleButton);
                builder.setView(inputView);
                builder.setCancelable(true);
                final android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                iDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteData(currentGoal);

                        dialog.dismiss();
                    }
                });

                toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editGoal.setEnabled(true);
                            toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                        }else{
                            String text = editGoal.getText().toString();
                            updateData(currentGoal, text);
                            editGoal.setEnabled(false);
                            toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_edit_black_24dp));
                            dialog.dismiss();
                        }
                    }
                });
                return true;
            }
        });






        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalsInput();
            }
        });


        return view;
    }


    public void goalsInput(){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        @SuppressLint("InflateParams") View inputView = LayoutInflater.from(getActivity()).inflate(R.layout.goal_input_layout, null);
        inputGoal = inputView.findViewById(R.id.etGoalInput);
        iAdd = inputView.findViewById(R.id.iAdd);
        builder.setView(inputView);
        builder.setCancelable(true);
        final android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
        iAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goal = inputGoal.getText().toString();
                if(goal.equals("")){
                    return;
                }
                myDb.addGoals(goal);
                goals_list.add(goal);
                goalsAdapter.notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(getActivity(), "Added your Goal!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void readGoalsData(){
        Cursor res = myDb.getGoalsData();
        if(res == null){
            return;
        }

        while(res.moveToNext()){
            String currentGoal = res.getString(1);
            goals_list.add(currentGoal);
        }
        goalsAdapter.notifyDataSetChanged();
    }

    public void updateData(String text, String newGoal){

        myDb.updateGoals(text, newGoal);
        int i = goals_list.indexOf(text);
        goals_list.set(i, newGoal);
        goalsAdapter.notifyDataSetChanged();

    }

    public void deleteData(String text){
        myDb.deleteGoalsData(text);
        goals_list.remove(text);
        goalsAdapter.notifyDataSetChanged();

    }


}
