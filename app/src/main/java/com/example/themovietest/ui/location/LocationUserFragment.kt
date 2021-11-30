package com.example.themovietest.ui.location

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.themovietest.R
import com.example.themovietest.databinding.FragmentLocationUserBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap

class LocationUserFragment : Fragment(R.layout.fragment_location_user) {

    private lateinit var binding: FragmentLocationUserBinding
    private lateinit var map: GoogleMap
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLocationUserBinding.bind(view)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.btnGetLocation.setOnClickListener {
           getLocation()
        }

        binding.btnViewLocation.setOnClickListener {

        }
    }
    private fun getLocation(){
       val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
              binding.txtLat.text = it.latitude.toString()
              binding.txtLng.text = it.longitude.toString()
              Toast.makeText(requireContext(),"Se a guardado tus datos en firebase storage", Toast.LENGTH_SHORT).show()
            }
        }
    }
}