package space.darkduck.englishgame;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileWorker {
    private Context context;
    private ArrayList<String> engWords=new ArrayList<>(),rusWords=new ArrayList<>();
    public FileWorker(Context context){
        this.context=context;
    }

    public ArrayList<String> readEngFile(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("engwords.txt"), "UTF-8"));
            String mLine;

            while ((mLine = reader.readLine()) != null) {
                engWords.add(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return engWords;
    }

    public ArrayList<String> readRusFile(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("ruswords.txt"), "UTF-8"));
            String mLine;

            while ((mLine = reader.readLine()) != null) {
                rusWords.add(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return rusWords;
    }
}
