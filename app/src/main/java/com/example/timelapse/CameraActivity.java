package com.example.timelapse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.content.pm.PackageInfoCompat;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    private Camera mCamera;
    private CameraPreview mPreview;
    String TAG = "CameraActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Bundle extras = getIntent().getExtras();

        // Get extras
        int nPhotosToTake = 10;
        int photosDt = 10;

        try {
            nPhotosToTake = extras.getInt("N_PHOTOS");
            photosDt = extras.getInt("DELAY");
        } catch (NullPointerException e) {
            Log.d(TAG, "Could extract parameters: " + e);
        }

        final int checkedNPhotos = nPhotosToTake;
        final int checkedPhotosDt = photosDt;

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        Button captureButton = findViewById(R.id.button_capture);

        captureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                PictureCallback picCallback = createPictureCallback();

                for (int photoCount = 0; photoCount < checkedNPhotos; photoCount++ ) {

                    mCamera.takePicture(null, null, picCallback);
                    Log.d(TAG, "Number of pictures taken: " + (photoCount+1));

                    // Give the effect of stopping (essential, otherwise callback doesnt work)
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Log.d(TAG, "Error with sleep: " + e);
                    }
                    mCamera.startPreview();

                    // Wait for delay
                    try {
                        Thread.sleep((checkedPhotosDt*1000-500));
                    } catch (InterruptedException e) {
                        Log.d(TAG, "Error with sleep: " + e);
                    }
                }
            }
        });
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            Log.d("CameraActivity", "Cant open camera.");
        }
        return c; // returns null if camera is unavailable
    }

    PictureCallback createPictureCallback(){

        PictureCallback picture = new PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                // Create an output file that will contain the photo
                File photoFile = null;

                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    // Error occurred while creating the File
                    Log.d(TAG, "Could not create a new image file: " + e);
                }

                if (photoFile != null) {
                    try {
                        FileOutputStream photoFileOutput = new FileOutputStream(photoFile);
                        photoFileOutput.write(data);
                        photoFileOutput.close();
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "Could not bind the output stream with the file created: " + e);
                    } catch (IOException e) {
                        Log.d(TAG, "Could not bind the output stream with the file created: " + e);
                    }
                }

            }
        };

        return picture;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }
}

