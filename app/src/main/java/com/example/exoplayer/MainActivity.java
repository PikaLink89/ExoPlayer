package com.example.exoplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer reproductor;
    private String botonurl;
    ExtractorMediaSource archivoMultimedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        botonurl="";
        playerView = findViewById(R.id.playerview);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Alcomenzar el mainActivity, creamos la instancia al reproductor
        //creamos el reproductor
        reproductor = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        //Asignamos a la view el reproductor
        playerView.setPlayer(reproductor);


    }



    @Override
    protected void onStop() {
        //Al finalizar el progama, eliminamos el objeto
        super.onStop();
        //Le quitamos al view el reproductor
        playerView.setPlayer(null);
        //Eliminamos el objeto
        reproductor.release();
        ;
    }

//Cuando se hace click al botón reproducir url, se genera un alert dialog y envía al metodo reproducir el link
    public void checkURL(View view){

            final EditText cajaTexto = new EditText(this);
            final Toast toast2 = Toast.makeText(this, "No puede estar en blanco", Toast.LENGTH_LONG);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("URL")
                    .setMessage("Dirección de enlace")
                    //Mostramos la caja de texto creada
                    .setView(cajaTexto)
                    //Si pulsa el botón positivo (Añadir) le hacemos un listener
                    .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (cajaTexto.getText().toString().equals("")) { //Comprueba que no esté en blanco

                                toast2.show();
                            } else {
                                botonurl = cajaTexto.getText().toString();
                                reproducir(botonurl);
                            }

                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .create();
            dialog.show();

    }

    public void reproducir(String url){

        //Origen de datos
        DefaultDataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "ExoPlayer"));

        //El archivo multimedia a partir de una URL, origen del medio
        archivoMultimedia = new ExtractorMediaSource
                .Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(botonurl));
        //Meter el archivo al reproductor y ponerlo en marcha
        reproductor.prepare(archivoMultimedia);
        reproductor.setPlayWhenReady(true);

    }

}


