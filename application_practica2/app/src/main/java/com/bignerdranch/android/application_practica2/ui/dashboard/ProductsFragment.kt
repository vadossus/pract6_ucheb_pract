package com.bignerdranch.android.application_practica2.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.application_practica2.adapters.ProductAdapter
import com.bignerdranch.android.application_practica2.databinding.FragmentProductsBinding
import com.bignerdranch.android.application_practica2.ui.models.Product

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var products: List<Product>
    private lateinit var context:  Context
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)

        products = listOf(
            Product(1,"Шоколад"),
            Product(2,"Яйца"),
            Product(3,"Молоко"),
            Product(4,"Морковь"),
            Product(5,"Печенье"),
            Product(6,"Картофель"),
            Product(7,"Абоба"),
            Product(8,"Кокос"),
            Product(9,"Абрикос"),
            Product(10,"Помидор")
        )

        context = this.requireContext()
        adapter = ProductAdapter.create(context)
        binding.rvProducts.layoutManager = LinearLayoutManager(context)
        binding.rvProducts.adapter = adapter

        adapter.refreshProducts(products)



        val dashboardViewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)

        return binding.root
    }

}