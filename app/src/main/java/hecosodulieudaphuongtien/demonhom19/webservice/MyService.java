package hecosodulieudaphuongtien.demonhom19.webservice;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by Z170A Gaming M7 on 9/26/2016.
 */
public interface MyService {

    @GET("AudioPhantan/api/get_Audio_By_Name.php")
    Call<ResponseBody> searchAudioByName(@Query("name") String name);

}
