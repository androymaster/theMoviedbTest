package com.example.themovietest.ui.photoUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.themovietest.R
import com.example.themovietest.data.model.ImageList
import com.example.themovietest.databinding.FragmentLocationMapsBinding
import com.example.themovietest.databinding.FragmentLocationUserBinding
import com.example.themovietest.databinding.FragmentPhotoListBinding

class PhotoListFragment : Fragment(R.layout.fragment_photo_list) {

    private lateinit var binding: FragmentPhotoListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoListBinding.bind(view)

        val imageList = listOf(ImageList())
        binding.rvImages.adapter = PhotoListAdapter(imageList)

    }
}