package hecosodulieudaphuongtien.demonhom19.webservice;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Z170A Gaming M7 on 9/26/2016.
 */
public interface MyService {

    @GET("AudioPhantan/api/get_Audio_By_Name.php")
    Call<ResponseBody> searchAudioByName(@Query("name") String name);

    @GET("AudioPhantan/api/get_Audio_By_ID_Singer.php")
    Call<ResponseBody> searchAudioByIDSinger(@Query("id") int idSinger);

    @GET("AudioPhantan/api/get_Singer_By_Name.php")
    Call<ResponseBody> searchSingerByName(@Query("name") String name);

    @GET("AudioPhantan/api/update_Download.php")
    Call<ResponseBody> updateDownload(@Query("id") int id);

    @GET("AudioPhantan/api/update_View.php")
    Call<ResponseBody> updateView(@Query("id") int id);

    @GET("AudioPhantan/api/update_Rate.php")
    Call<ResponseBody> updateRate(@Query("id") int id, @Query("rate") float rate);

}