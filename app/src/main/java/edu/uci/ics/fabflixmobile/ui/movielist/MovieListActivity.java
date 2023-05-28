package edu.uci.ics.fabflixmobile.ui.movielist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.data.model.Movie;

import java.util.ArrayList;
import android.widget.SearchView;
import edu.uci.ics.fabflixmobile.ui.search.SearchActivity;
import edu.uci.ics.fabflixmobile.ui.singlemovie.SingleMovieActivity;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);
        // TODO: this should be retrieved from the backend server
        final ArrayList<Movie> movies = new ArrayList<>();

        Button searchButton = (Button)findViewById(R.id.search_page);
        Button prevButton = (Button)findViewById(R.id.prev);
        Button nextButton = (Button)findViewById(R.id.next);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event
                // Add your logic here
                Intent SearchActivityPage = new Intent(MovieListActivity.this, SearchActivity.class);
                startActivity(SearchActivityPage);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event
                // Add your logic here
//                Intent SearchActivityPage = new Intent(MovieListActivity.this, SearchActivity.class);
//                startActivity(SearchActivityPage);

                @SuppressLint("DefaultLocale") String message = String.format("TODO: PREVIOUS BUTTON");
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event
                // Add your logic here
//                Intent SearchActivityPage = new Intent(MovieListActivity.this, SearchActivity.class);
//                startActivity(SearchActivityPage);

                @SuppressLint("DefaultLocale") String message = String.format("TODO: NEXT BUTTON");
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });




        movies.add(new Movie("The Terminal", (short) 2004));
        movies.add(new Movie("The Final Season", (short) 2007));
        MovieListViewAdapter adapter = new MovieListViewAdapter(this, movies);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Movie movie = movies.get(position);
            @SuppressLint("DefaultLocale") String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            Intent SingleMoviePage = new Intent(MovieListActivity.this, SingleMovieActivity.class);
            SingleMoviePage.putExtra("title", movie.getName());
            SingleMoviePage.putExtra("year", movie.getYear());
            startActivity(SingleMoviePage);
        });
    }
}