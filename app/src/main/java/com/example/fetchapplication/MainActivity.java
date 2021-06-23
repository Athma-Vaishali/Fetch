package com.example.fetchapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity{
    public static final String URL="https://fetch-hiring.s3.amazonaws.com/hiring.json";
    public static final String TAG="MainActivity";
    List<Items> items=new ArrayList<>();

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    List<String> listDataHeader= new ArrayList<>();
    HashMap<String, List<String>> listDataChild = new HashMap<>();
    HashMap<String, List<Items>> objHash = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expListView = findViewById(R.id.lvExp);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                try {
                    JSONArray jsonArray=json.jsonArray;
                    items.addAll(Items.fromJsonArray(jsonArray));
                    prepareListData(items);
                    sortingNames();
                    listAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG,"Exception",e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG,"onFailure",throwable);
            }
        });

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortingNames() {
        for (List<String> nameList : listDataChild.values()) {
            nameList.sort(Comparator.comparing(String::toString));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortingID() {
        for (String key : objHash.keySet()) {
            List<Items> listItems = objHash.get(key);
            System.out.println("Before: "+ listItems);
            listItems.sort(Comparator.comparing(Items::getId));
            System.out.println("After: "+ objHash.get(key));
        }

        for (String key : objHash.keySet()) {
            List<String> tempNames=new ArrayList<>();
            List<Items> list=objHash.get(key);
            for(int i=0;i<list.size();i++){
                tempNames.add(list.get(i).getName());
            }
            listDataChild.put(key, tempNames);
        }

    }

    private void prepareListData(List<Items> items) {
        if(items.size()>0){
            Log.i(TAG,"MyDATA size " + items.size());
            Log.i(TAG,"MyDATA " + items.get(0).getName());

            for(int i=0;i<items.size();i++){
                if(listDataChild.get(items.get(i).getListId().toString())!=null){
                    List<String> temp;
                    temp=listDataChild.get(items.get(i).getListId().toString());
                    temp.add(items.get(i).getName());
                    listDataChild.put((items.get(i).getListId().toString()),temp);

                    List<Items> tempObj;
                    tempObj=objHash.get((items.get(i).getListId().toString()));
                    tempObj.add(items.get(i));
                    objHash.put((items.get(i).getListId().toString()),tempObj);
                }else{
                    List<String> temp = new ArrayList<>();
                    listDataChild.put((items.get(i).getListId().toString()),temp);

                    List<Items> tempObj=new ArrayList<>();
                    objHash.put((items.get(i).getListId().toString()),tempObj);
                }
            }
            listDataHeader.addAll(listDataChild.keySet());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sortName) {
            sortingNames();
            listAdapter.notifyDataSetChanged();
            return true;
        }

        if (item.getItemId() == R.id.sortID) {
            sortingID();
            listAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}