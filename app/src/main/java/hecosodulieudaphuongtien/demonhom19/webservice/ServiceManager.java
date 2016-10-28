package hecosodulieudaphuongtien.demonhom19.webservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Z170A Gaming M7 on 9/26/2016.
 */
public class ServiceManager {
    private static final String BASE_URL= "http://api.taplifeapp.com:6969/";
    private static MyService serviceInstance;

    public static MyService getServerInstance() {
        if (serviceInstance == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            serviceInstance = retrofit.create(MyService.class);
            return serviceInstance;
        }
        return serviceInstance;
    }
}
