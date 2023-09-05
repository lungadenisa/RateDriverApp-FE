package com.example.ratedriverapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratedriverapp.R;
import com.example.ratedriverapp.dtos.ReviewDTO;
import com.example.ratedriverapp.holders.ItemViewHolderByReceiver;

import java.util.List;

public class ItemAdaptorByReceiver extends RecyclerView.Adapter<ItemViewHolderByReceiver> {
    private List<ReviewDTO> reviewsByReceiver;

    public ItemAdaptorByReceiver(List<ReviewDTO> reviews) {
        this.reviewsByReceiver = reviews;
    }

    @NonNull
    @Override
    public ItemViewHolderByReceiver onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(reviewsByReceiver == null || reviewsByReceiver.isEmpty()){
            View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_review, parent,false);
            return new ItemViewHolderByReceiver(emptyView);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent,false);
            return new ItemViewHolderByReceiver(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderByReceiver holder, int position) {
        if(reviewsByReceiver == null || reviewsByReceiver.isEmpty()){
            holder.textViewReviewer.setText("No reviews yet!");
        }else{
            ReviewDTO review = reviewsByReceiver.get(position);
            holder.textViewReviewer.setText(review.getGiver().username);
            holder.textViewReviewText.setText(review.getComment());
            holder.ratingBar.setRating(review.getStars());
        }
    }

    @Override
    public int getItemCount() {
        if(reviewsByReceiver==null || reviewsByReceiver.isEmpty()){
            return 1;
        }
        return reviewsByReceiver.size();
    }
}
