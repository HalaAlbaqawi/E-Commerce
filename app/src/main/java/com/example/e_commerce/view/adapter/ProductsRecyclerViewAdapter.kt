package com.example.e_commerce.view.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.e_commerce.R
import com.example.e_commerce.model.Product.Product
import com.squareup.picasso.Picasso

class ProductsRecyclerViewAdapter() :
    RecyclerView.Adapter<ProductsRecyclerViewAdapter.ProductsViewHolder>() {
    // diffutil to control the data also
   // to check if the data are the same or not
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
  // this two fun to make sure the data and content are the same or not, we do the same thing on
  // every thing only we change inside the <>
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }
    // to take the size of the list and
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsRecyclerViewAdapter.ProductsViewHolder {
        return ProductsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.products_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = differ.currentList [position]
        holder.titleTextView.text = item.title
        holder.priceTextView.text = "${item.price} SAR"
        holder.favoriteToggleButton.isChecked = item.isFavorite

        Picasso.get().load(item.imagePath).into(holder.productImageView)

        holder.favoriteToggleButton.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
// to add the data inside the recycler view
    fun submitList(List: List<Product>){
        differ.submitList(List)
    }
    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleTextView: TextView = itemView.findViewById(R.id.product_name_textView)
    val priceTextView: TextView = itemView.findViewById(R.id.product_price_textView)
    val productImageView: ImageView = itemView.findViewById(R.id.product_imageView)
    val favoriteToggleButton: ToggleButton = itemView.findViewById(R.id.favorite_toggle_Button)



    }
}