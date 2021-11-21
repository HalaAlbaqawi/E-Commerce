package com.example.e_commerce.model.Product


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Product(
    @SerializedName("description")
    val description: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("isFavorite")
    val isFavorite: Boolean,
    @SerializedName("price")
    val price: Double,
    @SerializedName("title")
    val title: String
)