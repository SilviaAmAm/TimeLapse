package com.example.timelapse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class DisplayCheckedInputs extends AppCompatActivity {

    String TAG = "DisplayCheckedInputs";
    int checkedDelay;
    int checkedDuration;
    int nPhotosToTake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_checked_inputs);

        // Get the Intent that started this activity and extract the parameters
        Bundle extras = getIntent().getExtras();

        try {
            checkedDuration = extras.getInt("DURATION");
            checkedDelay = extras.getInt("DELAY");
        } catch (NullPointerException e) {
            Log.d(TAG, "Could not extract parameters: " + e);
        }

        // Calculate the number of photos that will be taken
        nPhotosToTake = (int)(checkedDuration / checkedDelay);

        // Calculate how long a video at 30 fps would be
        double video_length = nPhotosToTake / 30.0;

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(
                getResources().getString(R.string.confirm_input_txt, nPhotosToTake, video_length)
        );
    }

    public void confirmInputs(View view){

        Intent startCamera = new Intent(this, CameraActivity.class);

        startCamera.putExtra("N_PHOTOS", nPhotosToTake);
        startCamera.putExtra("DELAY", checkedDelay);
        startActivity(startCamera);
    }
}
