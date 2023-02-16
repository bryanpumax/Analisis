package com.example.analisis;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.analisis.conectar.Local;
import com.example.analisis.conectar.interacion;
import com.example.analisis.conectar.validacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.analisis.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
private interacion interaciones;
private Local locales;
private validacion validaciones;
//gps

    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageButton button = (ImageButton) findViewById(R.id.envio_btn);
        TextView aviso_tv=(TextView) findViewById(R.id.aviso);
        TextView aviso_tv2=(TextView) findViewById(R.id.aviso);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
aviso_tv2.setText(validacion.aviso_internet(connectivityManager));

      permiso_GPS();



button.setOnClickListener(new View.OnClickListener() {


    @Override
    public void onClick(View view) {
        aviso_tv.setText("");
        EditText user =(EditText) findViewById(R.id.user_edit);
        EditText pass =(EditText) findViewById(R.id.pass_edit);
        String usu=(user.getText().toString());
        String constr=pass.getText().toString();
        usu=validaciones.limpio_espacios(usu);
        user.setText(usu);
       if (validaciones.vacio_texto(usu)=="falso"){
            user.setText("");
            user.requestFocus();
            return;
        }
        constr=validaciones.limpio_espacios(constr);
        pass.setText(constr);

        if (validaciones.vacio_texto(constr)=="falso"){
            pass.setText("");
            pass.requestFocus();
            return;
        }
        locales.almacenar_share(context,"user",usu);
        locales.almacenar_share(context,"pass",constr);
        String estado=interaciones.login_consulta(context,usu,constr);
 if (estado=="true"){
     Intent intent = new Intent(Login.this, home.class);
     startActivity(intent);
 }else {
     aviso_tv2.setText("Usuario o contraseña incorrecta");
 }
    }
});

    }

    public void permiso_GPS(){
        TextView aviso_tv=(TextView) findViewById(R.id.aviso);
        aviso_tv.setText("");
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
aviso_tv.setText("Habilita tu gps");
        }else{
            aviso_tv.setText("");
          //  locationStart();
        }

    }




}