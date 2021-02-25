package comp3350.sceneit.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;

import java.util.ArrayList;

import comp3350.sceneit.R;
import comp3350.sceneit.data.DatabaseAccessException;
import comp3350.sceneit.data.DatabaseManager;
import comp3350.sceneit.data.PostgresDatabaseManager;
import comp3350.sceneit.data.Movie;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // HACK!!
        // So sorry this is necessary. due to the current way the DatabaseManager is implemented,
        // this is necessary for this to work in iteration 1. This will allow network requests on
        // the main thread.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // HACK!!
    }
}