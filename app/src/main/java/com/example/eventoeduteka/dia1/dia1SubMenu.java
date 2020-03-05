package com.example.eventoeduteka.dia1;

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

public class dia1SubMenu extends AppCompatActivity {

    String tipo;
    TextView dato;
    Button btnCC,btnQR;
    Dialog cuadroDialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dia1_sub_menu );

        tipo = getIntent().getStringExtra( "tipo" );

        dato = findViewById( R.id.txtTipo );
        btnCC = findViewById( R.id.btnCC );
        btnQR = findViewById( R.id.btnQR );

        if(tipo.equals( "2" )){
            dato.setText( "Sustentación" );
        }if(tipo.equals( "3" )){
            dato.setText( "Plenaria" );
        }


        btnCC.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cuadroDialogo = new Dialog( dia1SubMenu.this );
                cuadroDialogo.setContentView( R.layout.dialog_dia1_cc );
                cuadroDialogo.show();
                final EditText txtDcumento = cuadroDialogo.findViewById( R.id.txtResultado );
                final Button btnEnviar = cuadroDialogo.findViewById( R.id.btnEnviar );

                btnEnviar.setEnabled( true );

                btnEnviar.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent( dia1SubMenu.this, dia1Resultado.class );

                        if(txtDcumento.getText().toString().isEmpty()){
                            Toast.makeText(dia1SubMenu.this, "Tienes que llenar el campo documento", Toast.LENGTH_SHORT ).show();
                        }else{
                            intent.putExtra( "documento", txtDcumento.getText().toString() );
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
                Intent intent = new Intent( dia1SubMenu.this, dia1QR.class );
                intent.putExtra( "tipo",tipo );
                startActivity( intent );

            }
        } );


    }
}
