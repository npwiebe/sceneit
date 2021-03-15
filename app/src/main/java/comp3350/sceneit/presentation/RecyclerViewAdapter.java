package comp3350.sceneit.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import comp3350.sceneit.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private ArrayList<String> mImageURL;
    private ArrayList<String> mTitle;
    private ArrayList<String> mRating;
    private int carouselView = 0;
    private IMovieClickListener mMovieClickListener;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mImageURL, ArrayList<String> mTitle, ArrayList<String> mRating, int carouselView, IMovieClickListener movieClickListener) {
        this.mContext = mContext;
        this.mImageURL = mImageURL;
        this.mTitle = mTitle;
        this.mRating = mRating;
        this.carouselView = carouselView;
        this.mMovieClickListener = movieClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView moviePoster;
        TextView movieTitle;
        RatingBar movieRating;
        IMovieClickListener movieClickListener;

        public ViewHolder(@NonNull View itemView, IMovieClickListener movieClickListener) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.ivMoviePoster);
            movieTitle = itemView.findViewById(R.id.tvMovieTitle);
            movieRating = itemView.findViewById(R.id.rbMovieRating);
            this.movieClickListener = movieClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            movieClickListener.movieClick(getAdapterPosition(), carouselView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(carouselView, parent, false);
        return new ViewHolder(view, mMovieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).asDrawable().load(mImageURL.get(position)).into(holder.moviePoster);

        holder.movieTitle.setText(mTitle.get(position));

        holder.movieRating.setRating(Float.parseFloat(mRating.get(position)));
    }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }

}
