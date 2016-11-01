package hecosodulieudaphuongtien.demonhom19.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.adapter.AudioAdapter;
import hecosodulieudaphuongtien.demonhom19.mediaplayer.MyPlayer;
import hecosodulieudaphuongtien.demonhom19.model.Audio;
import hecosodulieudaphuongtien.demonhom19.model.AudioOfSingerResponse;
import hecosodulieudaphuongtien.demonhom19.model.Singer;
import hecosodulieudaphuongtien.demonhom19.model.Utils;
import hecosodulieudaphuongtien.demonhom19.sqlite.DownloadAudio;
import hecosodulieudaphuongtien.demonhom19.webservice.ServiceManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 3/24/2016.
 */
@SuppressLint("ValidFragment")
public class DetailSingerFragment extends Fragment implements AudioAdapter.OnClickItemRecyclerView, Callback<ResponseBody> {
    private final String URL_GET_ALL_AUDIOS = Utils.BASE_URL + "hecsdldpt19/android_connect/get_all_audios.php";

    private ArrayList<Audio> listAudios = new ArrayList<>();
    private MainActivity activity;
    private ImageView ivAvatar;
    private TextView tvProFile;
    private RecyclerView recyclerAudio;
    private GridLayoutManager manager;
    private AudioAdapter adapter;
    private Singer singer;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public DetailSingerFragment(Singer singer) {
        this.singer = singer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_singer, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorAccent, R.color.colorPrimary);
        ivAvatar = (ImageView) rootView.findViewById(R.id.ivAvatar);
        tvProFile = (TextView) rootView.findViewById(R.id.tvProfile);
        recyclerAudio = (RecyclerView) rootView.findViewById(R.id.listAudio);
        listAudios = new ArrayList<>();
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(singer.name);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });
        manager = new GridLayoutManager(getContext(), 1);
        recyclerAudio.setLayoutManager(manager);
        adapter = new AudioAdapter(getContext(), this);
        recyclerAudio.setAdapter(adapter);
        tvProFile.setText(singer.profile);
        Glide.with(this).load(singer.urlAvatar).into(ivAvatar);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClickItem(Audio audio) {
        MyPlayer.getInstance().playOnline(audio);
        activity.replaceFragment(new PlayerFragment(MyPlayer.getAudioPlaying()));
    }

    @Override
    public void onClickSubItem(Audio audio) {
        DownloadAudio downloadAudio = new DownloadAudio(activity,audio,null);
        downloadAudio.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAudio();
    }

    private void loadAudio() {
        swipeRefreshLayout.setRefreshing(true);
        ServiceManager.getServerInstance().searchAudioByIDSinger(singer.id).enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        swipeRefreshLayout.setRefreshing(false);
        try {
            String responseString = response.body().string();
            AudioOfSingerResponse audioResponse = new AudioOfSingerResponse(responseString);
            adapter.updateData(audioResponse.listAudios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        swipeRefreshLayout.setRefreshing(false);


    }
}
