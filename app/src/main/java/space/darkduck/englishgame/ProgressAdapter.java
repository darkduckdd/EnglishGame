package space.darkduck.englishgame;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressHolder> {
    ArrayList<Integer> listOldProgress, listNewProgress;
    ArrayList<String> listWords;
    public ProgressAdapter(ArrayList<String> words,ArrayList<Integer> oldProgress,ArrayList<Integer> newProgress){
        this.listWords=words;
        this.listOldProgress =oldProgress;
        this.listNewProgress =newProgress;
    }

    @NonNull
    @Override
    public ProgressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.progress,parent,false);
        return new ProgressHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressHolder holder, int position) {
        holder.word.setText(listWords.get(position));
        holder.oldProgress.setText(String.valueOf(listOldProgress.get(position)));
        //holder.newProgress.setText(String.valueOf(listOldProgress.get(position)));
        startCountAnimation(holder.newProgress,listOldProgress.get(position),listNewProgress.get(position));
    }

    @Override
    public int getItemCount() {
        return listWords.size();
    }
    public static class ProgressHolder extends RecyclerView.ViewHolder{

        private TextView word,oldProgress,newProgress;
        public ProgressHolder(@NonNull View itemView) {
            super(itemView);
            word=itemView.findViewById(R.id.textWord);
            oldProgress=itemView.findViewById(R.id.textOldProgress);
            newProgress=itemView.findViewById(R.id.textNewProgress);

        }
    }
    private void startCountAnimation(TextView text, int start,int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end); //0 is min number, 600 is max number
        animator.setDuration(3000); //Duration is in milliseconds
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                text.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }
}
