package hecosodulieudaphuongtien.demonhom19.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by admin on 3/26/2016.
 */
public class Audio {
    public static final String ID_AUDIO = "ID_Audio";
    public static final String TITLE = "Title";
    public static final String VIEW_COUNT = "PartPosition";
    public static final String DOWNLOAD_COUNT = "PartPosition";
    public static final String PART_COUNT = "PartPosition";
    public static final String PART_POSITION = "PartPosition";
    public static final String URL = "Url";
    public static final String RATE = "PartPosition";

    public static final String SINGER = "Singer";


    public int idAudio;
    public String title;
    public int viewCount;
    public int downloadCount;
    public int partCount;
    public String rawUrl;
    public Singer singer;
    public float rate;
    public boolean isPlaying = false;
    public int number;
    public ArrayList<String> listRealUrl = new ArrayList<>();

    public String getURL(int position) {
        if (position < listRealUrl.size())
            return Utils.BASE_URL + listRealUrl.get(position);
        else return "";
    }

    public static Audio createAudio(JsonObject jsonObject) {
        Audio audio = new Audio();
        audio.idAudio = jsonObject.get(ID_AUDIO).getAsInt();
        audio.title = jsonObject.get(TITLE).getAsString();
        audio.viewCount = jsonObject.get(VIEW_COUNT).getAsInt();
        audio.downloadCount = jsonObject.get(DOWNLOAD_COUNT).getAsInt();
        audio.partCount = jsonObject.get(PART_COUNT).getAsInt();
        JsonArray arrUrl = jsonObject.get(URL).getAsJsonArray();
        List<Url> listURL = new ArrayList<Url>();
        for (int i = 0; i < arrUrl.size(); i++) {
            JsonObject urlJson = arrUrl.get(i).getAsJsonObject();
            listURL.add(Url.createUrl(urlJson));
        }
        Collections.sort(listURL, comparator); // use the comparator as much as u want
        for (int i = 0; i < listURL.size(); i++) {
            audio.listRealUrl.add(listURL.get(i).url);
        }
        audio.rate = jsonObject.get(RATE).getAsFloat();
        JsonObject singerJson = jsonObject.get(SINGER).getAsJsonObject();
        audio.singer = Singer.createSinger(singerJson);
        return audio;
    }

    public static Comparator<Url> comparator = new Comparator<Url>() {
        @Override
        public int compare(Url u1, Url u2) {
            return u2.positionPart - u1.positionPart; // use your logic
        }
    };

    public static class Url {
        public int positionPart;
        public String url;

        public static Url createUrl(JsonObject jsonObject) {
            Url url = new Url();
            url.positionPart = jsonObject.get(PART_POSITION).getAsInt();
            url.url = jsonObject.get(URL).getAsString();
            return url;

        }
    }
}
