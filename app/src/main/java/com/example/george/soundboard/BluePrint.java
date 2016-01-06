package com.example.george.soundboard;

import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.ImageButton;

/**
 * Created by George on 28/12/2015.
 */
public class BluePrint {
    public boolean eleSet;
    public AudioCap audio;
    public Uri image;
    public boolean imageSet;

    public BluePrint (int index)
    {
        imageSet = false;
        audio = new AudioCap(index);
        eleSet = false;
    }


}
