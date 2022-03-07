package com.practica2.movies

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.practica2.movies.databinding.ActivityDetailsBinding
import com.practica2.movies.db.DbMovies
import com.practica2.movies.model.Movie
import java.util.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private lateinit var dbMovies: DbMovies
    var movie: Movie? = null
    var id:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
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
                //tietGenre.setText(movie?.genre)
                tietAnio.setText(movie?.anio)

                //Para que no se abra el teclado al momento de hacer click en los TextInputEditText
                tietTitulo.inputType = InputType.TYPE_NULL
                //tietGenre.inputType = InputType.TYPE_NULL
                tietAnio.inputType = InputType.TYPE_NULL

            }
        }
    }

    fun click(view: View) {
        when(view.id){
            R.id.btnEdit -> {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("ID", id)
                startActivity(intent)
                finish()
            }

            R.id.btnDelete -> {
                AlertDialog.Builder(this)
                    .setTitle("Confirmación")
                    .setMessage("¿Realmente deseas eliminar el juego ${movie?.title}?")
                    .setPositiveButton("Sí", DialogInterface.OnClickListener { dialogInterface, i ->
                        if(dbMovies.deleteMovie(id)){
                            Toast.makeText(this, "Registro eliminado exitosamente", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                        //Si se desea realizar alguna acción cuando el usuario selecciona NO
                    })
                    .show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }


}