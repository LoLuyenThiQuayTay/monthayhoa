package hecosodulieudaphuongtien.demonhom19.mediaplayer;

import android.util.Log;
import android.view.View;

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

    public static ArrayList<PlayerPart> listPlayer;

    private static MyPlayer instance;
    private static Audio audioPlaying;

    private MyPlayer() {
    }

    public static Audio getAudioPlaying() {
        return audioPlaying;
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
        audioPlaying = audio;
        listPlayer = new ArrayList<>();
        audioPlaying.partPlaying = 0;
        for (int i = 0; i < audio.listPart.size(); i++) {
            PlayerPart playerPart = new PlayerPart(audio.listPart.get(i), i, this);
            listPlayer.add(playerPart);
        }
        audioPlaying.updatePartPercent();
    }

    public boolean isIsPlaying() {
        return listPlayer.get(audioPlaying.partPlaying).player.isPlaying();
    }

    public void playOffline(Audio audio) {

//        if (viewPlayer.getVisibility() != View.VISIBLE) {
//            viewPlayer.setVisibility(View.VISIBLE);
//        }
        listPlayer = new ArrayList<>();
        audioPlaying = audio;
        audioPlaying.partPlaying = 0;
        PlayerPart playerPart = new PlayerPart(audio.title, activity, this);
        listPlayer.add(playerPart);

        audioPlaying.updatePartPercent();
    }

    public void seekForward() {

    }

    public void onPause() {
        listPlayer.get(audioPlaying.partPlaying).onPause();
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
        listPlayer.get(audioPlaying.partPlaying).onResume();
    }

    public void seekBackward() {
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_play:
                if (isPlaying) {
                    listPlayer.get(audioPlaying.partPlaying).onPause();
                } else {
                    listPlayer.get(audioPlaying.partPlaying).onResume();
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
            audioPlaying.partPlaying++;
        } else {
            audioPlaying.partPlaying = 0;
        }
    }

    public int getDuration() {
        return audioPlaying.getAudioLength();
    }

    public int getCurrentPosition() {
        int total = audioPlaying.getAudioLength();
        int currentPosition = 0;
        int partPlayingPosition = audioPlaying.partPlaying;
        if (partPlayingPosition == 0) {
            currentPosition = listPlayer.get(0).player.getCurrentPosition();
        } else {
            for (int i = 0; i < listPlayer.size(); i++) {
                if (i < audioPlaying.partPlaying) {
                    currentPosition = currentPosition + audioPlaying.listPart.get(i).getLengthFromString();
                } else if (i == audioPlaying.partPlaying) {
                    currentPosition = currentPosition + listPlayer.get(i).player.getCurrentPosition();
                }
            }
        }
        return currentPosition;
    }

    public int getPositionPartPlaying() {
        return audioPlaying.partPlaying;
    }

    public int currentPositionAudioToCurrentPositionPart(int percent) {
        int currentDuration = audioPlaying.getCurrentPositionAudio(percent);
        int currentPartPosition = audioPlaying.findPositionPartPlaying(percent);

        int durationPlayed = 0;
        for (int i = 0; i < audioPlaying.listPart.size(); i++) {
            if (i < currentPartPosition) {
                durationPlayed += audioPlaying.listPart.get(i).getLengthFromString();
            }
        }
        return currentDuration - durationPlayed;
    }

    public void seekTo(int percent) {
        int currentPartPosition = audioPlaying.findPositionPartPlaying(percent);
        if (currentPartPosition != audioPlaying.partPlaying) {
            listPlayer.get(audioPlaying.partPlaying).onPause();
            audioPlaying.partPlaying = currentPartPosition;
            listPlayer.get(currentPartPosition).onResume();
        }
        Log.e("AA", "seekTo: currentPartPosition " + currentPartPosition + " ---" + currentPositionAudioToCurrentPositionPart(percent));
        listPlayer.get(currentPartPosition).player.seekTo(currentPositionAudioToCurrentPositionPart(percent));

    }
}
