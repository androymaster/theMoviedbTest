package com.example.themovietest.ui.location

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import com.example.themovietest.R
import com.example.themovietest.databinding.FragmentLocationMapsBinding
import com.example.themovietest.databinding.FragmentLocationUserBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.firestore.FirebaseFirestore

class LocationUserFragment : Fragment(R.layout.fragment_location_user) {

    private lateinit var binding: FragmentLocationUserBinding
    private val db = FirebaseFirestore.getInstance()
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

        binding.btnViewLocation.setOnClickListener { v ->
           Navigation.findNavController(v).navigate(R.id.action_locationUserFragment_to_locationMapsFragment)
        }
    }
    private fun getLocation() {
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

                //Ingresar información
                db.collection("Localizaciones").document("new_gps")
                    .set(GPS(it.latitude.toString(), it.longitude.toString()))
                    .addOnSuccessListener { document ->
                        Log.d(
                            "Firebase",
                            "Data Successfully Written!"
                        )
                        Toast.makeText(
                            requireContext(),
                            "Se a guardado tus datos en firebase storage",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w(
                            "Firebase",
                            "Error writing document",
                            e
                        )
                    }
            }
        }
    }

    data class GPS(val latitude: String = "", val longitude: String = "")
}