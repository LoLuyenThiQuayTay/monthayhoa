package hecosodulieudaphuongtien.demonhom19.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.adapter.AudioAdapter;
import hecosodulieudaphuongtien.demonhom19.model.Audio;
import hecosodulieudaphuongtien.demonhom19.model.AudioResponse;
import hecosodulieudaphuongtien.demonhom19.model.Singer;
import hecosodulieudaphuongtien.demonhom19.model.Utils;
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
        ivAvatar = (ImageView) rootView.findViewById(R.id.ivAvatar);
        tvProFile = (TextView) rootView.findViewById(R.id.tvProfile);
        recyclerAudio = (RecyclerView) rootView.findViewById(R.id.listAudio);
        listAudios = new ArrayList<>();
        manager = new GridLayoutManager(getContext(), 1);
        recyclerAudio.setLayoutManager(manager);
        adapter = new AudioAdapter(getContext(), this);
        recyclerAudio.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClickItem(int position) {

    }

    @Override
    public void onResume() {
        super.onResume();
        loadAudio();
    }

    private void loadAudio() {
        ServiceManager.getServerInstance().searchAudioByIDSinger(singer.id).enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            String responseString = response.body().string();
            AudioResponse audioResponse = new AudioResponse(responseString);
            adapter.updateData(audioResponse.listAudio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

    }
}
