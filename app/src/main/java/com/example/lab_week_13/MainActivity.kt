package com.example.lab_week_13

// Import
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.test_lab_week_12.R
import com.example.test_lab_week_12.databinding.ActivityMainBinding
import com.example.lab_week_13.model.Movie

class MainActivity : AppCompatActivity() {

    private val movieAdapter by lazy {
        MovieAdapter(object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                openMovieDetails(movie)
            }
        })
    }

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Initialize DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 2. Initialize ViewModel
        val movieRepository = (application as MovieApplication).movieRepository
        movieViewModel = MovieViewModel(movieRepository)

        // 3. Bind ViewModel to layout
        binding.viewModel = movieViewModel
        binding.lifecycleOwner = this

        // 4. Set RecyclerView adapter
        binding.movieList.adapter = movieAdapter

        // Note: lifecycleScope.launch block removed as per instruction
    }

    private fun openMovieDetails(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.EXTRA_TITLE, movie.title)
            putExtra(DetailsActivity.EXTRA_RELEASE, movie.releaseDate)
            putExtra(DetailsActivity.EXTRA_OVERVIEW, movie.overview)
            putExtra(DetailsActivity.EXTRA_POSTER, movie.posterPath)
        }
        startActivity(intent)
    }
}
