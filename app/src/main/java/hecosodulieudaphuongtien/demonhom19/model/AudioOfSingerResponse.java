package hecosodulieudaphuongtien.demonhom19.model;

import com.google.gson.JsonArray;

import java.util.ArrayList;

/**
 * Created by datjSs on 11/1/2016.
 */

public class AudioOfSingerResponse extends BaseResponse {
    public ArrayList<Audio> listAudios;

    public AudioOfSingerResponse(String jsonString) {
        super(jsonString);
        listAudios = new ArrayList<>();
        Singer singer = Singer.createSinger(getData().get("Singer").getAsJsonObject());
        JsonArray arrAudio = getData().get("audios").getAsJsonArray();
        for (int i = 0; i < arrAudio.size(); i++) {
            listAudios.add(Audio.createAudio(arrAudio.get(i).getAsJsonObject(), singer));
        }
    }
}
