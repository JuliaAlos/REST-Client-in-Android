package edu.upc.dsa.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Track> tracks;
    Context context;

    public MyAdapter(Context context,List<Track> trackList){
        tracks=trackList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Track track=tracks.get(position);
        holder.titol.setText(track.getTitle());
        holder.descripcio.setText(track.getSinger());
        holder.images.setImageResource(R.drawable.ic_music);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SecondActivity.class);
                intent.putExtra("id",track.getId());
                intent.putExtra("data1",track.getTitle());
                intent.putExtra("data2",track.getSinger());
                intent.putExtra("myImageView",R.drawable.ic_music);
                context.startActivity(intent);
            }
        });

    }

    @Override//Numero de items que tenim
    public int getItemCount() {
        return tracks.size();
    }

    /*****************************************************************
            Representa un sola fila del recycler view
     *****************************************************************/
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titol,descripcio;
        ImageView images;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            titol = itemView.findViewById(R.id.firstLine);
            descripcio=itemView.findViewById(R.id.secondLine);
            images=itemView.findViewById(R.id.imageView);
            mainLayout=itemView.findViewById(R.id.myLayout);
        }
    }
}
