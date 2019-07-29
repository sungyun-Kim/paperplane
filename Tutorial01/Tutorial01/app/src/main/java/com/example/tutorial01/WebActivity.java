package com.example.tutorial01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WebActivity extends AppCompatActivity {


    Button btnStart;
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        btnStart = findViewById(R.id.btnStart);
        tvContent = findViewById(R.id.tvContent);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpConnection httpConnection = HttpConnection.getInstance();
                httpConnection.requestServer(callback);

            }
        });
    }

    Callback callback = new Callback() {


        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final String jsonData = response.body().string();
            try {

                final String result = jsonParser(jsonData);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText(result);
                    }
                });

            } catch (JSONException e) {

            }

        }
    };

    public String jsonParser(String json) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(json);

        JSONArray jsonArray = jsonObject.getJSONArray("contacts");

        JSONObject object = jsonArray.getJSONObject(1);

        String result = object.getString("name")+"\n"+object.getString("email");
        return result;
    }

}
