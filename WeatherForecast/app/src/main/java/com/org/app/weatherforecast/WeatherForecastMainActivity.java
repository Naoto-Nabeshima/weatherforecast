package com.org.app.weatherforecast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WeatherForecastMainActivity extends AppCompatActivity {

    private Button mUpdateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast_main);

        mUpdateButton = (android.widget.Button)findViewById(R.id.update_button);
        mUpdateButton.setOnClickListener(UpdateListner);
    }

    private View.OnClickListener UpdateListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };
}
