package com.example.franklinsierra.realmlab.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franklinsierra.realmlab.R;
import com.example.franklinsierra.realmlab.models.City;
import com.squareup.picasso.Picasso;

import java.util.List;

//COMO TRABAJO CON RECYCLER VIEW EL ADAPTADOR ES OTRO
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    //propiedades

    private Context context;
    private List<City> cities;
    private int layout;

    //interfaces
    private OnItemClickListener itemClickListener;
    private OnButtonClickListener btnClickListener;

    public CityAdapter(List<City> cities, int layout, OnItemClickListener itemListener
            ,OnButtonClickListener btnClickListener) {
        this.cities = cities;
        this.layout = layout;
        this.itemClickListener=itemListener;
        this.btnClickListener = btnClickListener;
    }

    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creo la vista
        View vista = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(vista);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.ViewHolder holder, int position) {
        holder.bind(cities.get(position),itemClickListener,btnClickListener);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }


    //hago extender a ViewHolder

    public class ViewHolder extends RecyclerView.ViewHolder {

        //elementos del UI pantalla principal
        public TextView name;
        public TextView description;
        public TextView stars;
        public ImageView image;
        public Button btnDelete;

        //los ubico por id
        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.textViewCityName);
            description = (TextView) itemView.findViewById(R.id.textViewCityDescription);
            stars = (TextView) itemView.findViewById(R.id.textViewStars);
            image = (ImageView) itemView.findViewById(R.id.imageViewCity);
            btnDelete = (Button) itemView.findViewById(R.id.buttonDelete);

        }

        public void bind(final City city, final OnItemClickListener itemListener,
                         final OnButtonClickListener buttonClickListener) {

            name.setText(city.getnameCity());
            description.setText(city.getDescriptionCity());
            stars.setText(city.getStarts() + "");
            Picasso.get().load(city.getImageCity()).fit().into(image);

            //cuando clickean el boton Delete
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickListener.onButtonClick(city, getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListener.onItemClick(city, getAdapterPosition());
                }
            });
        }


    }
    //  ++++    INTERFACES  ++++    //

    public interface OnButtonClickListener {
        void onButtonClick(City city, int position);
    }

    public interface OnItemClickListener{
        void onItemClick(City city,int position);
    }

}
