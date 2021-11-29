package com.example.themovietest.ui.movie

import com.example.themovietest.core.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.themovietest.R
import com.example.themovietest.data.model.Movie
import com.example.themovietest.data.remote.MovieDataSource
import com.example.themovietest.databinding.FragmentMovieBinding
import com.example.themovietest.presentation.MovieViewModel
import com.example.themovietest.presentation.MovieViewModelFactory
import com.example.themovietest.repository.MovieRepositoryImpl
import com.example.themovietest.repository.RetrofitClient
import com.example.themovietest.ui.movie.adapters.MovieAdapter
import com.example.themovietest.ui.movie.adapters.concat.NowplayingConcatAdapter
import com.example.themovietest.ui.movie.adapters.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                MovieDataSource(RetrofitClient.webservice)
            )
        )
    }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resources.Loading -> {
                    //Log.d("LiveData", "Loading...")
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resources.Success -> {
                    //Log.d("LiveData", "Upcoming: ${result.data.first} \n\n Nowplaying: ${result.data.second}")
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                MovieAdapter(
                                    result.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            0,
                            NowplayingConcatAdapter(
                                MovieAdapter(
                                    result.data.second.results,
                                    this@MovieFragment
                                )
                            )
                        )
                    }
                    binding.rvMovie.adapter = concatAdapter
                }
                is Resources.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    //Log.d("Error", "${result.exception}")
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date,
            movie.vote_average.toFloat(),
            movie.vote_count
        )
        findNavController().navigate(action)
    }
}