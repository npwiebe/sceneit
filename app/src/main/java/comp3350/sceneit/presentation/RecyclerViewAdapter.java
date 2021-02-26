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
    private ArrayList<String> mImageURL = new ArrayList<>();
    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mRating = new ArrayList<>();
    private int carouselView = 0;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mImageURL, ArrayList<String> mTitle, ArrayList<String> mRating, int carouselView) {
        this.mContext = mContext;
        this.mImageURL = mImageURL;
        this.mTitle = mTitle;
        this.mRating = mRating;
        this.carouselView = carouselView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(carouselView, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).asDrawable().load(mImageURL.get(position)).into(holder.moviePoster);

        holder.movieTitle.setText(mTitle.get(position));

        holder.movieRating.setRating(Float.parseFloat(mRating.get(position)));

        holder.moviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on an image: " + mTitle.get(position));
                Toast.makeText(mContext, mTitle.get(position), Toast.LENGTH_LONG).show();

                Bundle movieDetails = new Bundle();

                movieDetails.putString("movieTitle", mTitle.get(position));
                movieDetails.putString("moviePoster", mImageURL.get(position));
                movieDetails.putString("movieRating", mRating.get(position));
                //movieDetails.putString("theatreLocation", theatreLocation);

                Log.d(TAG, "movie: " + mTitle.get(position) + " " + mImageURL.get(position) + " " + mRating.get(position));

                Intent intent = new Intent(mContext, OrderActivity.class);
                intent.putExtras(movieDetails);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle;
        RatingBar movieRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.ivMoviePoster);
            movieTitle = itemView.findViewById(R.id.tvMovieTitle);
            movieRating = itemView.findViewById(R.id.rbMovieRating);
        }
    }
}
