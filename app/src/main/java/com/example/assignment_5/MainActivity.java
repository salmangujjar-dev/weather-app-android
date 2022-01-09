package com.example.assignment_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


public class MainActivity extends AppCompatActivity {

    private Button checkWeatherBtn;
    private TextInputLayout cityName;
    String strCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkWeatherBtn = findViewById(R.id.checkWeather);
        cityName = findViewById(R.id.textInputLayout);

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
    public void onBackPressed() {
        finish();
    }

}