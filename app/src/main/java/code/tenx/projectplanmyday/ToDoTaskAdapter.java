package code.tenx.projectplanmyday;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ToDoTaskAdapter extends ArrayAdapter<ToDoTask> {

    private Context mCtx;
    private List<ToDoTask> taskList;

    public ToDoTaskAdapter(@NonNull Context context, @NonNull List<ToDoTask> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.todo_item_holder_layout, parent, false);
        }
        ToDoTask toDO = getItem(position);

        TextView task,start,end;
        task = view.findViewById(R.id.tvToDoTask);
        start = view.findViewById(R.id.tvToDoStartTime);
        end = view.findViewById(R.id.tvToDoEndTime);
        task.setText(toDO.getTask());
        start.setText(toDO.getStartTime());
        end.setText(toDO.getEndTime());

        return view;
    }
}

