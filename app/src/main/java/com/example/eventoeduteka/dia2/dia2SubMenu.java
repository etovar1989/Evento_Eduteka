package com.example.eventoeduteka.dia2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventoeduteka.R;

public class dia2SubMenu extends AppCompatActivity {

    String tipo,nombre,id;
    TextView dato;
    Button btnCC,btnQR;
    Dialog cuadroDialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dia2_sub_menu );

        tipo = getIntent().getStringExtra( "tipo" );
        nombre = getIntent().getStringExtra( "nombre" );
        id = getIntent().getStringExtra( "id" );

        dato = findViewById( R.id.txtTipo );
        btnCC = findViewById( R.id.btnCC );
        btnQR = findViewById( R.id.btnQR );

        if(tipo.equals( "P" )){
            dato.setText( "Plenaria - " );
        }if(tipo.equals( "S" )){
            dato.setText( "Simultanea \n"+nombre );
        }




        btnCC.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cuadroDialogo = new Dialog( dia2SubMenu.this );
                cuadroDialogo.setContentView( R.layout.dialog_dia1_cc );
                cuadroDialogo.show();
                final EditText txtDcumento = cuadroDialogo.findViewById( R.id.txtResultado );
                final Button btnEnviar = cuadroDialogo.findViewById( R.id.btnEnviar );

                btnEnviar.setEnabled( true );

                btnEnviar.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent( dia2SubMenu.this, dia2Resultado.class );

                        if(txtDcumento.getText().toString().isEmpty()){
                            Toast.makeText(dia2SubMenu.this, "Tienes que llenar el campo documento", Toast.LENGTH_SHORT ).show();
                        }else{
                            intent.putExtra( "documento", txtDcumento.getText().toString() );
                            intent.putExtra( "id",id );
                            intent.putExtra( "tipo",tipo );
                            startActivity( intent );
                            cuadroDialogo.cancel();
                        }


                    }
                } );

            }
        } );

        btnQR.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( dia2SubMenu.this, dia2QR.class );
                intent.putExtra( "tipo",tipo );
                intent.putExtra( "id",id );
                startActivity( intent );

            }
        } );




    }
}
