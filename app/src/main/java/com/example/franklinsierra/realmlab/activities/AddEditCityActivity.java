package com.example.franklinsierra.realmlab.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.franklinsierra.realmlab.R;
import com.example.franklinsierra.realmlab.models.City;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class AddEditCityActivity extends AppCompatActivity {

    private int cityId;
    //para distinguir entre la creacion o la edicion
    private boolean creation;
    private City city;
    private Realm realm;

    //componentes de la GUI
    private EditText nameCity;
    private EditText link;
    private EditText description;
    private ImageView imageCity;
    private RatingBar ratingBarCity;
    private FloatingActionButton fab;
    private Button btnPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_city);

        realm=Realm.getDefaultInstance();

        //busco las referencias de los elementos GUI
        bindUIReferences();

        //Se comprueba si es edicion o creacion
        if(getIntent().getExtras()!=null){
            cityId=getIntent().getExtras().getInt("id");
            creation=false;
        }else{
            creation=true;
        }

        //metodo para cambiar el titulo del activity
        setActivityTitle();

        if(!creation){
            city=getCityById(cityId);
            bindDataToFields();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditNewCity();
            }
        });
    }

    private boolean isDataValidForNewCity(){
        if(nameCity.getText().length()>0 && description.getText().length()>0 && link.getText().length()>0){
            return true;
        }else {
            return false;
        }
    }

    private void addEditNewCity(){
        if(isDataValidForNewCity()){
            String newName=nameCity.getText().toString();
            String newDescrip=description.getText().toString();
            String newLink=link.getText().toString();
            float stars=ratingBarCity.getRating();

            City newCity=new City(newName,newDescrip,newLink,stars);

            //en caso de no ser una creacion
            if(!creation){
                newCity.setIdCity(cityId);
            }

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(newCity);
            realm.commitTransaction();
            goToMain();
        }else{
            Toast.makeText(this,"the data is not valid for this action, please check the fields agin"
                    ,Toast.LENGTH_LONG).show();
        }


    }

    private void goToMain() {
        Intent intent=new Intent(AddEditCityActivity.this,CityActivity.class);
        startActivity(intent);
    }

    private void bindDataToFields() {
        nameCity.setText(city.getnameCity());
        description.setText(city.getDescriptionCity());
        link.setText(city.getImageCity());
        ratingBarCity.setRating(city.getStarts());
        loadImagePreview(city.getImageCity());
    }

    private void loadImagePreview(String editText_CityImage) {
        Picasso.get().load(editText_CityImage).fit().into(imageCity);
    }

    private City getCityById(int cityId) {
        return realm.where(City.class).equalTo("idCity",cityId).findFirst();
    }

    //  ++++    METODO PARA CAMBIAR TITULO DEL ACTIVITY DEPENDIENDO SI ES EDIT O CREATE CITY    ++++///
    private void setActivityTitle() {
        String title="Edit City";
        if (creation==true){
            title="Create New City";
        }else{
            setTitle(title);
        }
    }

    private void bindUIReferences() {
        nameCity=(EditText) findViewById(R.id.editText_CityName);
        link=(EditText) findViewById(R.id.editText_CityImage);
        description=(EditText) findViewById(R.id.editText_CityDescription);
        imageCity=(ImageView) findViewById(R.id.imageViewPreview);
        ratingBarCity=(RatingBar) findViewById(R.id.ratingBarCity);
        fab=(FloatingActionButton) findViewById(R.id.FabEditCity);
        btnPreview=(Button) findViewById(R.id.buttomPreview);
    }
}
