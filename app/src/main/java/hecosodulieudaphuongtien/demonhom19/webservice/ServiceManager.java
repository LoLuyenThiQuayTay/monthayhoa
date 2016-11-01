package hecosodulieudaphuongtien.demonhom19.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Z170A Gaming M7 on 9/26/2016.
 */
public class ServiceManager {
    private static final String BASE_URL = "http://192.168.164.1:8080/";
    private static MyService serviceInstance;

    public static MyService getServerInstance() {
        if (serviceInstance == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            serviceInstance = retrofit.create(MyService.class);
            return serviceInstance;
        }
        return serviceInstance;
    }
}
