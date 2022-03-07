package com.practica2.movies


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import com.practica2.movies.databinding.ActivityEditBinding
import com.practica2.movies.db.DbMovies
import com.practica2.movies.model.Movie

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    private lateinit var dbMovies: DbMovies
    var movie: Movie? = null
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            val bundle = intent.extras
            if(bundle != null){
                id = bundle.getInt("ID", 0)
            }
        }else{
            id = savedInstanceState.getSerializable("ID") as Int
        }

        dbMovies = DbMovies(this)

        movie = dbMovies.getMovie(id)

        if(movie != null){
            with(binding){
                tietTitulo.setText(movie?.title)
                tietGenre.setText(movie?.genre)
                tietAnio.setText(movie?.anio)

            }
        }
    }

    fun click(view: View) {
        with(binding){
            if(!tietTitulo.text.toString().isEmpty() && !tietGenre.text.toString().isEmpty() && !tietAnio.text.toString().isEmpty()){
                if(dbMovies.updateMovie(id, tietTitulo.text.toString(), tietGenre.text.toString(), tietAnio.text.toString())){
                    Toast.makeText(this@EditActivity, "Registro actualizado exitosamente", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@EditActivity, DetailsActivity::class.java)
                    intent.putExtra("ID", id)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this@EditActivity, "Error al actualizar el registro", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this@EditActivity, "Ningún campo puede quedar vacío", Toast.LENGTH_LONG).show()
            }
        }
    }
}