package com.practica2.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.practica2.movies.adapter.MoviesAdapter
import com.practica2.movies.databinding.ActivityMainBinding
import com.practica2.movies.db.DBHelper
import com.practica2.movies.db.DbMovies
import com.practica2.movies.model.Movie

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listMovies: ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //probando la generación de la bd
        /*val dbHelper = DBHelper(this)
        val db = dbHelper.writableDatabase

        if(db != null){
            Toast.makeText(this, "La bd fue creada exitosamente", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Error al crear la bd", Toast.LENGTH_LONG).show()
        }*/

        val dbMovies = DbMovies(this)

        listMovies = dbMovies.getMovies()

        if(listMovies.size == 0) binding.tvSinRegistros.visibility = View.VISIBLE
        else binding.tvSinRegistros.visibility = View.INVISIBLE


        val moviesAdapter = MoviesAdapter(this, listMovies)

        binding.lvGames.adapter = moviesAdapter

        binding.lvGames.setOnItemClickListener { adapterView, view, i, l ->
            //l es el id
            //i es la posición
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("ID", l.toInt())

            startActivity(intent)
            finish()
        }

    }

    fun click(view: View) {
        //eventos del click del botón flotante
        startActivity(Intent(this, InsertActivity::class.java))
        finish()
    }
}