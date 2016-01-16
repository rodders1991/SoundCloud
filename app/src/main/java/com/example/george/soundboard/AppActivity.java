package com.example.george.soundboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

/**
 * Created by George on 07/01/2016.
 */
public class AppActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_app, container, false);

        ImageButton[] images = new ImageButton[7];

        images[0] = (ImageButton) rootView.findViewById(R.id.image1);
        images[1] = (ImageButton) rootView.findViewById(R.id.image2);
        images[2] = (ImageButton) rootView.findViewById(R.id.image3);
        images[3] = (ImageButton) rootView.findViewById(R.id.image4);
        images[4] = (ImageButton) rootView.findViewById(R.id.image5);
        images[5] = (ImageButton) rootView.findViewById(R.id.image6);
        images[6] = (ImageButton) rootView.findViewById(R.id.image7);

        final BluePrint[] bluePrint = SetActivity.bluePrint;

        for(int i = 0; i < bluePrint.length; i++)
        {
            if(bluePrint[i].imageSet) {
                Picasso.with(images[i].getContext()).load(bluePrint[i].image).resize(300, 300).centerCrop().into(images[i]);
            }

            if(bluePrint[i].audio.isSet)
            {
                bluePrint[i].audio.prepare();
            }

        }

        for (int i = 0; i < images.length; i++) {
            final int p = i;
            images[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(bluePrint[p].audio.isSet) {
                        bluePrint[p].audio.start();
                    }

                }
            });
        }

        return rootView;
    }
}
