package com.elapp.booque.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elapp.booque.data.entity.category.Category
import com.elapp.booque.databinding.CategoryListBinding

class CategoryAdapter(private var categoryList: List<Category>): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        val view = CategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    inner class CategoryViewHolder(val binding: CategoryListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.txCategoryTitle.text = category.category
        }
    }

}