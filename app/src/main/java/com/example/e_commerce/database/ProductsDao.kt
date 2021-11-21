package com.example.e_commerce.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_commerce.model.Product.Product

// DATA BASE CACHING

@Dao
interface ProductsDao {
  // to replace any data already in and add the new data only
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertProducts(products: List<Product>)

 @Query("SELECT * FROM product")
 suspend fun getProducts(): List<Product>
// where to select a specific data
    @Query("SELECT * FROM product WHERE isFavorite")
    suspend fun getFavoriteProducts(): List<Product>

}