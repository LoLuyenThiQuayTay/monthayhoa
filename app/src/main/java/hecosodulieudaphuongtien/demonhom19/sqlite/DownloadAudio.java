package hecosodulieudaphuongtien.demonhom19.sqlite;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.net.URL;
import java.net.URLConnection;

import hecosodulieudaphuongtien.demonhom19.model.Audio;
import hecosodulieudaphuongtien.demonhom19.ui.MainActivity;

/**
 * Created by admin on 3/30/2016.
 */
public class DownloadAudio extends AsyncTask<String, String, String> {
    private MainActivity activity;
    private Audio audio;
    File rootDir = Environment.getExternalStorageDirectory();

    public DownloadAudio(MainActivity activity, Audio audio) {
        this.activity = activity;
        this.audio = audio;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.showDialog(activity.DIALOG_DOWNLOAD);
    }

    @Override
    protected String doInBackground(String... aurl) {
        if (audio.listPart.size() == 1) {
            downloadOnePart();
        } else {
            downloadTwoPart();
        }
        return null;

    }

    public void downloadTwoPart() {
        try {

            URL url1 = new URL(audio.listPart.get(0).url);
            URLConnection connection1 = url1.openConnection();
            connection1.connect();
            InputStream input1 = new BufferedInputStream(url1.openStream());


            URL url = new URL(audio.listPart.get(1).url);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream input2 = new BufferedInputStream(url.openStream());

            int lenghtOfFile1 = connection1.getContentLength();
            int lenghtOfFile2 = connection.getContentLength();
            int totalLength = lenghtOfFile1 + lenghtOfFile2;

            SequenceInputStream sis = new SequenceInputStream(input1, input2);
            OutputStream fos = new FileOutputStream(new File("/sdcard/Music/" + audio.title + ".mp3"));
            int count = 0;
            long total = 0;
            byte data[] = new byte[1024];
            while ((count = sis.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / totalLength));

                fos.write(data, 0, count);
            }
            fos.close();
            input1.close();
            input2.close();
            sis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//code nay` chay dung nhung lau a ?

    }

    public void downloadOnePart() {
        try {

            URL url1 = new URL(audio.listPart.get(0).url);
            URLConnection connection1 = url1.openConnection();
            connection1.connect();
            InputStream input1 = new BufferedInputStream(url1.openStream());

            int lenghtOfFile1 = connection1.getContentLength();

            OutputStream fos = new FileOutputStream(new File("/sdcard/Music/" + audio.title + ".mp3"));
            int count = 0;
            long total = 0;

            byte data[] = new byte[1024];
            while ((count = input1.read(data)) != -1) {
                total += count;
                fos.write(data, 0, count);
                publishProgress("" + (int) ((total * 100) / lenghtOfFile1));

            }
            fos.close();
            input1.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//code nay` chay dung nhung lau a ?

    }

    protected void onProgressUpdate(String... progress) {
        activity.progressDialog.setProgress(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String unused) {
        DataSource.addAudio(audio);
        activity.makeNotification("Tải về thành công !");
        activity.dismissDialog(activity.DIALOG_DOWNLOAD);
    }
}