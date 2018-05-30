package code.tenx.projectplanmyday.UI.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import code.tenx.projectplanmyday.Objects.Goal;
import code.tenx.projectplanmyday.R;
import code.tenx.projectplanmyday.ViewModels.GoalViewModel;


public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalViewHolder>{


    private List<Goal> mList;
    private static final String LOG_TAG = "GoalAdapter";
    private GoalViewModel viewModel;
    private LayoutInflater inflater;


    public GoalsAdapter(GoalViewModel viewModel) {
        this.viewModel = viewModel;
    }


    public  void insertAndRefresh(final List<Goal> list){
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
                    return mList.get(oldItemPosition).getGoal().equals(list.get(newItemPosition).getGoal());
                }
            });
            mList.clear();
            mList.addAll(list);
            result.dispatchUpdatesTo(this);
        }
    }

    class GoalViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
         GoalViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvGoalText);


        }
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater == null)
            this.inflater = LayoutInflater.from(parent.getContext());

            View view = inflater.inflate(R.layout.recyclerview_item_goal, parent, false);
      return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {

        final Goal currentGoal = mList.get(position);
        holder.textView.setText(currentGoal.getGoal());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "you just clicked : "+currentGoal.getGoal(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mList==null)? 0: mList.size();
    }
}
