package com.example.analisis.Gps;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.analisis.Parcela;

import java.util.List;

public class Ubicacion_parcela implements LocationListener {
    Parcela mainActivity;

    public Parcela getMainActivity() {
        return mainActivity;
    }
    public void setMainActivity(Parcela mainActivity) {
        this.mainActivity = mainActivity;
    }
    @Override
    public void onLocationChanged(Location loc) {
        loc.getLatitude();
        loc.getLongitude();
     
        this.mainActivity.setLocation(loc);//.setLocation(loc);
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Log.d("Gps", "GPS Activado");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Log.d("Gps", "GPS Desactivado");
    }
    // 2 paso  para  que copies en  otro  lado  pero siempre  cuando  son los  que actua  con  tu interfaz
    //lo  dejare comentara 2.1
}

  /* Aqui empieza la Clase Localizacion
  es  lo mismo
    public class Localizacion implements LocationListener {
        Login mainActivity;
        public Login getMainActivity() {
            return mainActivity;
        }
        public void setMainActivity(Login mainActivity) {
            this.mainActivity = mainActivity;
        }
        @Override
        public void onLocationChanged(Location loc) {
            TextView aviso_tv=(TextView) findViewById(R.id.aviso);
            aviso_tv.setText("");
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            String Text = "Lat = "+ loc.getLatitude() + "\n Long = " + loc.getLongitude();
            aviso_tv.setText(Text);
            this.mainActivity.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider) {
            TextView aviso_tv=(TextView) findViewById(R.id.aviso);
            aviso_tv.setText("");
            // Este metodo se ejecuta cuando el GPS es desactivado
            aviso_tv.setText("GPS Desactivado");
        }
        @Override
        public void onProviderEnabled(String provider) {
            TextView aviso_tv=(TextView) findViewById(R.id.aviso);
            aviso_tv.setText("");
            // Este metodo se ejecuta cuando el GPS es activado
            aviso_tv.setText("GPS Activado");
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }

    }
*/

/*
 2.1
   //Apartir de aqui empezamos a obtener la direciones y coordenadas
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Ubicacion_home Local = new Ubicacion_home();
        Local.setMainActivity(Login.this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
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
        TextView aviso_tv=(TextView) findViewById(R.id.aviso);
        aviso_tv.setText("");
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    aviso_tv.setText(loc.getLatitude()+","+loc.getLongitude());
                    Log.d("latitud", String.valueOf(loc.getLatitude()));
                    Log.d("Longitud", String.valueOf(loc.getLongitude()));
                   // aviso_tv.setText(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

 */