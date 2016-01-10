package com.example.george.soundboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ChooseElement extends AppCompatActivity {

    public static int imageID;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private ImageButton image;
    private AudioCap audio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_element);

        final int eleId = SetActivity.id;
        final BluePrint bluePrint = SetActivity.bluePrint[eleId];

        // Initial set up of instances goes here TODO: Decide if needed
        if(!bluePrint.eleSet)
        {

            bluePrint.eleSet = true;
        }


        audio = bluePrint.audio;

        Button set = (Button) findViewById(R.id.set);
        image = (ImageButton) findViewById(R.id.image);

        //TODO: Change image revolution by converting to BitMap
        //try { BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri)); }
        //catch (IOException e) {}

        if(SetActivity.bluePrint[SetActivity.id].imageSet)
        {
            image.setImageURI(SetActivity.bluePrint[SetActivity.id].image);
        }

        final Button record = (Button) findViewById(R.id.record);
        final Button play = (Button) findViewById(R.id.play);

        final Intent intent = new Intent(this, MainActivity.class);


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                // start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);



            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.clickPlay(play);
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.clickRecord(record);
            }
        });


    }

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "SoundBoard");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.


        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {
                Log.d("Photos", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");

                try {
                    BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
                }catch(IOException e)
                {
                    Log.e("BitMap","Failed to convert to BitMap: " + e);
                }
                SetActivity.bluePrint[SetActivity.id].image = fileUri;

                image.setImageURI(SetActivity.bluePrint[SetActivity.id].image);
                Log.v("ImageSet",SetActivity.bluePrint[SetActivity.id].image.toString());
                SetActivity.bluePrint[SetActivity.id].imageSet = true;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result

            }
        }
    }


}
