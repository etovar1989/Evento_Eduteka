package com.example.eventoeduteka.dia1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.eventoeduteka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dia1Resultado extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>{

    TextView txtResultado;
    TextView dato;
    String documento, tipo;

    int dato1=0;

    RequestQueue rq;
    JsonRequest jrq;

    Button btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dia1_resultado );


        documento =  getIntent().getStringExtra("documento");
        tipo =  getIntent().getStringExtra("tipo");

        txtResultado = findViewById( R.id.txtResultado );
        dato = findViewById( R.id.txtTipo );
        btnCerrar = findViewById( R.id.btnCerrar );

        txtResultado.setVisibility( View.INVISIBLE );

        rq = Volley.newRequestQueue(this);
        validarCedula(documento, tipo);

        if(tipo.equals( "2" )){
            dato.setText( "Sustentación" );
        }if(tipo.equals( "3" )){
            dato.setText( "Plenaria" );
        }

        btnCerrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );


    }


    private void validarCedula(String documento, String tipo ) {
        String url="https://www.eventoeduteka.com/2020/complementosApk/dia1.php?idU="+documento+"&idAct="+tipo;
        jrq = new JsonObjectRequest( Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        alertErrorRecord();
        Toast.makeText(dia1Resultado.this, error.toString(), Toast.LENGTH_SHORT ).show();
    }


    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonArray = response.optJSONArray( "datos" );
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject( 0 );

            dato1 = Integer.parseInt( jsonObject.optString( "validador" ) );

            txtResultado.setVisibility( View.VISIBLE );
            if(dato1 == 1){
                //Toast.makeText( this,"Se ha realizado el primer registro con exito", Toast.LENGTH_SHORT).show();
                txtResultado.setText( "Registro exitoso, puede ingresar a la conferencia." );
            }if(dato1 == 20){
                txtResultado.setText( "Oops!! El asistente con cc "+documento+" fue registrado anteriormente -_-." + dato1);
                txtResultado.setTextColor( Color.rgb( 176,133,8 ));
            }if(dato1 == 21){
                txtResultado.setText( "Upps!! El asistente no realizó el registro general, por favor dirígelo a registro general -_-." );
                txtResultado.setTextColor( Color.RED);
            }if(dato1 == 22){
                txtResultado.setText( "Upps!! No re enviaron datos, comunicate con el administrador -_-." );
                txtResultado.setTextColor( Color.RED);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /*
     * AlertDialog porblema con el registro del usuario
     */
    public void alertErrorRecord() {

        new AlertDialog.Builder( dia1Resultado.this)
                .setTitle("Opss")
                .setMessage("Hubo un problema con el registro, por favor comunicarse con Elisxenth")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }



}
