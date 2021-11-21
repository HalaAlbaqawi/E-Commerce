package com.example.e_commerce.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_commerce.model.Product.Product


@Database (entities = [Product::class], version = 1)
abstract class ProductsDatabase : RoomDatabase (){
    abstract fun productsDao(): ProductsDao



}