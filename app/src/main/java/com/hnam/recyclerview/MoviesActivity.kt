package com.hnam.recyclerview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hnam.recyclerview.adapter.MovieAdapter
import com.hnam.recyclerview.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets


class MoviesActivity : AppCompatActivity() {
    var movie: ArrayList<Movie> = ArrayList()
    lateinit var movieAdapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMoviesJson()
        rvMovie.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        movieAdapter = MovieAdapter(movie, this)
        rvMovie.adapter = movieAdapter
        movieAdapter.setListener(movieItemClickListener)
    }

    private val movieItemClickListener = object : MovieItemClickListener {
        override fun onItemCLicked(position: Int) {

            val intent = Intent(this@MoviesActivity, DetailActivity::class.java)
            startActivity(intent)

        }
    }

    private fun getMoviesJson() {
        try {
            val jsonString = getJSONData()
            val collectionType = object : TypeToken<List<Movie>>() {
            }.type
            val moviesArray = Gson().fromJson(jsonString, collectionType) as List<Movie>
            for (i in 0 until moviesArray.size) {
                val mObject = moviesArray[i]
                val mItem = Movie(
                    mObject.video,
                    mObject.vote_average,
                    mObject.title,
                    mObject.poster_path,
                    mObject.original_title,
                    mObject.overview,
                    mObject.release_date
                )
                movie.add(mItem as Movie)
            }
        } catch (exception: IOException) {
            Log.e(MoviesActivity::class.java.name, "Unable to parse JSON file.", exception)
        } catch (exception: JSONException) {
            Log.e(MoviesActivity::class.java.name, "Unable to parse JSON file.", exception)
        }
    }
    @Throws(IOException::class)
    private fun getJSONData():String?{
        var jsonString:String?
        try{
            val inputStream=covertInput()
            val bufferSizesize=inputStream.available()
            val buffer=ByteArray(bufferSizesize)
            inputStream.read(buffer)
            inputStream.close()
            jsonString=String(buffer)
        }catch (ex:IOException){
            ex.printStackTrace()
            jsonString=null
        }
        return jsonString
    }
    private fun covertInput():InputStream{
        val input = FakeService.getMovieRaw()
        val inputStream = ByteArrayInputStream(input.toByteArray(Charsets.UTF_8))
        return inputStream
    }
}
