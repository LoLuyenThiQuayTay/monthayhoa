package hecosodulieudaphuongtien.demonhom19.model;

import com.google.gson.JsonObject;

/**
 * Created by Z170A Gaming M7 on 10/27/2016.
 */
public class Singer {

    public static final String ID = "id";
    public static final String NAME = "Name";
    public static final String PROFILE = "Profile";
    public static final String AVATAR = "UrlImage";


    public int id;
    public String name;
    public String urlAvatar = "";
    public String profile;

    public Singer(String name) {
        this.name = name;
    }

    public Singer() {
    }

    public static Singer createSinger(JsonObject singerJson) {
        Singer singer = new Singer();
        singer.id = singerJson.get(ID).getAsInt();
        singer.name = singerJson.get(NAME).getAsString();
        singer.profile = singerJson.get(PROFILE).getAsString();
        singer.urlAvatar = singerJson.get(AVATAR).getAsString();
        return singer;
    }
}
