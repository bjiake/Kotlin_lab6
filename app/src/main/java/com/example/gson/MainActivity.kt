package com.example.gson

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import timber.log.Timber
import java.io.IOException
import java.net.URL

import androidx.appcompat.widget.Toolbar

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class MainActivity : AppCompatActivity() {
    private val photos = mutableListOf<Photo>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var myToolbar: Toolbar
    private var mMenuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load()

        recyclerView = findViewById(R.id.rView)
        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        mMenuItem = menu
        mMenuItem?.getItem(0)?.isVisible = false
        return true
    }
    private fun load() {
        val myUrl =
            URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder().url(myUrl).build()

        Timber.plant(Timber.DebugTree())

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Timber.tag("fdsa").e(e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()

                val wrapped: Wrapper = Gson().fromJson(json, Wrapper::class.java)
                val page: PhotoPage = Gson().fromJson(wrapped.photos, PhotoPage::class.java)

                val photoList = Gson().fromJson(page.photo, Array<Photo>::class.java).toList()

                photoList.forEachIndexed { index, photo ->
                    if (index % 5 == 0) {
                        photos.add(photo)
                        Timber.tag("tags").d(photo.toString())
                    }
                }
                runOnUiThread {
                    val adapter = ImageAdapter(photos) {
                        val intent = Intent(this@MainActivity, PicActivity::class.java)

                        intent.putExtra("picLink", it)
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter
                }
            }
        })
    }
}


data class Photo(
    val id: Long,
    val owner: String = "",
    val secret: String = "",
    val server: Int = 0,
    val farm: Int = 0,
    val title: String = "",
)

data class PhotoPage(
    val page: Int = 1,
    val pages: Int = 1,
    val photo: JsonArray,
)

data class Wrapper(
    val photos: JsonObject,
    val stat: String = "ok",
)