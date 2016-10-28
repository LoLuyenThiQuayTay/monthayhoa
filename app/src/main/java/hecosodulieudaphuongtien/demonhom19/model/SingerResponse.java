package hecosodulieudaphuongtien.demonhom19.model;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by Z170A Gaming M7 on 10/28/2016.
 */
public class SingerResponse extends BaseResponse {
    public ArrayList<Singer> listSinger;

    public SingerResponse(String jsonString) {
        super(jsonString);
        listSinger = new ArrayList<>();
        for (int i = 0; i < getListData().size(); i++) {
            JsonObject singerJson = getListData().get(i).getAsJsonObject();
            listSinger.add(Singer.createSinger(singerJson));
        }

    }
}
