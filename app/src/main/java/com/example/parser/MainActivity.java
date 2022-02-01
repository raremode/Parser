package com.example.parser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ListView listview;
    public Button get;
    public View view;
    public JSONObject file, file2;
    dadabase_work Dadabase_work;

    private String[] names={"btc", "eth", "usdt", "bnb", "usdc", "ada", "sol", "xrp", "dot", "luna"};
    public JsonObjectRequest request;

    private static final String url="https://api.coingecko.com/api/v3/global";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);
        listview=findViewById(R.id.cryptolist);
        get=findViewById(R.id.get_JSON);
        JSON_Load(url);
        Dadabase_work = new dadabase_work(this);
    }

    public void JSON_Load(String url){
        request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            file2 = response.getJSONObject("data");
                            file = file2.getJSONObject("market_cap_percentage");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Resource is not founded", Toast.LENGTH_SHORT);
                toast.show();
            } });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void Write_DB(View view) {
        JSON_Load(url);
        Dadabase_work.cleaner();
        try {
            for (int i = 0; i < names.length; i++) {
                String request = names[i];
                Double q=file.getDouble(request);
                String price=q.toString();
                Dadabase_work.inserts(request, price);
            }
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Successful parsing!", Toast.LENGTH_SHORT);
            toast.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void show(View view){
        ArrayList<String> result = Dadabase_work.read_DB();
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, result);
        listview.setAdapter(adapter);
    }

}