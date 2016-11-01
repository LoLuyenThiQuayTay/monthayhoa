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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.w3c.dom.Text;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.mediaplayer.MyPlayer;
import hecosodulieudaphuongtien.demonhom19.model.Audio;
import hecosodulieudaphuongtien.demonhom19.sqlite.DownloadAudio;

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
    private TextView currentTime, totalTime, tvViewCoung, tvRate, tvSingerName, btnDiscard, btnOK;
    private LinearLayout contentCount;
    private RelativeLayout background;
    private RatingBar ratingBar;
    private ProgressBar progressBar;

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
        activity.btn.setVisibility(View.GONE);
        return rootView;
    }

    private void init(View rootView) {
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        background = (RelativeLayout) rootView.findViewById(R.id.backgroundRate);
        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        contentCount = (LinearLayout) rootView.findViewById(R.id.countContent);
        tvRate = (TextView) rootView.findViewById(R.id.tvRateCount);
        tvViewCoung = (TextView) rootView.findViewById(R.id.tvViewCount);
        currentTime = (TextView) rootView.findViewById(R.id.tvCurrent);
        totalTime = (TextView) rootView.findViewById(R.id.tvTotal);
        btnPlay = (ImageView) rootView.findViewById(R.id.play);
        btnRate = (ImageView) rootView.findViewById(R.id.rate);
        btnOK = (TextView) rootView.findViewById(R.id.btnOK);
        btnDiscard = (TextView) rootView.findViewById(R.id.btnDiscard);
        btnDownload = (ImageView) rootView.findViewById(R.id.download);
        ivAvatar = (ImageView) rootView.findViewById(R.id.iv_avatar);
        seekBar = (SeekBar) rootView.findViewById(R.id.seekbar);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        tvSingerName = (TextView) rootView.findViewById(R.id.tvSingerName);
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
        if (audioPlaying.listPart.get(0).url == null) {
            btnDownload.setVisibility(View.GONE);
        } else {
            btnDownload.setVisibility(View.VISIBLE);

        }
        tvSingerName.setText("Singer: " + audioPlaying.singer.name);
        if (audioPlaying.listPart.get(0).url == null) {
            contentCount.setVisibility(View.GONE);
        } else {
            contentCount.setVisibility(View.VISIBLE);
            tvViewCoung.setText(" " + audioPlaying.viewCount);
            tvRate.setText(" " + audioPlaying.rate);
        }

        if (MyPlayer.getInstance().isIsPlaying()) {
            btnPlay.setImageResource(R.drawable.pause_detail);
        } else {
            btnPlay.setImageResource(R.drawable.play_circle_detail);
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
        background.setOnClickListener(this);
        btnDiscard.setOnClickListener(this);
        btnOK.setOnClickListener(this);
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
                background.setVisibility(View.VISIBLE);
                break;
            case R.id.download:
                DownloadAudio downloadAudio = new DownloadAudio(activity, audioPlaying);
                downloadAudio.execute();
                break;
            case R.id.backgroundRate:
                background.setVisibility(View.GONE);
                break;
            case R.id.btnDiscard:
                background.setVisibility(View.GONE);
                break;
            case R.id.btnOK:
                float rate = ratingBar.getRating();
                progressBar.setVisibility(View.VISIBLE);
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
            if (MyPlayer.getInstance().isIsPlaying()) {
                btnPlay.setImageResource(R.drawable.pause_detail);
            } else {
                btnPlay.setImageResource(R.drawable.play_circle_detail);
            }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.btn.setVisibility(View.VISIBLE);
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
