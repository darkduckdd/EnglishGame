package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {
    private Spinner wordSpinner;
    private Button applyButton,changeTimeButton;
    TextView currentDateTime;
    Calendar dateAndTime=Calendar.getInstance();
    private int count;
    private void init(){
        wordSpinner=findViewById(R.id.wordSpinner);
        applyButton=findViewById(R.id.applyButton);
        changeTimeButton=findViewById(R.id.changeTimeButton);
        currentDateTime=findViewById(R.id.currentTimeTextView);
        Integer[] counts={5,10,15,20};
        ArrayAdapter<Integer> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,counts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        wordSpinner.setAdapter(adapter);
        wordSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                count=counts[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        applyButton.setOnClickListener((v)->{
            ApplyChanges();
        });

        changeTimeButton.setOnClickListener((v)->{
            setTime();
        });
        setInitialDateTime();
    }
    // отображаем диалоговое окно для выбора времени
    public void setTime() {
        new TimePickerDialog(SettingActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    private void setInitialDateTime() {
        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
    }

    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }
    private void ApplyChanges(){
        DatabaseHelper.setWordLimit(count);
        Intent intent =new Intent(this,MainActivity.class);
        Statistics.setSaveCalendar(dateAndTime);
        startActivity(intent);
        finish();
    }
}