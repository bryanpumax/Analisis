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
import android.os.StrictMode;
import android.provider.Settings;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.analisis.Gps.Ubicacion_terreno;
import com.example.analisis.conectar.Local;
import com.example.analisis.conectar.interacion;
import com.example.analisis.conectar.validacion;
import com.example.analisis.databinding.ActivityTerrenoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class terreno extends AppCompatActivity {
    private ActivityTerrenoBinding binding;
    EditText lat_ter,lng_ter,descripcion_ter;
    TextView textView5;
    Spinner fk_id_pro,fk_id_par,fk_id_bar,fk_id_produc,tiempo_cosecha_ter;
    Button buttoninsert,buttonlimpiar,buttoneliminar;
    public  interacion interaciones;
      Local locales;
     validacion validaciones;
    ArrayList<String> fk_id_pro_value,fk_id_par_value,fk_id_bar_value,fk_id_produc_value,tiempo_cosecha_ter_value;

     ListView mostrar;
    String  id_propietarios,id_parroquia="1",id_barrio,id_produccion,id_tiempo,lat_ters,lng_ters,descripcion_ters;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTerrenoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mostrar=(ListView) findViewById(R.id.mostrarlist);
        fk_id_pro=(Spinner)findViewById(R.id.fk_id_pro);
        fk_id_par=(Spinner)findViewById(R.id.fkidpar);
        fk_id_bar=(Spinner)findViewById(R.id.fk_id_bar);
        fk_id_produc=(Spinner) findViewById(R.id.fkproduc);
        tiempo_cosecha_ter=(Spinner) findViewById(R.id.tiempo_cosecha_ter) ;
        lat_ter=(EditText)findViewById(R.id.lat_ter);
        lng_ter=(EditText) findViewById(R.id.lng_ter);
        descripcion_ter=(EditText)findViewById(R.id.descripcion_ter);
        textView5=(TextView)findViewById(R.id.textView5);
        buttoninsert=(Button) findViewById(R.id.buttoninsert);
        buttonlimpiar=(Button) findViewById(R.id.buttonlimpiar);
        buttoneliminar=(Button) findViewById(R.id.buttonatras);

     mostrar_lista();

      fk_id_pro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            id_propietarios=fk_id_pro_value.get(i);
          }
          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {          }      });
      fk_id_par.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              id_parroquia=fk_id_par_value.get(i);              String variable="dato=spinnerbarrio&par="+id_parroquia; spinner_barrio(variable);          }
          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {          }});
fk_id_bar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {id_barrio=fk_id_bar_value.get(i);    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}});
fk_id_produc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {id_produccion=fk_id_produc_value.get(i);}
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}});
tiempo_cosecha_ter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {id_tiempo=tiempo_cosecha_ter_value.get(i);}
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}});
buttoninsert.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {        insertar_validar_enviar();        limpiar_caja();        mostrar_lista();    }});


        buttonlimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {limpiar_caja();                mostrar_lista();}});
        buttoneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {mostrar_lista();atras();}        });

    }
    public void insertar_validar_enviar (){
         lng_ters= lng_ter.getText().toString();
         lat_ters=lat_ter.getText().toString();
 descripcion_ters=descripcion_ter.getText().toString();


        String variable="dato=terreno_r&id_propietario="+id_propietarios+"&id_parroquia="+id_parroquia+"&id_barrio="+id_barrio+"&id_produccion="+
                id_produccion+"&id_tiempo="+id_tiempo+"&lng_ter="+lng_ters+"&lat_ters="+lat_ters+"&descripcion_ters="+descripcion_ters;

        Log.d("varterreno_r",(variable));

        TextView textView5=(TextView)findViewById(R.id.textView5);

       Log.d("terreno_r",interacion.Sql_consulta_post(this,variable));
   //textView5.setText(interacion.Sql_consulta(this,variable));
    }
public  void  limpiar_caja(){

descripcion_ter.setText("");

    }
public  void  atras(){    Intent intent = new Intent(terreno.this, home.class);    startActivity(intent);}
public  void mostrar_lista() {
    permiso_GPS();
    String variable1 = "dato=terreno";
    Sql_consulta_mostrar(variable1);
    String variable2 = "dato=spinnerpropietario";
    spinner_propietario(variable2);
   String variable3 = "dato=spinnerparroquia";
    spinner_parroquia(variable3);
    String variable4 = "dato=spinnerproducion";
    spinnerproducion(variable4);


    spinner_tiempo_consecha();
}
    public void Sql_consulta_mostrar (  String variable){
        ArrayList<String> names;
        names=new ArrayList<String>() ;
        String urls="https://agriculturautc.com/movil.php?"+variable;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json = "";
            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            json = response.toString();

            JSONArray jsonArr = null;

            jsonArr = new JSONArray(json);

            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);
              names.add(jsonObject.optString("mostrar"));
            }
        }  catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
       mostrar.setAdapter(adapter);
    }
    public void spinner_propietario(  String variable){
        //fk_id_pro;
        ArrayList<String> names;
        names=new ArrayList<String>() ;
        fk_id_pro_value=new ArrayList<String>();
        String urls="https://agriculturautc.com/movil.php?"+variable;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json = "";
            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            json = response.toString();

            JSONArray jsonArr = null;

            jsonArr = new JSONArray(json);

            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                names.add(jsonObject.optString("mostrar"));
                fk_id_pro_value.add(jsonObject.optString("id_pro"));
            }
        }  catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        fk_id_pro.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,names));

    }
    public void spinner_parroquia( String variable){
        //fk_id_pro;
        ArrayList<String> names;
        names=new ArrayList<String>() ;
        fk_id_par_value=new ArrayList<String>();
        String urls="https://agriculturautc.com/movil.php?"+variable;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json = "";
            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            json = response.toString();

            JSONArray jsonArr = null;

            jsonArr = new JSONArray(json);

            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                names.add(jsonObject.optString("mostrar"));
                fk_id_par_value.add(jsonObject.optString("id_par"));
            }
        }  catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        fk_id_par.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,names));

    }


    public void spinner_barrio( String variable){
        //fk_id_pro;
        ArrayList<String> names;
        names=new ArrayList<String>() ;
        fk_id_bar_value=new ArrayList<String>();
        String urls="https://agriculturautc.com/movil.php?"+variable;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        HttpURLConnection conn;
        BufferedReader in =null;
        try {
            url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d("otro", "http response code is " + conn.getResponseCode());
                return ;
            }
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json = "";
            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            json = response.toString();

            JSONArray jsonArr = null;

            jsonArr = new JSONArray(json);

            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                names.add(jsonObject.optString("mostrar"));
                fk_id_bar_value.add(jsonObject.optString("id_bar"));
            }


        }  catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
      fk_id_bar.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,names));

    }
    public void spinnerproducion( String variable){
        //fk_id_pro;
        ArrayList<String> names;
        names=new ArrayList<String>() ;
        fk_id_produc_value=new ArrayList<String>();
        String urls="https://agriculturautc.com/movil.php?"+variable;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        HttpURLConnection conn;
        BufferedReader in =null;
        try {
            url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d("otro 383", "http response code is " + conn.getResponseCode());
                return ;
            }
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
              in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json = "";
            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            json = response.toString();

            JSONArray jsonArr = null;

            jsonArr = new JSONArray(json);

            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                names.add(jsonObject.optString("mostrar"));
                fk_id_produc_value.add(jsonObject.optString("id_produc"));
            }
        }  catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        fk_id_produc.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,names));

    }
    public void spinner_tiempo_consecha( ){

      ArrayList<String> names;
        names=new ArrayList<String>() ;
        tiempo_cosecha_ter_value=new ArrayList<String>();
        for (int i = 1; i < 13; i++) {
            names.add(i+" meses");
            tiempo_cosecha_ter_value.add(String.valueOf(i));
            tiempo_cosecha_ter.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,names));
        }

    }
    public void permiso_GPS(){

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);

        }else{
            locationStart();

        }

    }
    //Apartir de aqui empezamos a obtener la direciones y coordenadas
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Ubicacion_terreno Local = new Ubicacion_terreno();
        Local.setMainActivity(terreno.this);
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
              //  locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
      EditText lat_ter=(EditText) findViewById(R.id.lat_ter);
      EditText lng_ter=(EditText) findViewById(R.id.lng_ter);
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    android.location.Address DirCalle = list.get(0);
                    String latitud=  String.valueOf(loc.getLatitude());
                    String longitud=String.valueOf(loc.getLongitude());
                    lat_ter.setText( latitud.toString());
                    lng_ter.setText(longitud.toString());
                   return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
