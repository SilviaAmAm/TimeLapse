package com.example.timelapse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Check Inputs button */
    public void checkInputs(View view) {

        Intent intent = new Intent(this, DisplayCheckedInputs.class);

        // Get the input fields
        EditText duration_time_input = findViewById(R.id.duration_t);
        EditText photos_dt_input = findViewById(R.id.dt);

        // Get their values as integers (in seconds)
        String durationTimeStr = duration_time_input.getText().toString();
        String photosDtStr = photos_dt_input.getText().toString();

        if (durationTimeStr.length() == 0){
            duration_time_input.setError("Cannot be empty");
            return;
        } else if (photosDtStr.length() == 0){
            photos_dt_input.setError("Cannot be empty");
            return;
        }

        int duration_time = Integer.parseInt(duration_time_input.getText().toString());
        int photos_dt = Integer.parseInt(photos_dt_input.getText().toString());

        // Check the inputs
        if (duration_time <= photos_dt) {
            photos_dt_input.setError("Cannot be larger than the duration");
            return;
        } else if (photos_dt == 0) {
            photos_dt_input.setError("Cannot be zero");
            return;
        }

        // Attach the message to the intent
        intent.putExtra("DURATION", duration_time);
        intent.putExtra("DELAY", photos_dt);

        startActivity(intent);
    }

}
