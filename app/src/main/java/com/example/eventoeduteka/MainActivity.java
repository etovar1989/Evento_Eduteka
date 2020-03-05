package com.example.eventoeduteka;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtUsuario,edtClave;
    Button btnLogin;

    String usuario;
    String clave;
    String nombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        edtUsuario = findViewById( R.id.edtUsuario );
        edtClave = findViewById( R.id.edtClave );
        btnLogin = findViewById( R.id.btnLogin );


        recuperarPreferencias();

        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = edtUsuario.getText().toString();
                clave = edtClave.getText().toString();


                if (!usuario.isEmpty() && !clave.isEmpty()){
                    validarUsuario( "https://www.eventoeduteka.com/2020/complementosApk/validar_usuario.php" );
                }else{
                    alertErrorLogin3();
                }

            }
        } );


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
        }


    }


    private void validarUsuario(String URL){
        StringRequest stringRequest=new StringRequest( Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    nombres = response.toString();
                    String[] parts = response.split( "," );
                    String[] n = parts[1].split( ":" );
                    nombres = n[1];
                    nombres = nombres.replaceAll("\"","");
                    guardarPreferencias();

                    Intent intent =new Intent( getApplicationContext(), Menu.class );
                    startActivity( intent );
                    finish();

                }else{
                    alertErrorLogin1();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alertErrorLogin2();
                //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT ).show();

            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();
                //parametros.put( "usuario",edtUsuario.getText().toString() );
                //parametros.put( "clave",edtClave.getText().toString() );
                parametros.put( "usuario",usuario );
                parametros.put( "clave",clave );



                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }




    /*
     * guarda los datos de referencia para el autologion
     * */

    private void guardarPreferencias(){
        SharedPreferences preferences=getSharedPreferences( "preferenciasLogin", Context.MODE_PRIVATE );
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString( "usuario",usuario );
        editor.putString( "clave",clave );
        editor.putString( "nombres",nombres );
        editor.putBoolean( "sesion",true );
        editor.commit();
    }

    /*
     * Recupera los datos del usuario
     * */
    private void recuperarPreferencias(){
        SharedPreferences preferences=getSharedPreferences( "preferenciasLogin", Context.MODE_PRIVATE );
        edtUsuario.setText( preferences.getString( "usuario","" ) );
        edtClave.setText( preferences.getString( "clave","" ) );
    }




    /*
     * AlertDialog de error por usuario y clave.
     */
    public void alertErrorLogin1() {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Error en el ingreso")
                .setMessage("Usaurio o clave, no estan correctos por favor intetalo de nuevo")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }


    /*
     * AlertDialog problema de conexion o error de codigo.
     */
    public void alertErrorLogin2() {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Oops")
                .setMessage("Hubo un problema con tu ingreso, por favor comunicarse con Elisxenth")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }


    /*
     * AlertDialog campos vacios.
     */
    public void alertErrorLogin3() {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("-_-")
                .setMessage("Ni el usuario ni la clave deben de estar vacios, coja oficio")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }




}
