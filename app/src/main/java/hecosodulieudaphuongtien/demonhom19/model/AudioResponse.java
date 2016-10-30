package hecosodulieudaphuongtien.demonhom19.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by Z170A Gaming M7 on 10/28/2016.
 */
public class AudioResponse extends BaseResponse {
    public static final String AUDIOS = "audios";
    public static final String SINGER = "Singer";
    public ArrayList<Audio> listAudio;

    public AudioResponse(String jsonString) {
        super(jsonString);
        listAudio = new ArrayList<>();
        JsonArray audiosArr = getData().get(AUDIOS).getAsJsonArray();
      final   JsonObject singerObject = getData().get(SINGER).getAsJsonObject();

        for (int i = 0; i < audiosArr.size(); i++) {
            JsonObject audioJson = audiosArr.get(i).getAsJsonObject();
            listAudio.add(Audio.createAudio(audioJson,singerObject));
        }
    }
}
