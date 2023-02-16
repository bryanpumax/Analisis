package com.example.analisis;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.analisis.Gps.Ubicacion_home;
import com.example.analisis.databinding.ActivityHomeBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class home extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
permiso_GPS();
        ImageButton Proveedor_btn=(ImageButton) findViewById(R.id.Proveedor_btn);
        //imageButton2
        ImageButton imageButton2=(ImageButton) findViewById(R.id.imageButton2);
        ImageButton imageButton4=(ImageButton) findViewById(R.id.imageButton4);
        ImageButton imageButton3=(ImageButton) findViewById(R.id.imageButton3);
Proveedor_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(home.this,dueno.class);        startActivity(intent);
    }});
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this,Parcela.class);                startActivity(intent);
            }        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* int pid = android.os.Process.myPid(); android.os.Process.killProcess(pid); System.exit(0);
                finish(); System.exit(0);*/
                Intent intent = new Intent(Intent.ACTION_MAIN);
             intent.addCategory(Intent.CATEGORY_HOME);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this,Medicion.class);                startActivity(intent);
            }
        });

    }
    public void permiso_GPS(){
        TextView aviso_tv=(TextView) findViewById(R.id.textView3);
        aviso_tv.setText("");
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            aviso_tv.setText("Habilita tu gps");
        }else{
            aviso_tv.setText("");
             locationStart();
        }

    }
    //Apartir de aqui empezamos a obtener la direciones y coordenadas
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Ubicacion_home Local = new Ubicacion_home();
        Local.setMainActivity(home.this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        TextView aviso_tv=(TextView) findViewById(R.id.textView3);
        aviso_tv.setText("");
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    android.location.Address DirCalle = list.get(0);
                    aviso_tv.setText(loc.getLatitude()+","+loc.getLongitude());
                    // Log.d("latitud", String.valueOf(loc.getLatitude()));
                    // Log.d("Longitud", String.valueOf(loc.getLongitude()));
                    // aviso_tv.setText(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}