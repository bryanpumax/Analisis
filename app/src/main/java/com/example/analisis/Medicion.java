package com.example.analisis;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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

import com.example.analisis.conectar.interacion;
import com.example.analisis.conectar.validacion;
import com.example.analisis.databinding.ActivityMedicionBinding;

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

public class Medicion  extends AppCompatActivity {
    private ActivityMedicionBinding binding;
    Button buttoninsert,buttonlimpiar,buttoneliminar;
    Spinner fk_id_pro,id_ter;
    ArrayList<String> fk_id_pro_value,id_ter_value;
String id_propietario,id_terreno;
    ListView mostrar;
    validacion validaciones;
    EditText temperatura_med,humedad_med,oxigeno_med,observacion_med;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fk_id_pro=(Spinner)findViewById(R.id.fk_id_pro);
        id_ter=(Spinner)findViewById(R.id.id_ter);
        temperatura_med=(EditText) findViewById(R.id.temperatura_med);
        humedad_med=(EditText) findViewById(R.id.humedad_med);
        oxigeno_med=(EditText)findViewById(R.id.oxigeno_med);
        observacion_med=(EditText)findViewById(R.id.observacion_med);
        mostrar=(ListView) findViewById(R.id.mostrarlist);
        buttoninsert=(Button) findViewById(R.id.buttoninsert);
        buttonlimpiar=(Button) findViewById(R.id.buttonlimpiar);
        buttoneliminar=(Button) findViewById(R.id.buttonatras);
        mostrar_lista();
        fk_id_pro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             id_propietario=fk_id_pro_value.get(i);
                String variable3 = "dato=spinnerterreno&id_propietario="+id_propietario;
                spinnerterreno(variable3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        id_ter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_terreno=id_ter_value.get(i);
                String variable3 = "dato=medicion&id_propietario="+id_propietario+"&id_terreno="+id_terreno;
                Sql_consulta_mostrar(variable3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        buttoninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            insertar_validar_enviar();
                limpiar_caja();
                mostrar_lista();
                Toast.makeText(Medicion.this,"Seleccione propietario y terreno",Toast.LENGTH_LONG).show();
            }});


        buttonlimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {limpiar_caja();                mostrar_lista();}});
        buttoneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {mostrar_lista();atras();}        });
    }
    public  void  limpiar_caja(){
        humedad_med.setText("");
        temperatura_med.setText("");
        oxigeno_med.setText("");
        observacion_med.setText("");


    }
    public  void  atras(){    Intent intent = new Intent(Medicion.this, home.class);    startActivity(intent);}
    public  void mostrar_lista() {
      String variable2 = "dato=spinnerpropietario";
        spinner_propietario(variable2);

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
    public void spinnerterreno( String variable){
        //fk_id_pro;
        ArrayList<String> names;
        names=new ArrayList<String>() ;
        id_ter_value=new ArrayList<String>();
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
                id_ter_value.add(jsonObject.optString("id_ter"));
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
        id_ter.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,names));

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
    public void insertar_validar_enviar (){
        String temperatura_meds=validacion.limpio_espacios(temperatura_med.getText().toString());
      String  humedad_meds=validacion.limpio_espacios(humedad_med.getText().toString());
        String oxigeno_meds=validacion.limpio_espacios(oxigeno_med.getText().toString());
        String observacion_meds=validacion.limpio_espacios(observacion_med.getText().toString());
        if (validaciones.vacio_texto(temperatura_meds)=="falso"){
            temperatura_med.setText("");temperatura_med.requestFocus();return;
        }
        if (validaciones.vacio_texto(humedad_meds)=="falso"){
            humedad_med.setText("");humedad_med.requestFocus();return;
        }
        if (validaciones.vacio_texto(oxigeno_meds)=="falso"){
            oxigeno_med.setText("");oxigeno_med.requestFocus();return;
        }
        if (validaciones.vacio_texto(observacion_meds)=="falso"){
            observacion_med.setText("");observacion_med.requestFocus();return;
        }

        String variable="dato=medicion_r&id_terreno="+id_terreno+"&temperatura_med="+temperatura_meds+"&humedad_med="+humedad_meds+"&id_produccion="+
                "&oxigeno_med="+oxigeno_meds+"&observacion_med="+observacion_meds;
        TextView textView5=(TextView)findViewById(R.id.textView5);
        //Log.d("variable_medicion",variable);
        textView5.setText(interacion.Sql_consulta_post(this,variable));
    }
}
