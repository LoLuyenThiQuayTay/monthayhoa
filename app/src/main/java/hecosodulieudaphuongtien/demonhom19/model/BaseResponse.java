package hecosodulieudaphuongtien.demonhom19.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by Z170A Gaming M7 on 9/26/2016.
 */
public class BaseResponse {
    public static final String PROCESS_CODE = "success";
    public static final String DATA = "data";


    public int success;
    private JsonObject data;
    private JsonArray listData;
//hinh nhu tai cai dong ky tu trang kia lam the nao bay h  sua cai text y thanh k gioihan bay h sua chi co tao lai cai khac thoi vkl

    public BaseResponse(String jsonString) {
        JsonParser jsonParser = new JsonParser();

        JsonObject jsonObject = jsonParser.parse(jsonString).getAsJsonObject();
        if (!jsonObject.get(PROCESS_CODE).isJsonNull()) {
            success = jsonObject.get(PROCESS_CODE).getAsInt();
        }
        JsonElement element = jsonObject.get(DATA);
        if (!element.isJsonNull()) {
            if (element.isJsonObject()) {
                this.data = element.getAsJsonObject();
            } else if (element.isJsonArray()) {
                this.listData = element.getAsJsonArray();
            }

        }

    }


    public JsonObject getData() {
        return data;
    }

    public JsonArray getListData() {
        return listData;
    }

}
