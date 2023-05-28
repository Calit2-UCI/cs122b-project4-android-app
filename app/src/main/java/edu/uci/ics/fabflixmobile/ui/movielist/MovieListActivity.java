package edu.uci.ics.fabflixmobile.ui.movielist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.data.NetworkManager;
import edu.uci.ics.fabflixmobile.data.model.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.widget.SearchView;
import edu.uci.ics.fabflixmobile.ui.login.LoginActivity;
import edu.uci.ics.fabflixmobile.ui.search.SearchActivity;
import edu.uci.ics.fabflixmobile.ui.singlemovie.SingleMovieActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieListActivity extends AppCompatActivity {

    private static final int PAGE_SIZE = 10; // Number of movies per page
    private int currentPage = 1; // Current page number

    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "s23_122b_kickin_war";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);

        Button searchButton = findViewById(R.id.search_page);
        Button prevButton = findViewById(R.id.prev);
        Button nextButton = findViewById(R.id.next);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SearchActivityPage = new Intent(MovieListActivity.this, SearchActivity.class);
                startActivity(SearchActivityPage);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchMovies(currentPage - 1);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchMovies(currentPage + 1);
            }
        });

        fetchMovies(currentPage);
    }

    private void fetchMovies(int page) {
        Log.d("fetchMovies.info--->", "current page: " + currentPage);
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final String url = baseURL + "/api/movielist?page=" + page + "&moviePerPage=" + PAGE_SIZE;

        // Request movies using GET method
        final StringRequest movieListRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        // Parse the JSON response and retrieve the movie data

                        JSONArray movieArray = new JSONArray(response);

                        JSONObject lastObject = movieArray.getJSONObject(movieArray.length() - 1);

                        // Extract the values of total_count and current_page
                        int totalCount = lastObject.getInt("total_count");
                        int currentPage = lastObject.getInt("current_page");

                        ArrayList<Movie> movies = new ArrayList<>();

                        // Extract movie information from the JSON array
                        for (int i = 0; i < movieArray.length() - 1; i++) {
                            JSONObject movieObject = movieArray.getJSONObject(i);
                            String title = movieObject.getString("movie_title");
                            int year = movieObject.getInt("movie_year");
                            movies.add(new Movie(title, (short) year));
                        }

                        // Update the current page number
                        this.currentPage = currentPage;

                        // Update the ListView with the fetched movies
                        updateMovieList(movies);

                        // Enable/disable pagination buttons based on current page
                        updatePaginationButtons(currentPage, totalCount);
                    } catch (JSONException e) {
                        Log.d("fetchMovies.error", "Error parsing JSON response: " + e.getMessage());
                    }
                },
                error -> {
                    Log.d("fetchMovies.error", error.toString());
                }
        );

        // Set a custom timeout duration for the request
        int timeoutMilliseconds = 10000; // Example: Set timeout to 10 seconds
        movieListRequest.setRetryPolicy(new DefaultRetryPolicy(timeoutMilliseconds,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the queue
        queue.add(movieListRequest);
    }
    ;
        // important: queue.add is where the login request is actually sent

        // Mock implementation to populate dummy movies for testing
        // ArrayList<Movie> movies = createDummyMovies(page);

        // Update the ListView with the fetched movies
        // updateMovieList(movies);
    private void updatePaginationButtons(int currentPage, int totalPages) {
        Button prevButton = findViewById(R.id.prev);
        Button nextButton = findViewById(R.id.next);

        prevButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < totalPages);
    }


    private ArrayList<Movie> createDummyMovies(int page) {
        ArrayList<Movie> movies = new ArrayList<>();
        int startIndex = (page - 1) * PAGE_SIZE + 1;
        for (int i = startIndex; i <= startIndex + PAGE_SIZE - 1; i++) {
            movies.add(new Movie("Movie " + i, (short) (2000 + i)));
        }
        return movies;
    }

    private void updateMovieList(ArrayList<Movie> movies) {
        MovieListViewAdapter adapter = new MovieListViewAdapter(this, movies);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Movie movie = movies.get(position);
            String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            Intent SingleMoviePage = new Intent(MovieListActivity.this, SingleMovieActivity.class);
            SingleMoviePage.putExtra("title", movie.getName());
            SingleMoviePage.putExtra("year", movie.getYear());
            startActivity(SingleMoviePage);
        });
    }
}