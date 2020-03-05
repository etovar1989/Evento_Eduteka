package com.example.eventoeduteka;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventoeduteka.dia1.dia1;

public class Menu extends AppCompatActivity {

    private TextView txtNombre;
    Button btnD1,btnD2,btnD3,btnConsultar;

    private final int SOLICTUD_PERMISO_CAMARA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_menu );

        SharedPreferences preferences=getSharedPreferences( "preferenciasLogin", Context.MODE_PRIVATE );
        String nombres = preferences.getString( "nombres","NN" );

        txtNombre = (TextView)findViewById( R.id.txtNombre );
        txtNombre.setText( nombres );
        Toast.makeText(Menu.this, "Bienvenido "+nombres+" que la fuerza te acompañe, joven padawan", Toast.LENGTH_SHORT ).show();


        btnD1 = findViewById( R.id.btnD1 );
        btnD2 = findViewById( R.id.btnD2 );

        btnD1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Menu.this, dia1.class );
                startActivity( intent );
            }
        } );

        btnD2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Menu.this, dia2.class );
                startActivity( intent );
            }
        } );



        if(ActivityCompat.checkSelfPermission( this, Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText( this,"Gracias por tu confianza, que la fuerza te acompañe", Toast.LENGTH_SHORT).show();
        }else{
            explicarPermiso();
            solictarPermisoCamara();

        }


    }






    /* Barra de menu de la apk*/
    public boolean onCreateOptionsMenu (android.view.Menu mimenu){
        getMenuInflater().inflate(R.menu.barra_menu, mimenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem opcion_menu){
        int id=opcion_menu.getItemId();


        if(id==R.id.salir){
            SharedPreferences preferences=getSharedPreferences( "preferenciasLogin", Context.MODE_PRIVATE );
            preferences.edit().clear().commit();

            Intent intent =new Intent( getApplicationContext(), MainActivity.class );
            startActivity( intent );
            finish();
        }


        return super.onOptionsItemSelected(opcion_menu);
    }

    /*Fin Barra de menu de la apk*/




    private void solictarPermisoCamara() {
        //0. pedir permiso con cuadros de dialogo del sistema
        ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.CAMERA}, SOLICTUD_PERMISO_CAMARA );
        Toast.makeText( this,"Permiso camara", Toast.LENGTH_SHORT).show();

    }

    private void explicarPermiso() {
        Toast.makeText( this,"Los permisos son requeridos para el correcto funcionamiento de la aplicación.", Toast.LENGTH_SHORT).show();
        //alertDialogoBasico();
    }

    private void alertDialogoBasico() {
        //1. Instanciar el AlertDialog.Builder con este constructor
        AlertDialog.Builder builder = new AlertDialog.Builder( this );

        //2. Encadenar varios metodos setter para ajustar las caracteristicas del dialogo
        builder.setMessage( "Es necesario los permisos para la cámara, si quieres que se puedan leer los códigos QR. Por favor presiona ‘OK’" );

        builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        } );
        builder.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );

        if(requestCode == SOLICTUD_PERMISO_CAMARA){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText( this,"Gracias por tu confianza, que la fuerza te acompañe", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText( this,"No se otorgaron los permisos a la cámara, la aplicación no funcionara en su totalidad, no se podrán hacer la lectura de códigos QR. ", Toast.LENGTH_SHORT).show();
            }
        }

    }




    /*
     * AlertDialog de error por usuario y clave.
     */
    public void msjBiembenida( String nombre) {

        new AlertDialog.Builder(Menu.this)
                .setTitle("Bienvenido")
                .setMessage("Hola " + nombre + "que la fuerza te acompañe")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }


}
