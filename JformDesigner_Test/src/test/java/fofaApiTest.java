import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.dongliu.requests.Requests;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public  class fofaApiTest {
    public String email;
    public  String key;
    public  String search;
    public final int  PAGE_SIZE=100;
    public fofaApiTest(String email,String key, String search){
        this.email = email;
        this.key = key;
        this.search = search;
    }
    public String[][] getData(){
        int page = 1;
        int totalSize = 0;
        int totalPages = 0;
        String b64Search = Base64.getEncoder().encodeToString(this.search.getBytes(StandardCharsets.UTF_8));
        JSONArray allResults = new JSONArray();
        do{
            String bytes = Requests.get("https://fofa.info/api/v1/search/all?email="+this.email+"&key="+this.key+"&qbase64="+b64Search+"&fields=host,ip,port,protocoal,server,city,icon_hash").send().readToText();
            JSONObject jsonObject = JSONObject.parseObject(bytes);
            boolean error = jsonObject.getBoolean("error");
            if (error) {
                String message = jsonObject.getString("errmsg");
                System.out.println("Error: " + message);
                break;
            }
            JSONArray results = jsonObject.getJSONArray("results");
            allResults.addAll(results);
            //int currentSize = results.size();
            totalSize = jsonObject.getIntValue("size");
            totalPages = (totalSize + PAGE_SIZE - 1) / PAGE_SIZE;

            //            for (int i = 0; i < currentSize; i++) {
            //                JSONArray result = results.getJSONArray(i);
            //                System.out.println(result);
            //            }
            page++;
        }while(page <= totalPages);
        int resultSize = allResults.size();
        String[][] resultArray = new String[resultSize][];
        for (int i = 0; i < resultSize; i++) {
            JSONArray result = allResults.getJSONArray(i);
            resultArray[i] = new String[result.size()];
            for (int j = 0; j < result.size(); j++) {
                resultArray[i][j] = result.getString(j);
            }
        }
        return resultArray;
    }
}
