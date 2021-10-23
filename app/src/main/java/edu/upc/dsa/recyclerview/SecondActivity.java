package edu.upc.dsa.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class SecondActivity extends AppCompatActivity {

    ImageView mainImage;
    TextView mainTitle,mainDescription,idText;
    String idTrack;
    ViewSwitcher switcher1;
    ViewSwitcher switcher2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        mainImage=findViewById(R.id.mainImageView);
        mainTitle=findViewById(R.id.mainTitle);
        mainDescription=findViewById(R.id.mainDescription);
        idText=findViewById(R.id.textViewID);
        switcher1=(ViewSwitcher) findViewById(R.id.my_switcher_line1);
        switcher2=(ViewSwitcher) findViewById(R.id.my_switcher_line2);

        getData();
    }
    private void getData(){
        if(getIntent().hasExtra("newTrack")) {
            //Add nuevo track
            mainImage.setImageResource(R.drawable.ic_music);
            addNewTrack();

        }else if(getIntent().hasExtra("myImageView")&&getIntent().hasExtra("data1")
                &&getIntent().hasExtra("data2")&&getIntent().hasExtra("id")) {
            //Mostrar datos track
            mainTitle.setText(getIntent().getStringExtra("data1"));
            mainDescription.setText(getIntent().getStringExtra("data2"));
            mainImage.setImageResource(getIntent().getIntExtra("myImageView",1));
            idText.setText("ID: "+getIntent().getStringExtra("id"));
            idTrack=getIntent().getStringExtra("id");
            showTrack();
        }else{
            Toast.makeText(this,"No data.",Toast.LENGTH_LONG).show();
        }
    }


    /**************************************************************
     * Metodos onClick
     **************************************************************/
    public void cancel(View v){
        showTrack();
        //Hacer textBox no editable
        switcher1.showPrevious();
        switcher2.showPrevious();
    }
    public void save(View v){
        Intent intent = new Intent(this,MainActivity.class);
        EditText text =findViewById(R.id.hidden_title);
        intent.putExtra("data1",text.getText().toString());
        text=findViewById(R.id.hidden_description);
        intent.putExtra("data2",text.getText().toString());
        this.startActivity(intent);

    }
    public void delete(View v){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("delete",idTrack);
        this.startActivity(intent);
    }
    public void edit(View v){
        //Hacer textBox editables
        final ViewSwitcher switcher1 = (ViewSwitcher) findViewById(R.id.my_switcher_line1);
        switcher1.showNext(); //or switcher.showPrevious();
        final ViewSwitcher switcher2 = (ViewSwitcher) findViewById(R.id.my_switcher_line2);
        switcher2.showNext(); //or switcher.showPrevious();

        //Introducir los textos
        EditText edit =findViewById(R.id.hidden_title);
        TextView text =findViewById(R.id.mainTitle);
        edit.setText(text.getText().toString());
        edit =findViewById(R.id.hidden_description);
        text =findViewById(R.id.mainDescription);
        edit.setText(text.getText().toString());

        //Cambiar botones
        findViewById(R.id.editButton).setVisibility(View.GONE);
        findViewById(R.id.deleteButton).setVisibility(View.GONE);
        findViewById(R.id.saveButton).setVisibility(View.VISIBLE);
        findViewById(R.id.cancelButton).setVisibility(View.VISIBLE);

        //onClick save
        findViewById(R.id.saveButton).setOnClickListener(v1 -> {
            //enviar datos nuevos
            Intent intent = new Intent(this,MainActivity.class);
            EditText edit2 =findViewById(R.id.hidden_title);
            intent.putExtra("data1",edit2.getText().toString());
            edit2 =findViewById(R.id.hidden_description);
            intent.putExtra("data2",edit2.getText().toString());
            intent.putExtra("id",idTrack);
            this.startActivity(intent);
        });

    }

    /********************************************************************
     * Metodos para configurar el entorno de la actividad secundaria
     ********************************************************************/
    private void addNewTrack() {
        switcher1.showNext(); //or switcher.showPrevious();
        switcher2.showNext(); //or switcher.showPrevious();
        findViewById(R.id.editButton).setVisibility(View.GONE);
        findViewById(R.id.textViewID).setVisibility(View.GONE);
        findViewById(R.id.cancelButton).setVisibility(View.GONE);
        findViewById(R.id.deleteButton).setVisibility(View.GONE);
    }
    private void showTrack(){
        findViewById(R.id.saveButton).setVisibility(View.GONE);
        findViewById(R.id.cancelButton).setVisibility(View.GONE);
        findViewById(R.id.editButton).setVisibility(View.VISIBLE);
        findViewById(R.id.deleteButton).setVisibility(View.VISIBLE);
    }
}