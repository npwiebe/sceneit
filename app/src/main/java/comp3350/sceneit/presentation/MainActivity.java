package comp3350.sceneit.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sceneit.R;
import comp3350.sceneit.data.exceptions.DatabaseAccessException;
import comp3350.sceneit.data.DatabaseManager;
import comp3350.sceneit.data.Movie;
import comp3350.sceneit.data.PostgresDatabaseManager;

public class MainActivity extends AppCompatActivity implements IMovieClickListener {

    private static final String TAG = "MainActivity";

    //Now playing
    private ArrayList<String> mImageURL = new ArrayList<>();
    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mRating = new ArrayList<>();

    //Trending
    private ArrayList<String> tImageURL = new ArrayList<>();
    private ArrayList<String> tTitle = new ArrayList<>();
    private ArrayList<String> tRating = new ArrayList<>();

    //Theatres
    private String[] theatre = {"SilverCity St. Vital Cinemas", "Landmark Cinemas Towne Cinema 8", "Scotiabank Theatre",
            "Cineplex Odeon McGillivray", "Famous Players Cinemas", "Cinema City Northgate",
            "Cinematheque", "Burton Cummings Theatre", "Landmark Cinemas Garry Theatre"};

    private RecyclerView recyclerView;
    private TextView tvTheatreLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTheatreLocation = (TextView) findViewById(R.id.tvTheaterName);
        tvTheatreLocation.setText(theatre[0]);

        getNowPlayingDetails();
        getTrending();

        //OnClickListener for ivPin
        ImageView imgPin = (ImageView) findViewById(R.id.ivPin);
        imgPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTheatre();
            }
        });

        //OnClickListener for tvTheaterName
        TextView txtTheaterName = (TextView) findViewById(R.id.tvTheaterName);
        txtTheaterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTheatre();
            }
        });

        //OnClickListener for search
        ImageView imgSearch = (ImageView) findViewById(R.id.ivSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);

                //Send data to SearchActivity
                i.putStringArrayListExtra("mTitle", mTitle);
                i.putStringArrayListExtra("mRating", mRating);
                i.putExtra("theater", tvTheatreLocation.getText().toString());

                startActivity(i);
            }
        });

    }

    private void getNowPlayingDetails(){
        mImageURL.add("https://static.wikia.nocookie.net/godzilla/images/4/43/Godzilla_vs_Kong_Poster.jpg/revision/latest?cb=20210121180804");
        mImageURL.add("https://i5.walmartimages.com/asr/7ea01e0a-22b0-4bf8-8698-5c2e9bde87cb_1.5cfd5de0b85f9ab768f2a367474d4f8b.jpeg");
        mImageURL.add("https://www.joblo.com/assets/images/oldsite/posters/images/full/02_AVG_Online1Sht_UK2_rgb_thumb.jpg");
        mImageURL.add("https://m.media-amazon.com/images/I/91F6aF4UJ0L._AC_SL1500_.jpg");
        mImageURL.add("https://images-na.ssl-images-amazon.com/images/I/41QsBqbdIqL._AC_.jpg");
        mImageURL.add("https://images-na.ssl-images-amazon.com/images/I/91Tr%2BbhnMUL._AC_SL1500_.jpg");
        mImageURL.add("https://images-na.ssl-images-amazon.com/images/I/714hR8KCqaL._AC_SL1308_.jpg");

        DatabaseManager dbm = new PostgresDatabaseManager();
        ArrayList<Movie> movies;

        try
        {
            movies = dbm.getMovies();
            for(Movie mv : movies)
            {
                mTitle.add(mv.getTitle());
                mRating.add(convertRating(mv.getRating()));
            }
        } catch (DatabaseAccessException e)
        {
            e.printStackTrace();
        }

        display(R.id.rvNowPlaying, 50);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mImageURL, mTitle, mRating, R.layout.now_playing_carousel_item, this);
        recyclerView.setAdapter(adapter);
    }

    private void getTrending() {

        tImageURL.add("https://www.joblo.com/assets/images/oldsite/posters/images/full/02_AVG_Online1Sht_UK2_rgb_thumb.jpg");
        tTitle.add("The Avengers");
        tRating.add("4");

        tImageURL.add("https://images-na.ssl-images-amazon.com/images/I/41QsBqbdIqL._AC_.jpg");
        tTitle.add("Don't Breathe");
        tRating.add("3.5");

        tImageURL.add("https://static.wikia.nocookie.net/godzilla/images/4/43/Godzilla_vs_Kong_Poster.jpg/revision/latest?cb=20210121180804");
        tTitle.add("Godzilla vs. Kong");
        tRating.add("0");

        tImageURL.add("https://m.media-amazon.com/images/I/91F6aF4UJ0L._AC_SL1500_.jpg");
        tTitle.add("Joker");
        tRating.add("4.2");

        tImageURL.add("https://i5.walmartimages.com/asr/7ea01e0a-22b0-4bf8-8698-5c2e9bde87cb_1.5cfd5de0b85f9ab768f2a367474d4f8b.jpeg");
        tTitle.add("The Purge: Election Year");
        tRating.add("3.2");

        tImageURL.add("https://images-na.ssl-images-amazon.com/images/I/91Tr%2BbhnMUL._AC_SL1500_.jpg");
        tTitle.add("Spider-Man");
        tRating.add("3.8");

        tImageURL.add("https://images-na.ssl-images-amazon.com/images/I/714hR8KCqaL._AC_SL1308_.jpg");
        tTitle.add("Toy Story");
        tRating.add("4");

        display(R.id.rvTrending, 50);
        RecyclerViewAdapter adapter1 = new RecyclerViewAdapter(this, tImageURL, tTitle, tRating, R.layout.trending_carousel_item, this);
        recyclerView.setAdapter(adapter1);
    }

    private void display(int carouselDisplayType, int spacing) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(carouselDisplayType);
        recyclerView.setLayoutManager(layoutManager);
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(spacing); //spacing between movies
        recyclerView.addItemDecoration(itemDecorator);
    }

    private void chooseTheatre() {
        tvTheatreLocation = (TextView) findViewById(R.id.tvTheaterName);

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Theater");
        builder.setItems(theatre, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvTheatreLocation.setText(theatre[which]);
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onBackPressed(){
        moveTaskToBack(true);
    }

    //convert rating range from 0 - 100 to 0 - 5
    private String convertRating(int value)
    {
        double newRating = value * 0.05;
        return String.valueOf(newRating);
    }

    @Override
    public void movieClick(int position, int carouselViewType) {
        Bundle movieDetails = new Bundle();

        //check which recyclerView is being clicked before passing data
        if(carouselViewType == R.layout.now_playing_carousel_item) {
            movieDetails.putString("movieTitle", mTitle.get(position));
            movieDetails.putString("moviePoster", mImageURL.get(position));
            movieDetails.putString("movieRating", mRating.get(position));

            Log.d(TAG, "Data passed: " + mTitle.get(position) + " " + mImageURL.get(position) +
                    " " + mRating.get(position) + " " + tvTheatreLocation.getText().toString());
        }
        else {
            movieDetails.putString("movieTitle", tTitle.get(position));
            movieDetails.putString("moviePoster", tImageURL.get(position));
            movieDetails.putString("movieRating", tRating.get(position));

            Log.d(TAG, "Trending data passed: " + tTitle.get(position) + " " + tImageURL.get(position) +
                    " " + tRating.get(position) + " " + tvTheatreLocation.getText().toString());
        }

        movieDetails.putString("theater", tvTheatreLocation.getText().toString());

        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtras(movieDetails);
        this.startActivity(intent);
    }

}