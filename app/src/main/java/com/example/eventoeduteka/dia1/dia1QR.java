package com.example.eventoeduteka.dia1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eventoeduteka.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class dia1QR extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView myScannerView;

    String documento;
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dia1_q_r );

        myScannerView = new ZXingScannerView( this );
        setContentView( myScannerView );
        myScannerView.setResultHandler( this );
        myScannerView.startCamera();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );

    }


    @Override
    public void handleResult(Result result) {
        documento = result.getText();
        tipo = getIntent().getStringExtra( "tipo" );

        Toast.makeText( this,"Consulta "+documento, Toast.LENGTH_SHORT).show();

        Intent in = new Intent( dia1QR.this, dia1Resultado.class );

        in.putExtra( "documento",documento);
        in.putExtra( "tipo",tipo);

        startActivity( in );
        finish();
    }
}
