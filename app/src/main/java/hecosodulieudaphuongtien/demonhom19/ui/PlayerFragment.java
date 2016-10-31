package hecosodulieudaphuongtien.demonhom19.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.w3c.dom.Text;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.mediaplayer.MyPlayer;
import hecosodulieudaphuongtien.demonhom19.model.Audio;

/**
 * Created by admin on 10/30/2016.
 */
@SuppressLint("ValidFragment")
public class PlayerFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public Toolbar toolbar;
    public ImageView btnPlay, btnRate, btnDownload, ivAvatar;
    public SeekBar seekBar;
    public Audio audioPlaying;
    private MainActivity activity;
    private Handler mHandler;
    private TextView currentTime, totalTime;

    public PlayerFragment(Audio audioPlaying) {
        this.audioPlaying = audioPlaying;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (MainActivity) getActivity();
        mHandler = new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_detail, container, false);
        init(rootView);
        updateSeekBar();
        return rootView;
    }

    private void init(View rootView) {
        currentTime = (TextView) rootView.findViewById(R.id.tvCurrent);
        totalTime = (TextView) rootView.findViewById(R.id.tvTotal);
        btnPlay = (ImageView) rootView.findViewById(R.id.play);
        btnRate = (ImageView) rootView.findViewById(R.id.rate);
        btnDownload = (ImageView) rootView.findViewById(R.id.download);
        ivAvatar = (ImageView) rootView.findViewById(R.id.iv_avatar);
        seekBar = (SeekBar) rootView.findViewById(R.id.seekbar);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(audioPlaying.title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();

            }
        });
        btnPlay.setOnClickListener(this);
        btnRate.setOnClickListener(this);
        btnDownload.setOnClickListener(this);

        if (MyPlayer.getInstance().isIsPlaying()) {
            btnPlay.setImageResource(R.drawable.play_circle_detail);
        } else {
            btnPlay.setImageResource(R.drawable.pause_detail);
        }
        Glide.with(this).load(audioPlaying.singer.urlAvatar).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivAvatar) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                ivAvatar.setImageDrawable(circularBitmapDrawable);
            }
        });
        totalTime.setText(getStringTime(audioPlaying.getAudioLength()));
        seekBar.setOnSeekBarChangeListener(this);
    }

    public void updateSeekBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.play:
                if (MyPlayer.getInstance().isIsPlaying()) {
                    MyPlayer.getInstance().onPause();
                    btnPlay.setImageResource(R.drawable.play_circle_detail);
                } else {
                    MyPlayer.getInstance().onResume();
                    btnPlay.setImageResource(R.drawable.pause_detail);

                }
                break;
            case R.id.rate:
                break;
            case R.id.download:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int percent = seekBar.getProgress();
        MyPlayer.getInstance().seekTo(percent);
        updateSeekBar();
    }

    private Runnable mUpdateTimeTask = new Runnable() {

        public void run() {

            int totalDuration = MyPlayer.getInstance().getDuration();
            int currentDuration = MyPlayer.getInstance().getCurrentPosition();
            currentTime.setText(getStringTime(currentDuration));
            int progress = (int) (getProgressPercentage(currentDuration, totalDuration));
            seekBar.setProgress(progress);

            mHandler.postDelayed(this, 100);
        }
    };

    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);
        percentage = (((double) currentSeconds) / totalSeconds) * 100;
        return percentage.intValue();
    }

    private String getStringTime(int duration) {
        String newTime;
        long totalSec = duration / 1000;
        long minute = totalSec / 60;
        long sec = totalSec % 60;
        if (minute > 100) return ("--:--");
        if (minute < 10) {
            if (sec < 10) {
                newTime = "0" + minute + ":0" + sec;
                return newTime;
            } else {
                newTime = "0" + minute + ":" + sec;
                return newTime;
            }
        } else {
            if (sec < 10) {
                newTime = minute + ":0" + sec;
                return newTime;
            } else {
                newTime = minute + ":" + sec;
                return newTime;
            }
        }


    }
}
