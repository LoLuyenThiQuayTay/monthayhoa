package hecosodulieudaphuongtien.demonhom19.mediaplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

import hecosodulieudaphuongtien.demonhom19.model.Audio;

/**
 * Created by Z170A Gaming M7 on 10/28/2016.
 */
public class PlayerPart implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    public MediaPlayer player;
    public int position;
    public Audio.AudioPart playerPart;
    public OnCompletedPart listener;

    public PlayerPart(Audio.AudioPart audioPart, int position, OnCompletedPart listener) {
        this.listener = listener;
        this.playerPart = audioPart;
        this.position = position;
        player = new MediaPlayer();
        player.reset();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            player.setDataSource(audioPart.url);
            player.setOnPreparedListener(this);
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.setOnCompletionListener(this);
    }

    public PlayerPart(String title, Context context, OnCompletedPart listener) {
        this.listener = listener;
        player = new MediaPlayer();
        player.reset();
        String mediaPath = "sdcard/Music/" + title + ".mp3";
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Uri uri = Uri.parse(mediaPath);
        try {
            player.setDataSource(context, uri);
            player.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPause() {
        player.pause();
    }

    public void onResume() {
        player.start();
    }

    public void onReset() {
        player.reset();
    }

    public void onPrepare() {
        player.prepareAsync();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if (position == MyPlayer.partPlaying)
            player.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        listener.onCompletedPart(position);
    }
}
