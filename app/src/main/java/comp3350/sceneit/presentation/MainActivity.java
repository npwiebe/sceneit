package comp3350.sceneit.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.sceneit.R;
import comp3350.sceneit.data.DatabaseAccessException;
import comp3350.sceneit.data.DatabaseManager;
import comp3350.sceneit.data.PostgresDatabaseManager;
import comp3350.sceneit.data.Movie;

public class MainActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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

    }

    private void getNowPlayingDetails(){
        Log.d(TAG, "getMovieDetails: preparing bitmaps.");

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
                //Log.d(TAG, "URL result: " + mv.getPoster_url());
            }

        } catch (DatabaseAccessException e)
        {
            Toast.makeText(this, "something went wrong.", Toast.LENGTH_LONG).show();
        }

        display(R.id.rvNowPlaying, 50);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mImageURL, mTitle, mRating, R.layout.now_playing_carousel_item);
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
        RecyclerViewAdapter adapter1 = new RecyclerViewAdapter(this, tImageURL, tTitle, tRating, R.layout.trending_carousel_item);
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
        TextView tvTheatreLocation = (TextView) findViewById(R.id.tvTheaterName);

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Theater");
        builder.setItems(theatre, new DialogInterface.OnClickListener() {
            //@Override
            public void onClick(DialogInterface dialog, int which) {
                tvTheatreLocation.setText(theatre[which]);
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //convert rating range from 0 - 100 to 0 - 5
    private String convertRating(int value)
    {
        double newRating = value * 0.05;
        return String.valueOf(newRating);
    }

}