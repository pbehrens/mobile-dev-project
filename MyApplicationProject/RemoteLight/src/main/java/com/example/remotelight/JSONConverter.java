package com.example.remotelight;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import com.example.remotelight.Command;
import com.google.gson.*;
/**
 * Created by thebeagle on 11/2/13.
 */
public class JSONConverter {

    public JSONConverter(){

    }

    public static HashMap<String, Command> createHashMapFromJson(String json){
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        String jsonLine = "{\n" +
                "    \"commands\": [\n" +
                "        {\n" +
                "            \"name\": \"listDirectory\",\n" +
                "            \"type\": \"static\",\n" +
                "            \"command\": \"ls\",\n" +
                "            \"timeout\": \"3000\",\n" +
                "            \"argument_type\": \"flags\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"printWorkingDirectory\",\n" +
                "            \"type\": \"static\",\n" +
                "            \"command\": \"pwd\",\n" +
                "            \"timeout\": \"3000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"printProcesses\",\n" +
                "            \"type\": \"static\",\n" +
                "            \"command\": \"ps ax\",\n" +
                "            \"timeout\": \"3000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"grep\",\n" +
                "            \"type\": \"piped\",\n" +
                "            \"command\": \"grep\",\n" +
                "            \"timeout\": \"3000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"printText\",\n" +
                "            \"type\": \"static\",\n" +
                "            \"command\": \"echo\",\n" +
                "            \"timeout\": \"3000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"getLastPid\",\n" +
                "            \"type\": \"static\",\n" +
                "            \"command\": \"echo !$\",\n" +
                "            \"timeout\": \"3000\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        Map jsonJavaRootObject;
        jsonJavaRootObject = new Gson().fromJson(jsonLine, Map.class);

        Log.e("json" ,jsonJavaRootObject.toString());
//
//        JsonElement jelement = new JsonParser().parse(jsonLine);
//        JsonObject  jobject = jelement.getAsJsonObject();
//        jobject = jobject.getAsJsonObject("data");
//        JsonArray jarray = jobject.getAsJsonArray("translations");
//        jobject = jarray.get(0).getAsJsonObject();
//        String result = jobject.get("translatedText").toString();
//        Log.e("json", )
//        Command target = new Command(name, command, type, timeoutMillis);
//        Command newCommand = gson.fromJson(json, Command());
//        MyType target2 = gson.fromJson(json, MyType.class); // deserializes json into target2



//    public String parse(String jsonLine) {
//
//    }
        return (HashMap) jsonJavaRootObject;
    }

}
