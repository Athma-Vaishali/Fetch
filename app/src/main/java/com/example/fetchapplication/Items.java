package com.example.fetchapplication;


import android.os.Build;
import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Items {
    Integer id;
    Integer listId;
    String name;

    public Items(JSONObject obj) throws JSONException {
        this.id = obj.getInt("id");
        this.listId = obj.getInt("listId");
        this.name = obj.getString("name");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Items> fromJsonArray (JSONArray itemsArray) throws JSONException {
        List<Items> items=new ArrayList<>();
        for(int i=0;i<itemsArray.length();i++){
            int sizeofName=itemsArray.getJSONObject(i).getString("name").length();
            Boolean nullName = itemsArray.getJSONObject(i).isNull("name");
            if((!nullName) && (sizeofName != 0)){
                items.add(new Items(itemsArray.getJSONObject(i)));
            }
        }
        return items;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public Integer getListId() {
        return listId;
    }
}
