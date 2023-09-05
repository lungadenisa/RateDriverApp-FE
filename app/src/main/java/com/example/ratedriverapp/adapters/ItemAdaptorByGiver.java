package com.example.ratedriverapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratedriverapp.R;
import com.example.ratedriverapp.dtos.ReviewDTO;
import com.example.ratedriverapp.holders.ItemViewHolderByGiver;

import java.util.List;

public class ItemAdaptorByGiver extends RecyclerView.Adapter<ItemViewHolderByGiver> {
    private List<ReviewDTO> reviewsByGiver;

    public ItemAdaptorByGiver(List<ReviewDTO> reviews) {
        this.reviewsByGiver = reviews;
    }

    @NonNull
    @Override
    public ItemViewHolderByGiver onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(reviewsByGiver == null || reviewsByGiver.isEmpty()){
            View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_review, parent,false);
            return new ItemViewHolderByGiver(emptyView);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent,false);
            return new ItemViewHolderByGiver(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderByGiver holder, int position) {
        if(reviewsByGiver == null || reviewsByGiver.isEmpty()){
            holder.textViewReviewer.setText("No given reviews yet!");
        }else{
            ReviewDTO review = reviewsByGiver.get(position);
            holder.textViewReviewer.setText(review.getReceiver().username);
            holder.textViewReviewText.setText(review.getComment());
            holder.ratingBar.setRating(review.getStars());
        }
    }

    @Override
    public int getItemCount() {
        if(reviewsByGiver==null || reviewsByGiver.isEmpty()){
            return 1;
        }
        return reviewsByGiver.size();
    }
}
