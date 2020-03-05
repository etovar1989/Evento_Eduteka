package com.example.eventoeduteka;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class dia2Simultanea extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextView dato;
    private Spinner spT;
    private AsyncHttpClient cliente;
    String nombre,pocision,tipo;
    Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dia2_sub_menu );

        dato = findViewById( R.id.txtBloque );
        tipo="S";

        btnContinuar = findViewById( R.id.btnContinuar );

        spT = findViewById( R.id.spConferencias );
        cliente = new AsyncHttpClient( );

        spT.setOnItemSelectedListener(this);

        btnContinuar.setEnabled(false);




        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder( dia2Simultanea.this);
        builder.setTitle("Seleciona un bolque");

        // add a list
        String[] bloque = {"Bloque 1", "Bloque 2"};
        builder.setItems(bloque, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Blaque 1
                        dato.setText( "Bloque 1" );
                        llenatSpinner();
                        btnContinuar.setEnabled(true);
                        break;
                    case 1: // Blaque 2
                        dato.setText( "Bloque 2" );
                        llenatSpinner();
                        btnContinuar.setEnabled(true);
                        break;
                    default:
                        dato.setText( "Bloque 1" );
                        llenatSpinner();
                        break;
                }
            }
        });


        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        btnContinuar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText( dia2Simultanea.this,"Conferencia: "+nombre+"Posicion: "+pocision,Toast.LENGTH_LONG ).show();
                Intent intent = new Intent( dia2Simultanea.this,dia2SubMenu.class );
                intent.putExtra( "tipo",tipo );
                intent.putExtra( "nombre",nombre );
                intent.putExtra( "id",pocision );
                startActivity( intent );
            }
        } );



    }




    private void llenatSpinner() {
        String url="http://edukatic.icesi.edu.co/complementos_apk/d2Conferencia.php";
        cliente.post( url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    cargarSpinner(new String(responseBody));
                    //Toast.makeText(dia2SubMenu.this,new String(responseBody), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        } );
    }


    private void cargarSpinner(String respuesta) {
        ArrayList<Conferencias> lista = new ArrayList<Conferencias>();

        try{
            JSONArray jsonArreglo = new JSONArray( respuesta );
            for(int i=0; i< jsonArreglo.length(); i++){

                Conferencias t = new Conferencias();
                t.setNombre(jsonArreglo.getJSONObject( i ).getString("idConferencia")+" - "+ jsonArreglo.getJSONObject( i ).getString("nombreConferencia"));
                lista.add( t );
            }

            ArrayAdapter<Conferencias> a = new ArrayAdapter<Conferencias>(this,android.R.layout.simple_dropdown_item_1line,lista);
            spT.setAdapter( a );


        }catch (Exception e){

        }

    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Texto
        nombre = parent.getItemAtPosition(position).toString();
        //Posicion
        String string = nombre;
        String[] parts = string.split("-");
        pocision = parts[0];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
