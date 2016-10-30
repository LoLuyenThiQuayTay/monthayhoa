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
    public static final String Length = "Length";

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
    private int length;
    public ArrayList<AudioPart> listPart = new ArrayList<>();


    public static Audio createAudio(JsonObject jsonObject) {
        Audio audio = new Audio();
        audio.idAudio = jsonObject.get(ID_AUDIO).getAsInt();
        audio.title = jsonObject.get(TITLE).getAsString();
        audio.viewCount = jsonObject.get(VIEW_COUNT).getAsInt();
        audio.downloadCount = jsonObject.get(DOWNLOAD_COUNT).getAsInt();
        audio.partCount = jsonObject.get(PART_COUNT).getAsInt();
        JsonArray arrUrl = jsonObject.get(URL).getAsJsonArray();
        ArrayList<AudioPart> listURL = new ArrayList<AudioPart>();
        for (int i = 0; i < arrUrl.size(); i++) {
            JsonObject urlJson = arrUrl.get(i).getAsJsonObject();
            listURL.add(AudioPart.createUrl(urlJson));
        }
        Collections.sort(listURL, comparator); // use the comparator as much as u want
        audio.listPart = listURL;
        audio.rate = jsonObject.get(RATE).getAsFloat();
        JsonObject singerJson = jsonObject.get(SINGER).getAsJsonObject();
        audio.title = jsonObject.get(Length).getAsString();

        audio.singer = Singer.createSinger(singerJson);
        return audio;
    }

    public static Comparator<AudioPart> comparator = new Comparator<AudioPart>() {
        @Override
        public int compare(AudioPart u1, AudioPart u2) {
            return u2.positionPart - u1.positionPart; // use your logic
        }
    };

    public static class AudioPart {
        public int positionPart;
        public String url;
        public String lengthString;
        public int percent;

        public AudioPart() {
        }

        public AudioPart(int positionPart, String url, String lengthString) {
            this.positionPart = positionPart;
            this.url = url;
            this.lengthString = lengthString;
        }

        public static AudioPart createUrl(JsonObject jsonObject) {
            AudioPart url = new AudioPart();
            url.positionPart = jsonObject.get(PART_POSITION).getAsInt();
            url.url = jsonObject.get(URL).getAsString();
            url.lengthString = jsonObject.get(Length).getAsString();
            return url;

        }

        public int getLengthFromString() {
            String[] units = lengthString.split(":"); //will break the string up into an array
            int duration = 0;
            switch (units.length) {
                case 1:
                    int s1 = Integer.parseInt(units[0]); //second element
                    duration = s1; //add up our values
                    break;
                case 2:
                    int m2 = Integer.parseInt(units[0]); //first element
                    int s2 = Integer.parseInt(units[1]); //second element
                    duration = 60 * m2 + s2; //add up our values
                    break;
                case 3:

                    int h3 = Integer.parseInt(units[0]); //first element
                    int m3 = Integer.parseInt(units[1]); //second element
                    int s3 = Integer.parseInt(units[2]); //first element

                    duration = 60 * 60 * h3 + 60 * m3 + s3; //add up our values
                    break;

            }
            return duration;

        }
    }

    public int getAudioLength() {
        length = 0;
        for (int i = 0; i < listPart.size(); i++) {
            length += listPart.get(i).getLengthFromString();
        }
        return length;
    }

    public int getPartPercent(int position) {
        int percent = 0;
        percent = 100 * listPart.get(position).getLengthFromString() / getAudioLength();
        return percent;
    }

    public void updatePartPercent() {
        for (int i = 0; i < listPart.size(); i++) {
            listPart.get(i).percent = getPartPercent(i);
        }
    }

    public int getPositionPartPlaying(int percent) {
        int position = 0;
        if (percent < listPart.get(0).percent) return position;
        for (int i = 1; i < listPart.size(); i++) {
            if (listPart.get(i - 1).percent <= percent && listPart.get(i).percent > percent) {
                position = i;
                break;
            }
        }
        return position;
    }
}