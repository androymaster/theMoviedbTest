package com.example.themovietest.ui.photoUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.themovietest.R
import com.example.themovietest.core.Resources
import com.example.themovietest.data.remote.PhotoScreenDataSource
import com.example.themovietest.databinding.FragmentPhotoListBinding
import com.example.themovietest.domain.PhotoScreenRepoImpl
import com.example.themovietest.presentation.PhotoScreenViewModel
import com.example.themovietest.presentation.PhotoScreenViewModelFactory

class PhotoListFragment : Fragment(R.layout.fragment_photo_list) {

    private lateinit var binding: FragmentPhotoListBinding
    private val viewModel by viewModels<PhotoScreenViewModel>{ PhotoScreenViewModelFactory(PhotoScreenRepoImpl(
        PhotoScreenDataSource()
    ))}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoListBinding.bind(view)

        viewModel.fetchLatestImages().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvImages.adapter = PhotoListAdapter(result.data)
                }

                is Resources.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}