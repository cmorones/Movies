package com.practica2.movies


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.practica2.movies.databinding.ActivityInsertBinding
import com.practica2.movies.db.DbMovies
import java.util.*

class InsertActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityInsertBinding
    private lateinit var generos: ArrayAdapter<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generos = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item)

        generos.addAll(Arrays.asList("Comedia","Drama","Accion", "terror"))

        binding.spinnerGeneros.onItemSelectedListener = this
        binding.spinnerGeneros.adapter = generos

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        binding.tvSelected.text = generos.getItem(position)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun click(view: View) {

        val dbMovies = DbMovies(this)

        with(binding){

            if(!tietTitulo.text.toString().isEmpty() && !tvSelected.text.toString().isEmpty() && !tietAnio.text.toString().isEmpty()){
                val id = dbMovies.insertMovie(tietTitulo.text.toString(), tvSelected.text.toString(), tietAnio.text.toString())

                if(id > 0) { //el registro se insertó correctamente
                    Toast.makeText(this@InsertActivity, "Registro guardado exitosamente", Toast.LENGTH_LONG).show()

                    //Reiniciamos las cajas de textoprueba
                    tietTitulo.setText("")
                    tietAnio.setText("")
                    tvSelected.setText("")
                    tietTitulo.requestFocus()
                }else{
                    Toast.makeText(this@InsertActivity, "Error al guardar el registro", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this@InsertActivity, "Por favor llene todos los campos", Toast.LENGTH_LONG).show()

                //Para mandar un error en una caja de texto especíica
                //tietTitulo.text = "Por favor agrega un título"
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}