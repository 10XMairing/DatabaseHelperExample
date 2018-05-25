package code.tenx.projectplanmyday;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

public class SecondActivity extends AppCompatActivity{

    private static List<String> goals_list;
    GoalsAdapter goalsAdapter;
    ListView listView;
    ImageView image1,image2,image3,image4, iAdd,iDelete;
    ToggleButton toggleButton;
    TextView ivGoalsHeader;
    EditText inputGoal,editGoal;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment);

        listView = findViewById(R.id.listGolas);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        ivGoalsHeader = findViewById(R.id.tvGoalsHeader);

        myDb = new DatabaseHelper(this);
        goals_list = new ArrayList<>();
        goalsAdapter = new GoalsAdapter(this, goals_list);
        listView.setAdapter(goalsAdapter);
        readGoalsData();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String currentGoal = goals_list.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
                View inputView = LayoutInflater.from(SecondActivity.this).inflate(R.layout.goal_edit_delete_field, null);
                editGoal = inputView.findViewById(R.id.etGoalEdit);
                editGoal.setEnabled(false);
                editGoal.setText(currentGoal);
                iDelete = inputView.findViewById(R.id.iDelete);
                toggleButton = inputView.findViewById(R.id.toggleButton);
                builder.setView(inputView);
                builder.setCancelable(true);
                final AlertDialog dialog = builder.create();
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

    }

    public void goalsInput(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
        View inputView = LayoutInflater.from(SecondActivity.this).inflate(R.layout.goal_input_layout, null);
        inputGoal = inputView.findViewById(R.id.etGoalInput);
        iAdd = inputView.findViewById(R.id.iAdd);
        builder.setView(inputView);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.create();
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
                Toast.makeText(SecondActivity.this, "Added your Goal!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void readGoalsData(){
        Cursor res = myDb.getData();
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
        myDb.deleteData(text);
        goals_list.remove(text);
        goalsAdapter.notifyDataSetChanged();

    }

}
