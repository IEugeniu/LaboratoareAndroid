package com.example.laboratorul_2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.laboratorul_2.Alarm.AlertReceiver;
import com.example.laboratorul_2.Model.Event;
import com.example.laboratorul_2.R;
import com.example.laboratorul_2.Model.Task;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Pattern;

public class AddEventActivity extends AppCompatActivity {

    private String date;
    private TimePicker timePicker;
    private String time;
    private TextView dateTextView;
    private EditText taskEditText;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        date = getIntent().getStringExtra("date");

        dateTextView = (TextView) findViewById(R.id.dateTextAdd);
        timePicker = (TimePicker) findViewById(R.id.timePickerAdd);
        taskEditText = (EditText) findViewById(R.id.editTaskAdd);
        saveButton = (Button) findViewById(R.id.saveButtonAdd);

        dateTextView.setText(date);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = timePicker.getHour() + ":" + timePicker.getMinute();

                String[] date_split_table = date.split(Pattern.quote("."));

                int day = Integer.parseInt(date_split_table[0]);
                int month = Integer.parseInt(date_split_table[1]);
                int year = Integer.parseInt(date_split_table[2]);

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day, timePicker.getHour(), timePicker.getMinute(), 0);

                Random r = new Random();
                Integer requestCode = r.nextInt();
                startAlarm(calendar, requestCode, time, taskEditText.getText().toString());

                File xmlFile = new File(getFilesDir() + File.separator + date + ".xml");
                if(xmlFile.exists()) {

                    Event event = XMLDeserialization(xmlFile);

                    Task task = new Task(time, taskEditText.getText().toString(), requestCode);
                    event.setTaskList(task);

                    XMLSerialization(event);
                } else {
                    Task task = new Task(time, taskEditText.getText().toString(), requestCode);
                    Event event = new Event(date, task);

                    XMLSerialization(event);
                }

                //startService();

                Toast.makeText(AddEventActivity.this, "Event Saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddEventActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void XMLSerialization(Event event) {
        try {
            File xmlFile = new File(getFilesDir() + File.separator + date + ".xml");
            xmlFile.createNewFile();

            try {
                Serializer serializer = new Persister();
                serializer.write(event, xmlFile);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Event XMLDeserialization(File xmlFile) {
        Event event = new Event();

        try{
            Serializer serializer = new Persister();
            event = serializer.read(Event.class, xmlFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return event;
    }

    private void startAlarm(Calendar c, int requestCode, String time, String task) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("time", time);
        intent.putExtra("task", task);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
}
