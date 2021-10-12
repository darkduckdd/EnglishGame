package space.darkduck.englishgame.Adapters;

import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import space.darkduck.englishgame.R;

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
        startCountAnimation(holder.progressBar,listOldProgress.get(position),listNewProgress.get(position),holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listWords.size();
    }
    public static class ProgressHolder extends RecyclerView.ViewHolder{

        private TextView word;
        private ProgressBar progressBar;
        private ImageView imageView;
        public ProgressHolder(@NonNull View itemView) {
            super(itemView);
            word=itemView.findViewById(R.id.textWord);
            progressBar=itemView.findViewById(R.id.progressBarInFragment);
            imageView=itemView.findViewById(R.id.imageViewProgress);

        }
    }
    private void startCountAnimation(ProgressBar pb,int start,int end,ImageView image) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end); //0 is min number, 600 is max number
        animator.setDuration(3000); //Duration is in milliseconds
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                pb.setProgress(Integer.parseInt(animation.getAnimatedValue().toString()));
                if(pb.getProgress()>=99){
                    image.setVisibility(View.VISIBLE);
                }
            }
        });
        animator.start();
    }
}