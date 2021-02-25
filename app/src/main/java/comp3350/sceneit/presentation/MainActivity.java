package comp3350.sceneit.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import comp3350.sceneit.R;
import comp3350.sceneit.data.DatabaseManager;
import comp3350.sceneit.data.PostgresDatabaseManager;
import comp3350.sceneit.data.Movie;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}