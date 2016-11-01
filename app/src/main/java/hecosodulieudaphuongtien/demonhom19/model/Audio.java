package hecosodulieudaphuongtien.demonhom19.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by admin on 3/26/2016.
 */
public class Audio {
    public static final String ID_AUDIO = "ID_Audio";
    public static final String TITLE = "Title";
    public static final String VIEW_COUNT = "ViewCount";
    public static final String DOWNLOAD_COUNT = "DownloadCount";
    public static final String PART_COUNT = "PartCount";
    public static final String PART_POSITION = "PartPosition";
    public static final String URL = "Url";
    public static final String RATE = "Rate";
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
    public int partPlaying = 0;


    public static Audio createAudio(JsonObject jsonAudio) {
        Audio audio = new Audio();
        audio.idAudio = jsonAudio.get(ID_AUDIO).getAsInt();
        audio.title = jsonAudio.get(TITLE).getAsString().trim();
        audio.viewCount = jsonAudio.get(VIEW_COUNT).getAsInt();
        audio.downloadCount = jsonAudio.get(DOWNLOAD_COUNT).getAsInt();
        audio.partCount = jsonAudio.get(PART_COUNT).getAsInt();
        JsonArray arrUrl = jsonAudio.get(URL).getAsJsonArray();
        ArrayList<AudioPart> listURL = new ArrayList<AudioPart>();
        for (int i = 0; i < arrUrl.size(); i++) {
            JsonObject urlJson = arrUrl.get(i).getAsJsonObject();
            listURL.add(AudioPart.createUrl(urlJson));
        }
        Collections.sort(listURL, comparator); // use the comparator as much as u want
        audio.listPart = listURL;
        audio.rate = jsonAudio.get(RATE).getAsFloat();
        JsonObject singerJson = jsonAudio.get(SINGER).getAsJsonObject();
        audio.singer = Singer.createSinger(singerJson);
        return audio;
    }

    public static Audio createAudio(JsonObject jsonAudio, Singer singer) {
        Audio audio = new Audio();
        audio.idAudio = jsonAudio.get(ID_AUDIO).getAsInt();
        audio.title = jsonAudio.get(TITLE).getAsString().trim();
        audio.viewCount = jsonAudio.get(VIEW_COUNT).getAsInt();
        audio.downloadCount = jsonAudio.get(DOWNLOAD_COUNT).getAsInt();
        audio.partCount = jsonAudio.get(PART_COUNT).getAsInt();
        JsonArray arrUrl = jsonAudio.get(URL).getAsJsonArray();
        ArrayList<AudioPart> listURL = new ArrayList<AudioPart>();
        for (int i = 0; i < arrUrl.size(); i++) {
            JsonObject urlJson = arrUrl.get(i).getAsJsonObject();
            listURL.add(AudioPart.createUrl(urlJson));
        }
        Collections.sort(listURL, comparator); // use the comparator as much as u want
        audio.listPart = listURL;
        audio.rate = jsonAudio.get(RATE).getAsFloat();
        audio.singer = singer;
        return audio;
    }

    //vl ong dat deo hieu sao :D ditme ghe chua ghe v l
    public static Comparator<AudioPart> comparator = new Comparator<AudioPart>() {
        @Override
        public int compare(AudioPart u1, AudioPart u2) {
            return (u1.positionPart - u2.positionPart); // use your logic
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
            url.url = jsonObject.get(URL).getAsString().trim();
            url.lengthString = jsonObject.get(Length).getAsString().trim();
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
            return duration * 1000;

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

    public int findPositionPartPlaying(int percent) {
        int position = 0;
        if (percent <= listPart.get(0).percent) {
            return position;
        }
        for (int i = 1; i < listPart.size(); i++) {
            if (listPart.get(i - 1).percent < percent && getCurentPercent(i) >= percent) {
                position = i;
                break;
            }
        }
        return position;
    }

    public int getCurentPercent(int position) {
        int currentPercent = 0;
        for (int i = 0; i < listPart.size(); i++) {
            if (i <= position) {
                currentPercent += listPart.get(i).percent;
            }
        }
        return currentPercent;
    }

    public int getCurrentPositionAudio(int percent) {
        int currentPosition = 0;
        int positionPart = findPositionPartPlaying(percent);

        if (positionPart == 0) {
            AudioPart firstPart = listPart.get(0);
            currentPosition = progressToTimer(percent, getAudioLength());
        } else {
            int totalPercentPlayed = 0;
            for (int i = 0; i <= positionPart; i++) {
                if (i < positionPart) {
                    currentPosition += listPart.get(i).getLengthFromString();
                    totalPercentPlayed += listPart.get(i).percent;
                } else if (i == positionPart) {
                    int percentInPart = percent - totalPercentPlayed;
                    currentPosition += progressToTimer(percentInPart, getAudioLength());
                }
            }
        }
        return currentPosition;
    }

    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        return currentDuration * 1000;
    }

    public String getStringTime(int duration) {
        String newTime;
        long totalSec = duration / 1000;
        long minute = totalSec / 60;
        long sec = totalSec % 60;
        if (minute > 100) return ("--:--");
        if (minute < 10) {
            if (sec < 10) {
                newTime = "0" + minute + ":0" + sec;
                return newTime;
            } else {
                newTime = "0" + minute + ":" + sec;
                return newTime;
            }
        } else {
            if (sec < 10) {
                newTime = minute + ":0" + sec;
                return newTime;
            } else {
                newTime = minute + ":" + sec;
                return newTime;
            }
        }


    }
}
