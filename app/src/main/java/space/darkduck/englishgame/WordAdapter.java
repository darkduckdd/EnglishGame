package space.darkduck.englishgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter  extends RecyclerView.Adapter<WordAdapter.WordHolder> {
    private Context context;
    private ArrayList<String> listEngWord,listRusWord;
    private ArrayList<Integer> listProgress;

    public WordAdapter(Context context, ArrayList<String> listEngWord, ArrayList<String> listRusWord, ArrayList<Integer> listProgress){
        this.context=context;
        this.listEngWord=listEngWord;
        this.listRusWord=listRusWord;
        this.listProgress=listProgress;
    }

    @NonNull
    @Override
    public WordAdapter.WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.word,parent,false);
        return new WordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapter.WordHolder holder, int position) {
        holder.engText.setText(listEngWord.get(position));
        holder.rusText.setText(listRusWord.get(position));
        holder.progressText.setText(String.valueOf(listProgress.get(position)));
    }

    @Override
    public int getItemCount() {
        return listEngWord.size();
    }
    public static class WordHolder extends RecyclerView.ViewHolder{
        private TextView engText,rusText,progressText;
        public WordHolder(@NonNull View itemView) {
            super(itemView);
            engText=itemView.findViewById(R.id.textViewEngWord);
            rusText=itemView.findViewById(R.id.textViewRusWord);
            progressText=itemView.findViewById(R.id.textViewProgress);
        }
    }
}
