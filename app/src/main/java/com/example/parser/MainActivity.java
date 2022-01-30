package com.example.parser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Double> Price;
    public ListView listview;
    public Button get;
    public View view;
    public JSONArray array;
    public ArrayList<JSONObject> list;
    public JSONObject file, file2;
    dadabase_work Dadabase_work;

    private String[] names={"btc", "eth", "usdt", "bnb", "usdc", "ada", "sol", "xrp", "dot", "luna"};
    public JsonObjectRequest request;

    private static final String url="https://api.coingecko.com/api/v3/global";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview=findViewById(R.id.cryptolist);
        get=findViewById(R.id.get_JSON);
        JSON_Load(url);
        Dadabase_work = new dadabase_work(this);
    }
   // public void action(View view){getJSON(array);}

    public void JSON_Load(String url){
        request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            file2 = response.getJSONObject("data");
                            file = file2.getJSONObject("market_cap_percentage");
                           // for(int i=0; i< names.length; i++){
                          //      Double v=file.getDouble(names[i]);
                          //      Log.w(names[i], " equals " + v);
                         //   }
                         //   Write_DB(view);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){} });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void return_DB(){
        JSON_Load(url);

    }
    public void Write_DB(View view) {
        JSON_Load(url);
        Dadabase_work.cleaner();

        try {
            for (int i = 0; i < names.length; i++) {
                String request = names[i];
                Double q=file.getDouble(request);
                String price=q.toString();
                Log.w("start_list", request + " " + price);
                Dadabase_work.inserts(request, price);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<String> result = Dadabase_work.read_DB();
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, result);
        listview.setAdapter(adapter);
    }

}