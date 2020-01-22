package com.example.timelapse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayCheckedInputs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_checked_inputs);

        // Get the Intent that started this activity and extract the message string
        Bundle extras = getIntent().getExtras();
        String message = extras.getString("CHECKED_INPUT_MSG");

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }

    public void confirmInputs(View view){
        Intent intent = new Intent(this, StartTakingPhotos.class);
        startActivity(intent);
    }
}
