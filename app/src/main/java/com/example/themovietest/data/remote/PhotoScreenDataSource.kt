package com.example.themovietest.data.remote

import com.example.themovietest.core.Resources
import com.example.themovietest.data.model.ImageList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PhotoScreenDataSource {

    suspend fun getLatestImages(): Resources<List<ImageList>> {
        val imageList = mutableListOf<ImageList>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("Imagenes").get().await()
        for(images in querySnapshot.documents){
            images.toObject(ImageList::class.java)?.let {
                imageList.add(it)
            }
        }
        return Resources.Success(imageList)
    }
}