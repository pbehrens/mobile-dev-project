package com.example.remotelight;

import android.util.Log;

import java.util.HashMap;

import org.json.*;


/**
 COPYRIGHT (C) <2013> <pbehrens>. All Rights Reserved.
 Unused JSOn parsing converter that was going to be used to parse lists of optional commands for the mobilew device to use on the server.
 @author <rplutarco>
 @version <1.0> <date:2013-12-9>
 */
public class JSONConverter {

    public JSONConverter(){

    }

    public static HashMap<String, Command> createHashMapFromJson(String json){
        HashMap<String, Command> commandList = new HashMap<String, Command>();
        String jsonLine = "{\n" +
                "    \"commands\": [\n" +
                "        {\n" +
                "            \"name\": \"listDirectory\",\n" +
                "            \"type\": \"static\",\n" +
                "            \"command\": \"ls\",\n" +
                "            \"timeout\": \"3000\",\n" +
                "            \"arguments\": [\n" +
                "                \"flags\",\n" +
                "                \"directory\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"printWorkingDirectory\",\n" +
                "            \"type\": \"static\",\n" +
                "            \"command\": \"pwd\",\n" +
                "            \"timeout\": \"3000\",\n" +
                "            \"arguments\": [\n" +
                "                \"flags\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"printProcesses\",\n" +
                "            \"type\": \"static\",\n" +
                "            \"command\": \"ps ax\",\n" +
                "            \"timeout\": \"3000\",\n" +
                "            \"arguments\": [\n" +
                "                \"flags\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"grep\",\n" +
                "            \"type\": \"piped\",\n" +
                "            \"command\": \"grep\",\n" +
                "            \"timeout\": \"3000\",\n" +
                "            \"arguments\": [\n" +
                "                \"flags\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"printText\",\n" +
                "            \"type\": \"static\",\n" +
                "            \"command\": \"echo\",\n" +
                "            \"timeout\": \"3000\",\n" +
                "            \"arguments\": [\n" +
                "                \"flags\",\n" +
                "                \"string\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"getLastPid\",\n" +
                "            \"type\": \"static\",\n" +
                "            \"command\": \"echo !$\",\n" +
                "            \"timeout\": \"3000\",\n" +
                "            \"arguments\": [\n" +
                "                \n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonLine);
            Log.e("json", jsonObj.toString());
            Log.e("json", "The length is " + String.valueOf(jsonObj.getJSONArray("commands").length()));
            JSONArray jsonArray = jsonObj.getJSONArray("commands");

            int len = jsonArray.length();
            for(int i = 0; i < len; i++){
                JSONObject command = jsonArray.getJSONObject(i);
                String nameString = command.get("name").toString();
                String commandString = command.get("command").toString();
                String typeString = command.get("type").toString();
                int timeout = Integer.valueOf(command.get("timeout").toString());
                JSONArray arguments = command.getJSONArray("arguments");

                Command aCommand = new Command(nameString, commandString, typeString, null, null, timeout);
                commandList.put(nameString, aCommand);

                Log.e("json", "command object is " + aCommand.toString() +  "\n");

            }
            return commandList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
