package hecosodulieudaphuongtien.demonhom19.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.adapter.AudioAdapter;
import hecosodulieudaphuongtien.demonhom19.adapter.SingerAdapter;
import hecosodulieudaphuongtien.demonhom19.mediaplayer.MyPlayer;
import hecosodulieudaphuongtien.demonhom19.model.Audio;
import hecosodulieudaphuongtien.demonhom19.model.AudioResponse;
import hecosodulieudaphuongtien.demonhom19.model.Singer;
import hecosodulieudaphuongtien.demonhom19.model.SingerResponse;
import hecosodulieudaphuongtien.demonhom19.sqlite.DownloadAudio;
import hecosodulieudaphuongtien.demonhom19.webservice.ServiceManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 3/24/2016.
 */
public class SearchFragment extends Fragment implements TextWatcher, AudioAdapter.OnClickItemRecyclerView, SingerAdapter.OnClickItemRecyclerView {

    private ArrayList<Audio> listAudios = new ArrayList<>();
    private MainActivity activity;
    private ImageView btnBack;
    private EditText etSearch;
    private RecyclerView listSinger, listAudio;
    private SingerAdapter adapterSinger;
    private AudioAdapter adapterAudio;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        btnBack = (ImageView) rootView.findViewById(R.id.btn_back);
        etSearch = (EditText) rootView.findViewById(R.id.ed_search);
        listSinger = (RecyclerView) rootView.findViewById(R.id.listSinger);
        listAudio = (RecyclerView) rootView.findViewById(R.id.listAudio);
        listAudio.setLayoutManager(new LinearLayoutManager(getActivity()));
        listSinger.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterAudio = new AudioAdapter(getActivity(), this);
        adapterSinger = new SingerAdapter(getActivity(), this);
        listAudio.setAdapter(adapterAudio);
        listSinger.setAdapter(adapterSinger);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getSupportFragmentManager().popBackStack();
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btnBack.getWindowToken(), 0);
            }
        });
        etSearch.addTextChangedListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
// something
        String input = etSearch.getText().toString();
        if (!input.trim().equals("")) {
            ServiceManager.getServerInstance().searchSingerByName(input).enqueue(responseSinger);
            ServiceManager.getServerInstance().searchAudioByName(input).enqueue(responAudio);
        } else {
            adapterSinger.updateData(new ArrayList<Singer>());
            adapterAudio.updateData(new ArrayList<Audio>());
        }
//ngon roi nguoi anh em uh
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClickItem(Audio audio) {
        MyPlayer.getInstance().playOnline(audio);
        activity.replaceFragmentUp(new PlayerFragment(MyPlayer.getAudioPlaying()));
    }

    @Override
    public void onClickSubItem(Audio audio) {
        DownloadAudio downloadAudio = new DownloadAudio(activity, audio, null);
        downloadAudio.execute();
    }

    @Override
    public void onClickItem(Singer singer) {
        activity.replaceFragment(new DetailSingerFragment(singer));
    }


    private Callback<ResponseBody> responAudio = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                String stringResponse = response.body().string();
                AudioResponse audioResponse = new AudioResponse(stringResponse);
                if (audioResponse.success > 0) {
                    adapterAudio.updateData(audioResponse.listAudio);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

        }
    };
    private Callback<ResponseBody> responseSinger = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                String stringResponse = response.body().string();
                Log.e("WTF", "onResponse: " + stringResponse);
                SingerResponse singerResponse = new SingerResponse(stringResponse);
                if (singerResponse.success > 0) {
                    adapterSinger.updateData(singerResponse.listSinger);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

        }
    };
}
