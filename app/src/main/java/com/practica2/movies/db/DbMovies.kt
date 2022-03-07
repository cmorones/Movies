package com.practica2.movies.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.practica2.movies.model.Movie

class DbMovies(context: Context?) : DBHelper(context) {
    //Aquí va el código para el CRUD

    val context = context

    fun insertMovie(title: String, genre: String, anio: String): Long {
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        var id: Long = 0

        try {

            val values = ContentValues()

            values.put("title", title)
            values.put("genre", genre)
            values.put("anio", anio)

            id = db.insert(TABLE_MOVIES, null, values)

        } catch (e: Exception) {
            //Manejo de la excepción
        } finally {
            db.close()
        }

        return id
    }

    fun getMovies(): ArrayList<Movie>{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var listMovies = ArrayList<Movie>()
        var movieTmp: Movie? = null
        var cursorMovies: Cursor? = null

        cursorMovies = db.rawQuery("SELECT * FROM $TABLE_MOVIES", null)

        if(cursorMovies.moveToFirst()){
            do{
                movieTmp = Movie(cursorMovies.getInt(0), cursorMovies.getString(1), cursorMovies.getString(2), cursorMovies.getString(3))
                listMovies.add(movieTmp)
            }while(cursorMovies.moveToNext())
        }

        cursorMovies.close()

        return listMovies
    }

    fun getMovie(id: Int): Movie?{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var movie: Movie? = null
        var cursorMovies: Cursor? = null

        cursorMovies = db.rawQuery("SELECT * FROM $TABLE_MOVIES WHERE id = $id LIMIT 1", null)

        if(cursorMovies.moveToFirst()){
            movie = Movie(cursorMovies.getInt(0), cursorMovies.getString(1), cursorMovies.getString(2), cursorMovies.getString(3))
        }

        cursorMovies.close()

        return movie
    }

    fun updateMovie(id: Int, title: String, genre: String, anio: String): Boolean{

        var banderaCorrecto = false

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("UPDATE $TABLE_MOVIES SET title = '$title', genre = '$genre', anio = '$anio' WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto

    }

    fun deleteMovie(id: Int): Boolean{

        var banderaCorrecto = true

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("DELETE FROM $TABLE_MOVIES WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto
    }

}




