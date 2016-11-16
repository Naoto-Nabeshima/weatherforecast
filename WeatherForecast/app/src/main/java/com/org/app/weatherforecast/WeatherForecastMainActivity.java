package com.org.app.weatherforecast;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WeatherForecastMainActivity extends AppCompatActivity implements LocationListener{

    private Button mUpdateButton;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast_main);

        // 更新ボタンのインスタンスを取得
        mUpdateButton = (android.widget.Button) findViewById(R.id.update_button);
        mUpdateButton.setOnClickListener(UpdateListener);

        // 位置情報のインスタンスを取得
        mLocationManager = (LocationManager)getSystemService(this.LOCATION_SERVICE);

        if (null != mLocationManager) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // ネットワークによる取得が可能な場合、ネットワークプロバイダを使用
                if (true == mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    mLocationManager.removeUpdates(WeatherForecastMainActivity.this);
                    mLocationManager.requestLocationUpdates(mLocationManager.NETWORK_PROVIDER, 0, 0, WeatherForecastMainActivity.this);
                }
                // GPSによる取得が可能な場合、GPSを使用
                else if (true == mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }

    // 更新ボタンをクリック
    private View.OnClickListener UpdateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //    SetLocation();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        // 位置情報の取得を終了する
        mLocationManager.removeUpdates(this);
    }
}
