package com.example.themovietest.ui.moviedetails

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.themovietest.R
import com.example.themovietest.databinding.FragmentMovieBinding
import com.example.themovietest.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.posterImageUrl}").centerCrop().into(binding.imageMovie)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.backgroundImageUrl}").centerCrop().into(binding.imgBackground)
        binding.description.text = args.overview
        binding.txtTitleMovie.text = args.title
        binding.txtRelease.text = "Released ${args.releaseDate}"
        binding.txtLanguage.text = "Language ${args.language}"
        binding.txtPopular.text = "${args.voteAverage} (${args.voteCount}Reviews)"
    }
}