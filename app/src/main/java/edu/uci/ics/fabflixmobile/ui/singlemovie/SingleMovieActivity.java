package edu.uci.ics.fabflixmobile.ui.singlemovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.data.model.Movie;
import edu.uci.ics.fabflixmobile.ui.movielist.MovieListActivity;
import edu.uci.ics.fabflixmobile.ui.movielist.MovieListViewAdapter;
import edu.uci.ics.fabflixmobile.ui.search.SearchActivity;

import java.util.ArrayList;

public class SingleMovieActivity extends AppCompatActivity {

    private EditText searchQuery;

    private TextView titleText;

    private TextView yearText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlemovie);

        String title = getIntent().getStringExtra("title");
        short year = getIntent().getShortExtra("year", (short) 0);

        titleText = findViewById(R.id.title);
        yearText = findViewById(R.id.year);

        titleText.setText(title);
        yearText.setText(String.valueOf(year));

        Button homeButton = findViewById(R.id.home);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event
                // Add your logic here
                Intent movieListIntent = new Intent(SingleMovieActivity.this, MovieListActivity.class);
                startActivity(movieListIntent);
            }
        });
    }
}
