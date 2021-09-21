package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//Класс для добавление данных в БД
public class AddActivity extends AppCompatActivity {

    EditText engInput,rusInput;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        engInput=findViewById(R.id.engInput);
        rusInput=findViewById(R.id.rusInput);
        saveButton=findViewById(R.id.saveButton);
        engInput.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start,
                                               int end, Spanned dest, int dstart, int dend) {
                        if (source.equals("")) { // for backspace
                            return source;
                        }
                        if (source.toString().matches("[a-zA-Z]+")) {
                            return source;
                        }
                        return "";
                    }
                }
        });
        rusInput.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start,
                                               int end, Spanned dest, int dstart, int dend) {
                        if (source.equals("")) { // for backspace
                            return source;
                        }
                        if (source.toString().matches("[а-яА-Я]+")) {
                            return source;
                        }
                        return "";
                    }
                }
        });
        saveButton.setOnClickListener((v)-> {
                MyDatabaseHelper myDB=new MyDatabaseHelper(AddActivity.this);
                myDB.addDictionary(engInput.getText().toString().trim(),
                        rusInput.getText().toString().trim(),0);
                engInput.setText("");
                rusInput.setText("");
        });
    }
}