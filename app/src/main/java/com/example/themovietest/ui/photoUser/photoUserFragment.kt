package com.example.themovietest.ui.photoUser

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.themovietest.R
import com.example.themovietest.databinding.FragmentMovieBinding
import com.example.themovietest.databinding.FragmentPhotoUserBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

class photoUserFragment : Fragment(R.layout.fragment_photo_user) {

    private lateinit var binding: FragmentPhotoUserBinding
    private lateinit var imageView: ImageView
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoUserBinding.bind(view)

        binding.btnTakePicture.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent(){
       val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
       try {
           startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
       } catch(e: ActivityNotFoundException){
           Toast.makeText(requireContext().applicationContext, "No se encontro app para tomar la foto", Toast.LENGTH_SHORT).show()
       }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.ivPhotoUser.setImageBitmap(imageBitmap)
            uploadPicture(imageBitmap)
        }
    }

    private fun uploadPicture(bitmap: Bitmap) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef = storageRef.child("imagenes/${UUID.randomUUID()}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imagesRef.putBytes(data)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { exception ->
                    throw exception
                }
            }
            imagesRef.downloadUrl
        }.addOnCompleteListener { task ->
           if (task.isSuccessful){
               val downloadUrl = task.result.toString()
               Log.d("Storage", "uploadPictureURL: $downloadUrl")
           }
        }
    }
}