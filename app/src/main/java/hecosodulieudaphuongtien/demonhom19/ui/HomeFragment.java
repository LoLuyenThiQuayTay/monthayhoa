package hecosodulieudaphuongtien.demonhom19.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.adapter.AudioDownloadedAdapter;
import hecosodulieudaphuongtien.demonhom19.model.Audio;
import hecosodulieudaphuongtien.demonhom19.model.Singer;
import hecosodulieudaphuongtien.demonhom19.sqlite.DataSource;

/**
 * Created by admin on 3/24/2016.
 */
public class HomeFragment extends Fragment implements AudioDownloadedAdapter.OnClickItemRecyclerView, MediaPlayer.OnPreparedListener {
    private RecyclerView listView;
    private ArrayList<Audio> listAudios = new ArrayList<>();
    private AudioDownloadedAdapter adapter;
    private MainActivity activity;
    private Toolbar toolbar;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        listAudios = DataSource.getAudiosDownloaded();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        listView = (RecyclerView) rootView.findViewById(R.id.listView);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        adapter = new AudioDownloadedAdapter(activity, this);
        listView.setLayoutManager(new LinearLayoutManager(activity));
        listView.setAdapter(adapter);
        toolbar.setTitle("Home");
        toolbar.setNavigationIcon(R.drawable.search);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.replaceFragmentUp(new SearchFragment());
            }
        });
        ArrayList<Audio> listAudio = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Audio audio = new Audio();
            audio.title = "Fallin'";
            audio.singer = new Singer("Alicia Keys");
            listAudio.add(audio);
        }

        adapter.updateData(listAudio);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public void deleteAudio(Audio audio) {
        DataSource.deleteAudio(audio);
        listAudios = DataSource.getAudiosDownloaded();
        adapter.updateData();
    }

    MediaPlayer player;

    @Override
    public void onClickItem(int position) {
        Audio audio = new Audio();
        audio.title = "Test";
        ArrayList<String> listURL = new ArrayList<>();
        listURL.add("http://api.taplifeapp.com:6969/Uploads/1.mp3");
        listURL.add("http://api.taplifeapp.com:6969/Uploads/2.mp3");
        audio.listRealUrl = listURL;
        activity.getPlayerController().playOnline(audio);

//        player = new MediaPlayer();
//        player.reset();
//        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        try {
//            player.setDataSource("http://api.taplifeapp.com:6969/Uploads/2.mp3");
//            player.setOnPreparedListener(this);
//            player.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        player.start();
    }
}
