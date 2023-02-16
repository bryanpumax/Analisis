package com.example.analisis.conectar;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class validacion {
    public static String limpio_espacios(final String texto){
        String textos=texto.trim();
        return remplazar_simbolos(textos);
    }
    public static String vacio_texto(final  String texto){
        int cantidad=texto.length();
        if (cantidad>0){
            return "verdad";
        }
        return "falso";
    }
    public  static String remplazar_simbolos(final String texto){
        String textos=texto.replace("@","");
        textos=texto.replace("+","");
        textos=texto.replace("#","");
        textos=texto.replace(".","");
        textos=texto.replace("=","");
        textos=texto.replace("?","");
        textos=texto.replace("'","");
        textos=texto.replace("\"","");
        textos=texto.replace("/","");
        textos=texto.replace("-","");
        textos=texto.replace(",","");
        textos=texto.replace("?","");

        return textos;
    }
    public static String aviso_internet( ConnectivityManager connectivityManager){
       String Mensaje="";

        if (Boolean.toString(isNetDisponible(connectivityManager))=="false"&& Boolean.toString(isOnlineNet())=="false" ){

            Mensaje=("Activa el internet ");

        }
return Mensaje;
    }
    public static boolean isNetDisponible(final  ConnectivityManager connectivityManager) {


       // ConnectivityManager connectivityManager1= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }
    public static  Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
