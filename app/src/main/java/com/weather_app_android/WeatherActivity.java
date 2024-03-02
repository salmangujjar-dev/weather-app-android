package com.weather_app_android;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.weather_app_android.data.CityHistoryDatabase;
import com.weather_app_android.data.entities.CityHistory;
import com.weather_app_android.data.entities.CityHistoryDetails;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    private String cityName;
    String cityN, country, windDir, weatherDesc, weatherIconUrl;
    Integer temp, windSpeed, humidity;
    Double preCip;
    private Gson gson;
    public static CityHistoryDatabase db;

    Button historyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        db = Room.databaseBuilder(getApplicationContext(), CityHistoryDatabase.class, "cityhistory-db").allowMainThreadQueries().build();       Intent intent = getIntent();
        cityName = intent.getExtras().getString("cityName");


        weather();

        historyBtn = findViewById(R.id.checkHistory);
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addToCityHistory(String cityN, Integer temp, String weatherDesc, Integer windSpeed, String windDir, Double preCip, Integer humidity) {
        CityHistory cityHistory = new CityHistory();
        cityHistory.setCityName(cityN);

        CityHistoryDetails cityHistoryDetails = new CityHistoryDetails();
        cityHistoryDetails.setRecordDate(""+java.time.LocalDate.now());
        cityHistoryDetails.setTemperature(temp);
        cityHistoryDetails.setDescription(weatherDesc);
        cityHistoryDetails.setWindSpeed(windSpeed);
        cityHistoryDetails.setWindDir(windDir);
        cityHistoryDetails.setPrecip(preCip);
        cityHistoryDetails.setHumidity(humidity);


        List<String> itemsFromDB = db.cityHistoryDAO().searchCity(cityName);
        if (itemsFromDB.isEmpty()) {
            db.cityHistoryDAO().insertCity(cityHistory);
            Integer m = db.cityHistoryDAO().getMax();
            cityHistoryDetails.setCityId(m);
            db.cityHistoryDetailsDAO().insertWeather(cityHistoryDetails);

        }
    }

    public void weather(){
        String apiKey = getResources().getString(R.string.weatherstack_api_key);
        String url = "http://api.weatherstack.com/current" +
                "?access_key=" + apiKey +
                "&query=" + cityName ;

        JSONObject jsonObject = new JSONObject();

        ImageView weatherIcon = findViewById(R.id.weatherIcon);
        TextView degree = findViewById(R.id.degree);
        TextView city = findViewById(R.id.city);
        TextView desc = findViewById(R.id.desc);
        TextView wdir = findViewById(R.id.wdir);
        TextView wspeed = findViewById(R.id.wspeed);
        TextView pcip = findViewById(R.id.pcip);
        TextView hum = findViewById(R.id.hum);


        Response.Listener<JSONObject> successBlock = new Response.Listener<JSONObject>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
               try{
                   //Using Gson
                    Gson gson = new Gson();
                    WeatherData weatherData;
                    weatherData = gson.fromJson(response.toString(), WeatherData.class);
                    cityN = weatherData.getLocation().getName();
                    country = weatherData.getLocation().getCountry();

                    temp = weatherData.getCurrent().getTemperature();
                    windDir = weatherData.getCurrent().getWind_dir();
                    windSpeed = weatherData.getCurrent().getWind_speed();
                    preCip = weatherData.getCurrent().getPrecip();
                    humidity = weatherData.getCurrent().getHumidity();

                    city.setText(cityN.toString() +", "+country);
                    degree.setText(temp.toString()+"°");
                    wdir.setText("Wind Direction: " + windDir);
                    wspeed.setText("Wind Speed: " + windSpeed.toString() +" km/h");
                    pcip.setText("Precip: " + preCip.toString());
                    hum.setText("Humidity: " + humidity.toString());

                    //For JsonArray
                   JSONObject current = response.getJSONObject("current");
                   JSONArray weather_description = current.getJSONArray("weather_descriptions");
                   weatherDesc = weather_description.getString(0);
                   JSONArray weather_icons = current.getJSONArray("weather_icons");
                   weatherIconUrl = weather_icons.getString(0);
                   Glide.with(WeatherActivity.this).load(weatherIconUrl).into(weatherIcon);
                   desc.setText(weatherDesc);

                   //Database Update
                   addToCityHistory(cityN, temp, weatherDesc, windSpeed, windDir, preCip, humidity);

                   Toast.makeText(WeatherActivity.this , cityN + country + " Weather Fetch Successfully.", Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(WeatherActivity.this, e.getMessage() , Toast.LENGTH_LONG).show();
                }

            }


        };

        Response.ErrorListener failureBlock = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeatherActivity.this, "Error. " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, url, jsonObject, successBlock, failureBlock);
        Volley.newRequestQueue(this).add(request);

    }
    public void onBackPressed() {
        finish();
    }

}
