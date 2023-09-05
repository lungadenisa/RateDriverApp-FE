package com.example.ratedriverapp.holders;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratedriverapp.R;

public class ItemViewHolderByReceiver extends RecyclerView.ViewHolder {
    public TextView textViewReviewer;
    public TextView textViewReviewText;
    public RatingBar ratingBar;

    public ItemViewHolderByReceiver(@NonNull View itemView) {
        super(itemView);
        this.textViewReviewer = itemView.findViewById(R.id.textViewReviewer);
        this.textViewReviewText = itemView.findViewById(R.id.textViewReviewText);
        this.ratingBar = itemView.findViewById(R.id.ratingBar);
    }
}
