package com.example.eventoeduteka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class dia2 extends AppCompatActivity {

    Button btnPlenaria,btnSimultanea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dia2 );

        btnPlenaria = findViewById( R.id.btnPlenaria );
        btnSimultanea = findViewById( R.id.btnSimultanea );

        btnSimultanea.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( dia2.this, dia2Simultanea.class );
                startActivity( intent );
            }
        } );


    }
}
