import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.dongliu.requests.Requests;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class fofaApi {
    public static final int PAGE_SIZE = 100;

    public static void main(String[] args) {
        //System.out.println(bytes);
        int page = 1;
        int totalSize = 0;
        int totalPages = 0;
        do {
            String bytes = Requests.get("https://fofa.info/api/v1/search/all?email=xxxx@qq.com&key=xxxx&qbase64=aHR0cHM6Ly9zMDEubWlmaWxlLmNuL2Zhdmljb24uaWNv&fields=host,icon_hash").send().readToText();
            JSONObject jsonObject = JSONObject.parseObject(bytes);
            boolean error = jsonObject.getBoolean("error");
            if (error) {
                String message = jsonObject.getString("errmsg");
                System.out.println("Error: " + message);
                break;
            }
            JSONArray results = jsonObject.getJSONArray("results");
            int currentSize = results.size();
            totalSize = jsonObject.getIntValue("size");
            totalPages = (totalSize + PAGE_SIZE - 1) / PAGE_SIZE;

            for (int i = 0; i < currentSize; i++) {
                JSONArray result = results.getJSONArray(i);
                System.out.println(result);
            }
            page++;
        } while (page <= totalPages);
    }
}

