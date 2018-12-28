package com.example.franklinsierra.realmlab.app;


//la hago extender de aplication para que se ejecute de primera y se config la data base

import android.app.Application;

import com.example.franklinsierra.realmlab.models.City;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyAplication extends Application {

    //declaracion de id's para autoincrement

    public static AtomicInteger cityId = new AtomicInteger();



    //  ++++   PROCESO PARA ESTABLECER LA CONFG DE LA BD Y OBTENCION DE LOS ID'S    ++++    //

    @Override
    public void onCreate() {
        super.onCreate();
        //inicio la operacion para conf la base de datos y los id's
        Realm.init(this);
        //accedo a la confg por defecto
        setUpRealmConfig();
        //hago un objeto realm con la conf por defecto
        Realm realm = Realm.getDefaultInstance();
        //busco los id's
        cityId = getIdByTable(realm, City.class);
        //cierro el proceso
        realm.close();
    }


    //  ++++    CONFIGURACION DE LA BASE DE DATOS   ++++    //

    private void setUpRealmConfig() {
        RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        //hago la conf por defecto
        Realm.setDefaultConfiguration(configuration);
    }


    //  ++++    METODO PARA CONOCER EL VALOR MAXIMO QUE LLEVA CADA ID DE CUALQUIER CLASE    ++++    //

    //La T significa cualquier clase
    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass) {

        //listo los resultados de lo que encuentren en cualquier tabla
        RealmResults<T> results = realm.where(anyClass).findAll();

        if (results.size() > 0) {
            //quiere decir que hay al menos un registro
            return new AtomicInteger(results.max("idCity").intValue());
        } else {
            //no hay registros
            return new AtomicInteger();
        }

    }
}
