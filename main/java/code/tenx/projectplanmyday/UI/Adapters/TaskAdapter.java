package code.tenx.projectplanmyday.UI.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import code.tenx.projectplanmyday.Objects.Task;
import code.tenx.projectplanmyday.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context mContext;
    private List<Task> mList;
    class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView tvToDo, tvStart, tvEnd;
         TaskViewHolder(View itemView) {
            super(itemView);
            tvToDo = itemView.findViewById(R.id.tvTaskToDo);
            tvStart = itemView.findViewById(R.id.tvTaskStart);
            tvEnd = itemView.findViewById(R.id.tvTaskEnd);

        }
    }

    public TaskAdapter(Context mContext, List<Task> tasks) {
        this.mContext = mContext;
        this.mList = tasks;
    }

    public  void insertAndRefresh(final List<Task> list){
        if(mList==null){
            this.mList = list;
        }else{
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mList.size();
                }

                @Override
                public int getNewListSize() {
                    return list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mList.get(oldItemPosition).equals(list.get(newItemPosition));
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return mList.get(oldItemPosition).getTodo().equals(list.get(newItemPosition).getTodo());
                }
            });
            mList = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = mList.get(position);
        holder.tvToDo.setText(currentTask.getTodo());
        holder.tvStart.setText(currentTask.getStartTime());
        holder.tvEnd.setText(currentTask.getEndTime());

    }

    @Override
    public int getItemCount() {
        return (mList==null)? 0 : mList.size();
    }




}

