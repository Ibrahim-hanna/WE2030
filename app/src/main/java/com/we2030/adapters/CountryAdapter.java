package com.we2030.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we2030.R;
import com.we2030.models.Country;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    private final List<Country> countries;
    private final OnCountryClickListener listener;

    public interface OnCountryClickListener {
        void onCountryClick(Country country);
    }

    public CountryAdapter(List<Country> countries, OnCountryClickListener listener) {
        this.countries = countries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.bind(country, listener);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView flagImageView;
        private final TextView nameTextView;

        CountryViewHolder(View itemView) {
            super(itemView);
            flagImageView = itemView.findViewById(R.id.ivFlag);
            nameTextView = itemView.findViewById(R.id.tvName);
        }

        void bind(final Country country, final OnCountryClickListener listener) {
            // Définir l'image du drapeau
            flagImageView.setImageResource(country.getFlagResourceId());
            
            // Définir le nom du pays
            nameTextView.setText(country.getName());
            
            // Définir le clic
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCountryClick(country);
                }
            });
        }
    }
} 