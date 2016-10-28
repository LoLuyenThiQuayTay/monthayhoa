package hecosodulieudaphuongtien.demonhom19.model;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by Z170A Gaming M7 on 10/28/2016.
 */
public class AudioResponse extends BaseResponse {
    public ArrayList<Audio> listAudio;

    public AudioResponse(String jsonString) {
        super(jsonString);
        listAudio = new ArrayList<>();
        for (int i = 0; i < getListData().size(); i++) {
            JsonObject audioJson = getListData().get(i).getAsJsonObject();
            listAudio.add(Audio.createAudio(audioJson));
        }
    }
}
