package com.example.george.soundboard;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

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




        if(SetActivity.bluePrint[SetActivity.id].imageSet)
        {
            Picasso.with(image.getContext()).load(SetActivity.bluePrint[SetActivity.id].image).resize(300,300).centerCrop().into(image);
            image.setBackgroundResource(0);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        final Button record = (Button) findViewById(R.id.record);
        final Button play = (Button) findViewById(R.id.play);
        final Button preview = (Button) findViewById(R.id.preview);

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audio.isSet) audio.start();
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.clickRecord(record);
            }
        });

        play.setVisibility(View.GONE);

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

                Intent pickIntent = new Intent();
                pickIntent.setType("image/*");
                pickIntent.setAction(Intent.ACTION_GET_CONTENT);



                String pickTitle = "Select or take a new Picture"; // Or get from strings.xml
                Intent chooserIntent = Intent.createChooser(intent, pickTitle);




                chooserIntent.putExtra
                        (
                                Intent.EXTRA_INITIAL_INTENTS,
                                new Intent[] { pickIntent }
                        );

                // start the image capture Intent
                startActivityForResult(chooserIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);



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


                File i = new File(fileUri.getPath());

                if(!i.exists())
                {

                    SetActivity.bluePrint[SetActivity.id].image = data.getData();
                }else
                {
                    SetActivity.bluePrint[SetActivity.id].image = fileUri;
                }

                Picasso.with(image.getContext()).load(SetActivity.bluePrint[SetActivity.id].image).resize(300,300).centerCrop().into(image);
                image.setBackgroundResource(0);
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                Log.v("ImageSet", SetActivity.bluePrint[SetActivity.id].image.toString());
                SetActivity.bluePrint[SetActivity.id].imageSet = true;







            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result

            }
        }
    }



}
