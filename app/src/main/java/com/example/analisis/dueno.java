package com.example.analisis;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.analisis.conectar.Local;
import com.example.analisis.conectar.interacion;
import com.example.analisis.conectar.validacion;
import com.example.analisis.databinding.ActivityDuenoBinding;

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

public class dueno extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityDuenoBinding binding;
    EditText identificacion_pro,apellido_pro,nombre_pro,correo,telefono;
    Button buttoninsert,buttonlimpiar,buttoneliminar;
    public  interacion interaciones;
      Local locales;
     validacion validaciones;
     ListView mostrar;
    String  identificacion_pros,apellido_pros,nombre_pros,correos,telefonos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDuenoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        identificacion_pro=(EditText) findViewById(R.id.identificacion_pro);
        apellido_pro=(EditText) findViewById(R.id.apellido_pro);
        nombre_pro=(EditText) findViewById(R.id.nombre_pro);
        correo=(EditText) findViewById(R.id.correos);
        telefono=(EditText) findViewById(R.id.telefonos);
        buttoninsert=(Button) findViewById(R.id.buttoninsert);
        buttonlimpiar=(Button) findViewById(R.id.buttonlimpiar);
        buttoneliminar=(Button) findViewById(R.id.buttonatras);
        mostrar=(ListView) findViewById(R.id.mostrarlist);

        limpiar_caja();mostrar_lista();
buttoninsert.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        insertar_validar_enviar();
        limpiar_caja();
        mostrar_lista();
    }
});
        buttonlimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar_caja();
                mostrar_lista();
            }
        });
        buttoneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrar_lista();
                atras();

            }
        });
    }
    public void insertar_validar_enviar (){


        identificacion_pros=validaciones.limpio_espacios(identificacion_pro.getText().toString());
        apellido_pros=validaciones.limpio_espacios(apellido_pro.getText().toString());
        nombre_pros=validaciones.limpio_espacios(nombre_pro.getText().toString());
        correos=validaciones.limpio_espacios(correo.getText().toString());
        telefonos=validaciones.limpio_espacios(telefono.getText().toString());
        if (validaciones.vacio_texto(identificacion_pros)=="falso"){
            identificacion_pro.setText("");
            identificacion_pro.requestFocus();
            return;
        }
        if (validaciones.vacio_texto(apellido_pros)=="falso"){
            apellido_pro.setText("");
            apellido_pro.requestFocus();
            return;
        }
        if (validaciones.vacio_texto(nombre_pros)=="falso"){
            nombre_pro.setText("");
            nombre_pro.requestFocus();
            return;
        }
        if (validaciones.vacio_texto(correos)=="falso"){
            correo.setText("");
            correo.requestFocus();
            return;
        }
        if (!correos.contains("@")){
            correo.setText("");
            correo.requestFocus();
            return;
        }
        if (validaciones.vacio_texto(telefonos)=="falso"){
            telefono.setText("");
            telefono.requestFocus();
            return;
        }
        boolean isNumericidentificacion_pros =  identificacion_pros.matches("[+-]?\\d*(\\.\\d+)?");
        boolean isNumerictelefonos =  telefonos.matches("[+-]?\\d*(\\.\\d+)?");
        if (!isNumericidentificacion_pros){
            identificacion_pro.setText("");
            identificacion_pro.requestFocus();
            return;
        }
        if (!isNumerictelefonos){
            telefono.setText("");
            telefono.requestFocus();
            return;
        }
        String variable="dato=propetario_r&identificacion_pro="+identificacion_pros+"&apellido_pro="+apellido_pros+"&" +
                "nombre_pro="+nombre_pros+"&email_pro="+correos+"&telefono_pro="+telefonos;
        TextView textView5=(TextView)findViewById(R.id.textView5);
        textView5.setText(interacion.Sql_consulta(this,variable));
    }
public  void  limpiar_caja(){

    identificacion_pro.setText("");
          apellido_pro.setText("");
        nombre_pro.setText("");
        correo.setText("");
        correo.setText("");
        telefono.setText("");
    }
public  void  atras(){
    Intent intent = new Intent(dueno.this, home.class);
    startActivity(intent);
}
public  void mostrar_lista(){

    String variable="dato=propietario";
    Sql_consulta_mostrar(this,variable);

}
    public void Sql_consulta_mostrar (Context context, String variable){
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
}
