package com.example.analisis.conectar;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

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

public class interacion {

    private static com.example.analisis.conectar.Local Local;

    public static String login_consulta(Context context, final String user, final String pass){
         Local locales=(Local);
        String urls="https://agriculturautc.com/movil.php?";
        String variable="";
        urls+="dato=login&";
        urls+="user="+user+"&pass="+pass;
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
            String nombre_usu="",apellido_usu="";
            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                variable=jsonObject.optString("estado");
                nombre_usu=jsonObject.optString("nombre_usu");
                apellido_usu=jsonObject.optString("apellido_usu");
                locales.almacenar_share(context,"nombre_usu",nombre_usu);
                locales.almacenar_share(context,"apellido_usu",apellido_usu);
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
        return variable;
    }
    public static  String Sql_consulta (Context context,String variable){
        String mensaje="";
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
                mensaje=jsonObject.optString("estado");
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
        Log.d("mensaje_insertar_bdd",mensaje);
        return mensaje;
    }
    public static  String Sql_consulta_post (Context context,String variable){
        String mensaje="";
        String urls="https://agriculturautc.com/movil.php?"+variable;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        HttpURLConnection conn;
        try {
            url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET" +
                    "");

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
                mensaje=jsonObject.optString("estado");
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
        Log.d("mensaje_insertar_bdd",mensaje);
        return mensaje;
    }
    /*
    public static String Sql_consulta_mostrar (Context context, String variable){
        ArrayList<String> mensaje;
        mensaje=new ArrayList<String>();
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
                mensaje.add(jsonObject.optString("mostrar"));
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

        return mensaje;
    }
*/

}
