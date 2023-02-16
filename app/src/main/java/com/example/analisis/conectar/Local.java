package com.example.analisis.conectar;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Local {

    public static String almacenar_share(Context context, final String llave,final String valor){
        SharedPreferences sharedPreferenceses = context.getSharedPreferences("ArchivoSp",context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferenceses.edit();
        editor.putString(llave,valor);
        editor.commit();
      return "Guardado "+llave+" valor:"+valor;
    }
    public static String ver_share(Context context, final String llave){
        SharedPreferences sharedPreferenceses = context.getSharedPreferences("ArchivoSp",context.MODE_PRIVATE);
        String Valor=sharedPreferenceses.getString(llave,"");
        Log.d(llave,Valor);
        return Valor;
    }
}
