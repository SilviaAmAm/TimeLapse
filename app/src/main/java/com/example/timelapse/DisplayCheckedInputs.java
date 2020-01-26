package com.example.timelapse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class DisplayCheckedInputs extends AppCompatActivity {

    String TAG = "DisplayCheckedInputs";
    int checkedDuration = 1000;
    int checkedDelay = 10;
    int nPhotosToTake = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_checked_inputs);

        // Get the Intent that started this activity and extract the parameters
        Bundle extras = getIntent().getExtras();

        int duration = 0;
        int photosDt = 0;
        try {
            duration = extras.getInt("DURATION");
            photosDt = extras.getInt("DELAY");
        } catch (NullPointerException e) {
            Log.d(TAG, "Could extract parameters: " + e);
        }

        // Check the inputs
        if (photosDt > 0 || duration >= photosDt) {
            checkedDelay = photosDt;
            checkedDuration = duration;
        }

        // Calculate the number of photos that will be taken
        nPhotosToTake = (int)(checkedDuration / checkedDelay);

        // Calculate how long a video at 30 fps would be
        double video_length = nPhotosToTake / 30.0;

        // Create the message
        String checked_inputs_msg = String.format(
                "This will take %d photos, which corresponds to a %.1f s long video at 30 fps.",
                nPhotosToTake,
                video_length
        );

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(checked_inputs_msg);
    }

    public void confirmInputs(View view){

        Intent startCamera = new Intent(this, CameraActivity.class);

        startCamera.putExtra("N_PHOTOS", nPhotosToTake);
        startCamera.putExtra("DELAY", checkedDelay);
        startActivity(startCamera);
    }
}
