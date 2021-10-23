package edu.upc.dsa.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<Track> trackList;
    SwaggerAPI swaggerAPI;
    Track track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://192.168.0.87:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        swaggerAPI= retrofit.create(SwaggerAPI.class);
        Boolean newTrack = newTrack();
        if(newTrack!=null&&newTrack){//Si se ha añadido un nuevo track
            addTrack();
            Toast.makeText(this,"ADD SUCCESSFUL",Toast.LENGTH_LONG).show();
        }else if(newTrack!=null){//Modifica track
            editTrack();
            Toast.makeText(this,"EDIT SUCCESSFUL",Toast.LENGTH_LONG).show();
        }else if(delete()) {
            Toast.makeText(this,"DELETE SUCCESSFUL",Toast.LENGTH_LONG).show();
        }else{getAllTracks();}

    }

    /***********************************************************
     * OPERACIONES CON LA API
     ***********************************************************/
    private void getAllTracks(){
        Call<List<Track>> call =swaggerAPI.getTracks();
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if(!response.isSuccessful()){
                    Log.d("MYAPP", "Error"+response.code());
                    return;
                }

                trackList =response.body();
                for(Track track:trackList){
                    Log.d("MYAPP", track.getId());
                }
                inicializarRecyclerView(trackList);
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                //Quando algo va mal
                Log.d("MYAPP", "Error obtencion Tracks:"+t.getMessage());
            }
        });
    }
    private void addTrack(){
        Call<Track> call = swaggerAPI.addTrack(track);
        call.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if(!response.isSuccessful()){
                    Log.d("MYAPP", "Error"+response.code());
                    return;
                }
                Track track =response.body();
                Log.d("MYAPP", track.getId());
                getAllTracks();
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Log.d("MYAPP", "Error obtencion Tracks:"+t.getMessage());
            }
        });
        track=null;
    }
    private void editTrack(){
        Call<Void> call=swaggerAPI.editTrack(track);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Log.d("MYAPP", "Error"+response.code());
                    return;
                }
                getAllTracks();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("MYAPP", "Error obtencion Tracks:"+t.getMessage());
                }
        });
        track = null;
    }
    private void deleteTrack(String id){
        Call<Void> call=swaggerAPI.deleteTrack(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Log.d("MYAPP", "Error"+response.code());
                    return;
                }
                getAllTracks();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("MYAPP", "Error obtencion Tracks:"+t.getMessage());
            }
        });
    }

    /***************************************************
     * Otros metodos
     ***************************************************/
    public void inicializarRecyclerView(List<Track> tracks){//Inicializar RecyclerView
        MyAdapter myAdapter= new MyAdapter(this,tracks);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setHasFixedSize(true);

    }

    public void insertItem(View view){//Add new track-> abre plantilla vacia
        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtra("newTrack",true);
        startActivity(intent);
    }

    private Boolean newTrack(){//Para cuando se ha añadido un nuevo Track
        if(getIntent().hasExtra("data1") &&getIntent().hasExtra("data2")&&getIntent().hasExtra("id")) {
            //Add nuevo track
            track = new Track(getIntent().getStringExtra("id"),getIntent().getStringExtra("data1"),getIntent().getStringExtra("data2"));
            return false;
        }else if(getIntent().hasExtra("data1") &&getIntent().hasExtra("data2")){
            track = new Track(getIntent().getStringExtra("data1"),getIntent().getStringExtra("data2"));
            return true;
        } else{return null;}
    }
    private Boolean delete(){
        if(getIntent().hasExtra("delete")){
            //Add nuevo track
            deleteTrack(getIntent().getStringExtra("delete"));
            return true;
        }return false;
    }
}