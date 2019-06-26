package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class requestsActivity extends AppCompatActivity {

    ListView nListView;
    ArrayList<String> list;
    String bandRequests;
    String bandMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        requestsActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.brushed1);

//        define list and listview with onclicklistener
        list = new ArrayList<>();
        nListView = findViewById(R.id.listView3);
        nListView.setOnItemClickListener(new requestsActivity.GridItemClickListener());

//        get values from sharedpreferences
        SharedPreferences sharedPreferences = getSharedPreferences("bandRequests", MODE_PRIVATE);
        bandRequests = sharedPreferences.getString("bandRequests", "None");
        SharedPreferences sharedPreferences1 = getSharedPreferences("bandMembers", MODE_PRIVATE);
        bandMembers = sharedPreferences1.getString("bandMembers", "None");

//        get all the request from the band and set in listadapter
        String[] results = bandRequests.split("<>");
        for(int i=0;i<results.length;i++) {
            list.add(results[i]);
        }
        nListView.setAdapter(new requestListAdapter(this, 0, list));
    }

//    gridItemClickListener for listview with requests
    private class GridItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            get email from user that requested
            String Email = list.get(position);
            String requests = "";
            String members;

//            save clicked user to members of the band
            if(!bandMembers.equals("Null")){
                members = bandMembers + "<>" + Email;
            }else{
                members = Email;
            }

//            remove clicked user from requests of the band
            String[] results = bandRequests.split("<>");
            for(int i=0;i<results.length;i++){
                if(!results[i].equals(Email)){
                    requests += results[i] + "<>";
                }
            }

//            save in preferences
            bandRequests = requests;
            bandMembers = members;
            setPreference("bandRequests", requests);
            setPreference("bandMembers", members);

//            define requests and set in listview
            list = new ArrayList<>();
            results = bandRequests.split("<>");
            for(int i=0;i<results.length;i++) {
                list.add(results[i]);
            }
            nListView.setAdapter(new requestListAdapter(requestsActivity.this, 0, list));

//            get band from preferences of the current user and make request to add member to database
            SharedPreferences sharedPreferences=getSharedPreferences("Band",MODE_PRIVATE);
            String band = sharedPreferences.getString("Band", "None");

            new AddBandMember().execute(Email, band, requests, members);

        }
    }

//    easy way to get the sharedPreferences
    private void setPreference(String name, String value){
        SharedPreferences sharedPreferences=getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name,value);
        editor.apply();
    }

//    function for connecting to api for adding new member to band in database
    public class AddBandMember extends AsyncTask<String, Void, String> {
        String result;
        Response response;

//        if request is finished
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        showToast("Member added to band!");
    }

//    function that runs in the background
    @Override
        protected String doInBackground(String... strings) {

//        get values from call function
            String Email = strings[0];
            String Band = strings[1];
            String requests = strings[2];
            String members = strings[3];
            String finalURL = "http://10.0.2.2/api/accept_request.php";

            OkHttpClient okHttpClient = new OkHttpClient();

//            make form body with post data
            RequestBody formBody = new FormBody.Builder()
                .add("user_id",Email)
                .add("band_name",Band)
                .add("requests",requests)
                .add("members",members)
                .build();

//            make and execute request
            Request request =  new Request.Builder()
                    .url(finalURL)
                    .get()
                    .post(formBody)
                    .build();

//            check if request is handled correct
            try{
                response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()){
                    result = response.body().string();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

//    function for showing Toast messages
    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(requestsActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
