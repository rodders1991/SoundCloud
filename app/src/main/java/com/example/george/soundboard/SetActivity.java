package com.example.george.soundboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by George on 07/01/2016.
 */
public class SetActivity extends Fragment {

    private static boolean bluePrintSet;
    public static boolean[] eleInstanceCreated;
    public static BluePrint[] bluePrint;
    public static AudioCap[] audio;
    public static int id;
    public static int[] ids;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_set, container, false);

        ImageButton[] images = new ImageButton[7];


        // Intial setUp of app goes here
        if(!bluePrintSet)
        {
            bluePrintSet = true;
            ids = new int[7];
            audio = new AudioCap[7];
            eleInstanceCreated = new boolean[7];
            bluePrint = new BluePrint[7];

            for(int i=0; i < bluePrint.length; i++) bluePrint[i] = new BluePrint(i);

        }

        images[0] = (ImageButton) rootView.findViewById(R.id.image1);
        images[1] = (ImageButton) rootView.findViewById(R.id.image2);
        images[2] = (ImageButton) rootView.findViewById(R.id.image3);
        images[3] = (ImageButton) rootView.findViewById(R.id.image4);
        images[4] = (ImageButton) rootView.findViewById(R.id.image5);
        images[5] = (ImageButton) rootView.findViewById(R.id.image6);
        images[6] = (ImageButton) rootView.findViewById(R.id.image7);



        for(int i = 0; i < ids.length; i++)
        {
            if(!bluePrint[i].imageSet) {
                images[i].setImageResource(R.mipmap.dashed);
            }
            else
            {
                images[i].setImageURI(bluePrint[i].image);
            }
        }

        final Intent intent = new Intent(getActivity(), ChooseElement.class);

        for (int i = 0; i < images.length; i++) {
            final int p = i;
            images[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id = p;
                    startActivity(intent);
                }
            });
        }

        return rootView;

    }
}
