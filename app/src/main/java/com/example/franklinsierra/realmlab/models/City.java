package com.example.franklinsierra.realmlab.models;

import com.example.franklinsierra.realmlab.app.MyAplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class City extends RealmObject {

    //propiedades
    @PrimaryKey
    private int idCity;
    @Required
    private String nameCity;
    @Required
    private String descriptionCity;
    @Required
    //asi por que la imagen es un enlace
    private String imageCity;
    //float porque pueden haber fracciones de estrellas
    private float stars;


    public City(){}


    public City(String nameCity,String descriptionCity, String imageCity, float stars) {
        this.idCity = MyAplication.cityId.incrementAndGet();
        this.nameCity=nameCity;
        this.descriptionCity = descriptionCity;
        this.imageCity = imageCity;
        this.stars = stars;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getnameCity() {
        return nameCity;
    }

    public void setnameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getDescriptionCity() {
        return descriptionCity;
    }

    public void setDescriptionCity(String descriptionCity) {
        this.descriptionCity = descriptionCity;
    }

    public String getImageCity() {
        return imageCity;
    }

    public void setImageCity(String imageCity) {
        this.imageCity = imageCity;
    }


    public float getStarts() {
        return stars;
    }

    public void setStart(float stars) {
        this.stars = stars;
    }
}
