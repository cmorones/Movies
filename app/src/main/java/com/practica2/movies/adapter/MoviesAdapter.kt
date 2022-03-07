package com.practica2.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.practica2.movies.R
import com.practica2.movies.databinding.ListItemBinding
import com.practica2.movies.model.Movie

class MoviesAdapter(contexto: Context, listMovies: ArrayList<Movie>): BaseAdapter() {

    private val listMovies = listMovies
    private val layoutInflater = LayoutInflater.from(contexto)

    override fun getCount(): Int {
        return listMovies.size
    }

    override fun getItem(p0: Int): Any {
        return listMovies[p0]
    }

    override fun getItemId(p0: Int): Long {
        return listMovies[p0].id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val binding = ListItemBinding.inflate(layoutInflater)

        with(binding){
            tvTitle.text = listMovies[p0].title
            tvGenre.text = listMovies[p0].genre
            tvAnio.text = listMovies[p0].anio

            if(listMovies[p0].genre == "Comedia")
            imageView.setImageResource(
                R.drawable.comedia
            )
            if(listMovies[p0].genre == "Accion")
                imageView.setImageResource(
                    R.drawable.suspenso
                )

            if(listMovies[p0].genre == "Drama")
                imageView.setImageResource(
                    R.drawable.drama
                )
            if(listMovies[p0].genre == "terror")
                imageView.setImageResource(
                    R.drawable.terror
                )
        }

        return binding.root
    }
}