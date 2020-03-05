package com.example.eventoeduteka.dia1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eventoeduteka.R;

public class dia1 extends AppCompatActivity {

    Button btnD1S, btnD1P;
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dia1 );

        btnD1S = findViewById( R.id.btnD1S );
        btnD1P = findViewById( R.id.btnD1P );

        /*tipo = 2 sustentacion dia miercoles en la mañana y tarde*/

        btnD1S.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipo = "2";
                Intent intent = new Intent( dia1.this, dia1SubMenu.class );
                intent.putExtra( "tipo",tipo );
                startActivity( intent );
            }
        } );

        /*tipo = 3 plenaria dia miercoles en la tarde*/
        btnD1P.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipo = "3";
                Intent intent = new Intent( dia1.this,dia1SubMenu.class );
                intent.putExtra( "tipo",tipo );
                startActivity( intent );
            }
        } );

    }
}
