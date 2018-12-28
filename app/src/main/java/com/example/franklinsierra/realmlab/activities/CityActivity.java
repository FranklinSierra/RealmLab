package com.example.franklinsierra.realmlab.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.franklinsierra.realmlab.R;
import com.example.franklinsierra.realmlab.adapters.CityAdapter;
import com.example.franklinsierra.realmlab.models.City;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class CityActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<City>> {

    //propiedades

    private FloatingActionButton fab;
    private CityAdapter adapter;

    //RealmResult es cuando se obtienen query
    //RealmList es cuando se modela relaciones 1:m
    private RealmResults<City> cities;
    private Realm realm;

    private RecyclerView recycler;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        //establezco la base de datos
        realm=Realm.getDefaultInstance();
        //busco todas las ciudades
        cities=realm.where(City.class).findAll();
        //notifico al adaptador
        cities.addChangeListener(this);

        //ubico los elementos

        recycler=(RecyclerView)findViewById(R.id.reciclerViewCity);
        //previene el colapso cuando el recycler no depende del adapter
        recycler.setHasFixedSize(true);
        //meneja los cambios visibles en los elementos
        recycler.setItemAnimator(new DefaultItemAnimator());

        layoutManager=new LinearLayoutManager(this);
        //le paso el layout al recycler
        recycler.setLayoutManager(layoutManager);

        fab=(FloatingActionButton)findViewById(R.id.FabAddCity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //paso al otro activity
                Intent intent=new Intent(CityActivity.this,AddEditCityActivity.class);
                startActivity(intent);
            }
        });

        adapter=new CityAdapter(cities, R.layout.recycler_view_item, new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City city, int position) {
                Intent intent = new Intent(CityActivity.this, AddEditCityActivity.class);
                intent.putExtra("id", cities.get(position).getIdCity());
                startActivity(intent);
            }
        }, new CityAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(City city, int position) {
                //cuando desean borrar la ciudad
                showAlertForRemovingCity("Delete city","Are you sure you want delete"+city.getnameCity()+"?",position);
            }
        });

        recycler.setAdapter(adapter);
        cities.addChangeListener(this);
    }

    private void showAlertForRemovingCity(String title, String message, final int position) {
        AlertDialog dialog=new AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               deleteCity(position);
                Toast.makeText(CityActivity.this,"has been delete succesfully",Toast.LENGTH_LONG).show();
            }
        })
                .setNegativeButton("Cancel",null).show();
    }

    private void deleteCity(int position) {
        realm.beginTransaction();
        cities.get(position).deleteFromRealm();
        realm.commitTransaction();
    }

    //  +++ ACTUALIZAR EL ADAPTADOR POR CANTIDAD DE ELEMENTOS   +++ //
    @Override
    public void onChange(RealmResults<City> cities) {
        adapter.notifyDataSetChanged();
    }
}