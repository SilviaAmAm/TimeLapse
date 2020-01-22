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

        // Check that photos_dt_input is not zero // TODO fix this
        if( photos_dt_input.getText().toString() == "0" )
            photos_dt_input.setError( "This field cannot be zero" );

        // Get their values as integers/double and convert minutes to h
        int duration_time = Integer.parseInt(duration_time_input.getText().toString());
        double photos_dt = Integer.parseInt(photos_dt_input.getText().toString()) / 60.0;

        // Calculate the number of photos that will be taken
        int n_photos_to_take = (int)(duration_time / photos_dt);

        // Calculate the memory size that the photos will occupy
//        String filepath = Environment.getExternalStorageDirectory() + "/test.png";
//        File file = new File(filepath);
//        double size_of_photo = file.length() * 0.000001; // Size in Mb
//
//        double memory_size = n_photos_to_take * size_of_photo;
//
        // Calculate how long a video at 30 fps would be
        double video_length = n_photos_to_take / 30.0;

        // Create the message
        String checked_inputs_msg = String.format("This will take %d photos, which corresponds to a %.1f s long video at 30 fps.", n_photos_to_take, video_length);

        // Attach the message to the intent
        intent.putExtra("CHECKED_INPUT_MSG", checked_inputs_msg);

        startActivity(intent);

    }

}
