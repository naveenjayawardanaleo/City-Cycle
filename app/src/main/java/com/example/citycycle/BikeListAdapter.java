package com.example.citycycle;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BikeListAdapter extends RecyclerView.Adapter<BikeListAdapter.ViewHolder> {
    private List<String> bikeList;
    private List<Integer> bikeImage;
    private List<String> bikeId;
    private List<Double> price;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bikeNameTextView;
        ImageView BikeImageSrc;
        TextView bikeIdTextView;
        TextView priceTextView;
        Button btnSubmit;

        public ViewHolder(View itemView) {
            super(itemView);
            bikeNameTextView = itemView.findViewById(R.id.bikeName);
            BikeImageSrc = itemView.findViewById(R.id.bikeImage);
            bikeIdTextView = itemView.findViewById(R.id.bikeId);
            priceTextView = itemView.findViewById(R.id.price);
            btnSubmit = itemView.findViewById(R.id.btnSubmit);
        }
    }

    public BikeListAdapter(List<String> bikeList, List<Integer> bikeImage, List<String> bikeId, List<Double> price) {
        this.bikeList = bikeList;
        this.bikeImage = bikeImage;
        this.bikeId = bikeId;
        this.price = price;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bikecard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bikeNameTextView.setText(bikeList.get(position));

        if (bikeImage != null && position < bikeImage.size()) {
            holder.BikeImageSrc.setImageResource(bikeImage.get(position));
        } else {
            holder.BikeImageSrc.setImageResource(R.drawable.baseline_add_a_photo_24);
        }

        holder.bikeIdTextView.setText(bikeId.get(position));
        holder.priceTextView.setText(String.format("LKR%.2f", price.get(position)));

        holder.btnSubmit.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RentingDetails.class);
            intent.putExtra("bikeName", bikeList.get(position));
            intent.putExtra("bikeImageResId", bikeImage.get(position));
            intent.putExtra("bikeId", bikeId.get(position));
            intent.putExtra("price", price.get(position));
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bikeList.size();
    }
}