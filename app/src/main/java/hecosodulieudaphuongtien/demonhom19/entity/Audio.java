package hecosodulieudaphuongtien.demonhom19.entity;

/**
 * Created by admin on 3/26/2016.
 */
public class Audio {
//    public static String IP = "http://172.20.10.2/";


    public int id;
    public int idAudio;
    public String title;
    public String rawUrl;
    public int partCount;
    public int partPosition;
    public int idSinger;
    public float rate;
    public boolean isPlaying = false;
    public int number;

    public String getURL() {
        return Utils.BASE_URL + rawUrl;
    }

    ;
}
