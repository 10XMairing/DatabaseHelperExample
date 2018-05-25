package code.tenx.projectplanmyday;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GoalsAdapter extends ArrayAdapter<String> {

    public GoalsAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, 0,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_goals, parent, false);
        }
        String goal = getItem(position);

        TextView textGoal = view.findViewById(R.id.tvGoalText);
        textGoal.setText(goal);

        return view;

    }
}
