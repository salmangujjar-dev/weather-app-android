package com.weather_app_android;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weather_app_android.data.adapters.ContactAdapter;
import com.weather_app_android.data.entities.CityHistory;
import com.weather_app_android.data.entities.CityHistoryDetails;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ContactAdapter adapter;

    ArrayList<CityHistory> cityHistoryList = new ArrayList<CityHistory>();
    TextView txtView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        adapter = new ContactAdapter(this, cityHistoryList);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Spinner spinner = findViewById(R.id.spinner);


        List<String> allHistory = new ArrayList<String>();
        allHistory.add("All Cities");
        allHistory.addAll(WeatherActivity.db.cityHistoryDAO().getCityNames());
        ArrayAdapter sAdapter = new ArrayAdapter(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, allHistory);
        sAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sAdapter);

        txtView= findViewById(R.id.cityDetails);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    txtView.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    List<CityHistoryDetails> historyDetails = WeatherActivity.db.cityHistoryDetailsDAO().getCityWeather(position);
                    txtView.setText("Record ID: "+historyDetails.get(0).getRecordId()+"\n\n"+
                            "City ID: "+historyDetails.get(0).getCityId()+"\n\n"+
                            "Record Date: "+historyDetails.get(0).getRecordDate()+"\n\n"+
                            "Temperature: "+historyDetails.get(0).getTemperature()+"\n\n"+
                            "Description: "+historyDetails.get(0).getDescription()+"\n\n"+
                            "Wind Speed: "+historyDetails.get(0).getWindSpeed()+"\n\n"+
                            "Wind Direction: "+historyDetails.get(0).getWindDir()+"\n\n"+
                            "Precip: "+historyDetails.get(0).getPrecip()+"\n\n"+
                            "Himidity: "+historyDetails.get(0).getHumidity());
                    txtView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getHistory();

    }
    private void getHistory(){
        List<CityHistory> allHistory = WeatherActivity.db.cityHistoryDAO().getAll();
        cityHistoryList.clear();
        cityHistoryList.addAll(allHistory);
        adapter.notifyDataSetChanged();
    }
}
