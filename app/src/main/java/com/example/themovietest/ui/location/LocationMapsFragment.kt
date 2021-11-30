package com.example.themovietest.ui.location

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.themovietest.R
import com.example.themovietest.databinding.FragmentLocationMapsBinding
import com.example.themovietest.databinding.FragmentPhotoUserBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.properties.Delegates

class LocationMapsFragment : Fragment(R.layout.fragment_location_maps) {

    private lateinit var binding: FragmentLocationMapsBinding
    private val db = FirebaseFirestore.getInstance()

    private val callback = OnMapReadyCallback { googleMap ->

        db.collection("Localizaciones").document("new_gps").get().addOnSuccessListener { document ->
            document?.let {
                Log.d("Firebase", "DocumentSnapshot data: ${document.data}")
                var lat = document.getString("latitude")!!.toDouble()
                var lng = document.getString("longitude")!!.toDouble()
                val location = LatLng(lat, lng)
                googleMap.addMarker(MarkerOptions().position(location).title("Your Marker"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
            }
        }.addOnFailureListener { error ->
            Log.e("Firebase", error.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLocationMapsBinding.bind(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}