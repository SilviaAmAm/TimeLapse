package com.example.timelapse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;

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
        EditText duration_time_input = (EditText) findViewById(R.id.duraton_t);
        EditText photos_dt_input = (EditText) findViewById(R.id.dt);

        // Get their values as integers (in seconds)
        int duration_time = Integer.parseInt(duration_time_input.getText().toString());
        int photos_dt = Integer.parseInt(photos_dt_input.getText().toString());

        // Attach the message to the intent
        intent.putExtra("DURATION", duration_time);
        intent.putExtra("DELAY", photos_dt);

        startActivity(intent);

    }

}
