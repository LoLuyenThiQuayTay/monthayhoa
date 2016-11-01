package hecosodulieudaphuongtien.demonhom19.sqlite;

/**
 * Created by datjSs on 11/1/2016.
 */

public interface DownloadListener {
    public void onStartDownload();

    public void onFinishDownload(int newCount);
}
