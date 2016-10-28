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
    private MainActivity activity;

    private TextView tv_Title;
    private LinearLayout btnPlay, btnForward, btnBackward;
    private ImageView iconPlay;
    private RelativeLayout viewPlayer;
    public static boolean isPlaying = false;
    public static int partPlaying = 0;

    public static ArrayList<PlayerPart> listPlayer;

    public MyPlayer(MainActivity activity) {
        this.activity = activity;
        init();
    }

    public void init() {
        tv_Title = (TextView) activity.findViewById(R.id.tv_title);
        iconPlay = (ImageView) activity.findViewById(R.id.iv_play);
        btnBackward = (LinearLayout) activity.findViewById(R.id.btn_backward);
        btnForward = (LinearLayout) activity.findViewById(R.id.btn_forward);
        btnPlay = (LinearLayout) activity.findViewById(R.id.btn_play);
//        viewPlayer = (RelativeLayout) activity.findViewById(R.id.player);
//        viewPlayer.setVisibility(View.GONE);
        btnPlay.setOnClickListener(this);
        btnBackward.setOnClickListener(this);
        btnForward.setOnClickListener(this);
    }

    public void playOnline(Audio audio) {

//        if (viewPlayer.getVisibility() != View.VISIBLE) {
//            viewPlayer.setVisibility(View.VISIBLE);
//        }
        tv_Title.setText(audio.title);
        iconPlay.setBackgroundResource(R.drawable.pause_circle);

        listPlayer = new ArrayList<>();
        partPlaying = 0;
        for (int i = 0; i < audio.listRealUrl.size(); i++) {
            PlayerPart playerPart = new PlayerPart(audio.getURL(i), i, this);
            listPlayer.add(playerPart);
        }
//        listPlayer.get(0).onPrepare();

    }

    public void playOffline(String title) {

//        if (viewPlayer.getVisibility() != View.VISIBLE) {
//            viewPlayer.setVisibility(View.VISIBLE);
//        }
        tv_Title.setText(title);
        iconPlay.setBackgroundResource(R.drawable.pause_circle);
        listPlayer = new ArrayList<>();
        partPlaying = 0;
        PlayerPart playerPart = new PlayerPart(title, activity, this);
        listPlayer.add(playerPart);

    }

    public void seekForward() {

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
                    iconPlay.setBackgroundResource(R.drawable.play_circle);
                } else {
                    listPlayer.get(partPlaying).onResume();

                    iconPlay.setBackgroundResource(R.drawable.pause_circle);
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
        }
    }
}
