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
import hecosodulieudaphuongtien.demonhom19.mediaplayer.MyPlayer;
import hecosodulieudaphuongtien.demonhom19.mediaplayer.PlayerPart;
import hecosodulieudaphuongtien.demonhom19.model.Audio;
import hecosodulieudaphuongtien.demonhom19.model.Singer;
import hecosodulieudaphuongtien.demonhom19.sqlite.DataSource;
import hecosodulieudaphuongtien.demonhom19.sqlite.DownloadAudio;

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

        adapter.updateData(DataSource.getAudiosDownloaded());
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
        MyPlayer.getInstance().playOffline(adapter.getListData().get(position));
        activity.replaceFragmentUp(new PlayerFragment(MyPlayer.getInstance().getAudioPlaying()));

//        Audio audio = new Audio();
//        audio.title = "Chuyện của mùa đông";
//        ArrayList<Audio.AudioPart> listURL = new ArrayList<>();
//        listURL.add(new Audio.AudioPart(1, "http://api.taplifeapp.com:6969/Uploads/chuyen%20cua%20mua%20dong%20part1.mp3", "02:10"));
//
//        listURL.add(new Audio.AudioPart(1, "http://api.taplifeapp.com:6969/Uploads/chuyen%20cua%20mua%20dong%20part2.mp3", "03:45"));
//
//        audio.listPart = listURL;
//        audio.singer = new Singer("Hà Anh Tuấn");
//        DownloadAudio downloadAudio = new DownloadAudio((MainActivity) getActivity(), audio);
//        downloadAudio.execute();

//        MyPlayer.getInstance().playOnline(audio);
//        activity.replaceFragmentUp(new PlayerFragment(audio));

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
