package com.weather_app_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AutoCompleteTextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button checkWeatherBtn;
    private TextInputLayout cityName;
    private AutoCompleteTextView cityNameAutoComplete;
    private ArrayAdapter<String> adapter;
    private RequestQueue requestQueue;
    private Handler handler;
    private Runnable fetchCitiesRunnable;
    private ArrayList<String> suggestions = new ArrayList<>();
    String strCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkWeatherBtn = findViewById(R.id.checkWeather);
        cityName = findViewById(R.id.textInputLayout);

        cityNameAutoComplete = findViewById(R.id.cityNameAutoComplete);

        requestQueue = Volley.newRequestQueue(this);
        handler = new Handler();

        cityNameAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString().trim();
                if (!query.isEmpty()) {
                    debounceFetchCities(query);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        Intent intent = new Intent(this, WeatherActivity.class);

        checkWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strCityName = cityName.getEditText().getText().toString().trim();
                if (strCityName.matches("")){
                    Toast.makeText(MainActivity.this, "Please enter a City Name. ", Toast.LENGTH_LONG).show();
                }
                else{
                    intent.putExtra("cityName", strCityName);
                    startActivity(intent);
                }
            }
        });
    }

    private void debounceFetchCities(final String query) {
        if (fetchCitiesRunnable != null) {
            handler.removeCallbacks(fetchCitiesRunnable);
        }

        fetchCitiesRunnable = new Runnable() {
            @Override
            public void run() {
                fetchCities(query);
            }
        };

        // Delay debounce reset to ensure API call is triggered effectively
        handler.postDelayed(fetchCitiesRunnable, 300);
    }

    private List<String> fetchCities(String query) {
        String userName = getResources().getString(R.string.userName);
        String url = "http://api.geonames.org/searchJSON?q=" + query + "&maxRows=10&username="+userName;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<String> cities = new ArrayList<>();
                        try {
                            JSONArray geonames = response.getJSONArray("geonames");
                            for (int i = 0; i < geonames.length(); i++) {
                                JSONObject cityObject = geonames.getJSONObject(i);
                                String city = cityObject.getString("name");
                                String countryName = cityObject.getString("countryName");
                                cities.add(city + ", " + countryName);
                            }
                            adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, cities);

                            cityNameAutoComplete.setThreshold(1);
                            cityNameAutoComplete.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            cityNameAutoComplete.showDropDown();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
        return null;
    }

    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fetchCitiesRunnable != null) {
            handler.removeCallbacks(fetchCitiesRunnable);
        }
    }

}