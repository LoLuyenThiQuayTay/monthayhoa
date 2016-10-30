package hecosodulieudaphuongtien.demonhom19.mediaplayer;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.model.Audio;
import hecosodulieudaphuongtien.demonhom19.ui.MainActivity;

/**
 * Created by admin on 3/29/2016.
 */
public class MyPlayer implements View.OnClickListener, OnCompletedPart {
    private static MainActivity activity;

    public static boolean isPlaying = false;
    public static int partPlaying = 0;

    public static ArrayList<PlayerPart> listPlayer;

    private static MyPlayer instance;

    private MyPlayer() {
    }

    public static void initIfNeed(MainActivity mactivity) {
        activity = mactivity;
    }

    public static MyPlayer getInstance() {
        if (instance == null) {
            instance = new MyPlayer();
        }
        return instance;
    }

    public void playOnline(Audio audio) {

//        if (viewPlayer.getVisibility() != View.VISIBLE) {
//            viewPlayer.setVisibility(View.VISIBLE);
//        }
        onReset();
        listPlayer = new ArrayList<>();
        partPlaying = 0;
        for (int i = 0; i < audio.listPart.size(); i++) {
            PlayerPart playerPart = new PlayerPart(audio.listPart.get(i), i, this);
            listPlayer.add(playerPart);
        }

    }

    public boolean isIsPlaying() {
        return listPlayer.get(partPlaying).player.isPlaying();
    }

    public void playOffline(String title) {

//        if (viewPlayer.getVisibility() != View.VISIBLE) {
//            viewPlayer.setVisibility(View.VISIBLE);
//        }
        listPlayer = new ArrayList<>();
        partPlaying = 0;
        PlayerPart playerPart = new PlayerPart(title, activity, this);
        listPlayer.add(playerPart);

    }

    public void seekForward() {

    }

    public void onPause() {
        listPlayer.get(partPlaying).onPause();
    }

    public void onReset() {
        try {
            for (int i = 0; i < listPlayer.size(); i++) {
                listPlayer.get(i).onReset();
            }
        } catch (Exception e) {

        }

    }

    public void onResume() {
        listPlayer.get(partPlaying).onResume();
    }

    public void seekBackward() {
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_play:
                if (isPlaying) {
                    listPlayer.get(partPlaying).onPause();
                } else {
                    listPlayer.get(partPlaying).onResume();
                }
                break;
            case R.id.btn_backward:
                seekBackward();
                break;
            case R.id.btn_forward:
                seekForward();
                break;
        }
    }


    @Override
    public void onCompletedPart(int position) {
        if (position < listPlayer.size() - 1) {
            listPlayer.get(position + 1).onResume();
            partPlaying++;
        } else {
            partPlaying = 0;
        }
    }
}
