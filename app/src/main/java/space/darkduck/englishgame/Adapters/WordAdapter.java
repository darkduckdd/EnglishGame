package space.darkduck.englishgame.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import space.darkduck.englishgame.R;

public class WordAdapter  extends RecyclerView.Adapter<WordAdapter.WordHolder> {
    private ArrayList<String> listEngWord,listRusWord;
    private ArrayList<Integer> listID;

    public WordAdapter(ArrayList<Integer>listID ,ArrayList<String> listEngWord, ArrayList<String> listRusWord){
        this.listEngWord=listEngWord;
        this.listRusWord=listRusWord;
        this.listID=listID;
    }

    @NonNull
    @Override
    public WordAdapter.WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.word,parent,false);
        return new WordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapter.WordHolder holder, int position) {
        holder.idText.setText(String.valueOf(listID.get(position)));
        holder.engText.setText(listEngWord.get(position));
        holder.rusText.setText(listRusWord.get(position));
    }

    @Override
    public int getItemCount() {
        return listEngWord.size();
    }
    public static class WordHolder extends RecyclerView.ViewHolder{
        private TextView idText,engText,rusText;
        public WordHolder(@NonNull View itemView) {
            super(itemView);
            idText=itemView.findViewById(R.id.idText);
            engText=itemView.findViewById(R.id.textViewEngWord);
            rusText=itemView.findViewById(R.id.textViewRusWord);
        }
    }
}