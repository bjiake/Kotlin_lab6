package com.example.gson

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class PicActivity : AppCompatActivity() {
    private var agr: String? = ""
    private var url: String? = ""
    private var mMenuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pic_layout)
        invalidateOptionsMenu()

        val image: ImageView = findViewById(R.id.picView)

        url = intent.getStringExtra("picLink")

        Glide.with(this)
            .load(url)
            .into(image)

        agr = getSharedPreferences("hui", Context.MODE_PRIVATE).getString(url, null)


//        imageFavorite.setOnClickListener {
//            if (agr == url){
//                Toast.makeText(this,"Добавлено в избранное", Toast.LENGTH_SHORT).show()
//                val sharedPref = getSharedPreferences("hui", Context.MODE_PRIVATE)
//                with(sharedPref.edit()){
//                    clear()
//                    apply()
//                }
//            }
//            else {
//                Toast.makeText(this,"Убрано из избранного", Toast.LENGTH_SHORT).show()
//                val sharedPref = getSharedPreferences("hui", Context.MODE_PRIVATE)
//                with(sharedPref.edit()) {
//                    putString(url, url)
//                    apply()
//                }
//            }
//            finish()
//        }

    }

    override fun invalidateOptionsMenu() {
        super.invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        mMenuItem = menu
        mMenuItem?.getItem(0)?.isVisible = true

        mMenuItem?.getItem(0)?.setIcon(
            if (agr == url) {
                R.drawable.ic_baseline_favorite
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
        )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.iconFavorite -> if (agr == url) {
                Toast.makeText(this, "Убрано из избранного", Toast.LENGTH_SHORT).show()
                val sharedPref = getSharedPreferences("hui", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    clear()
                    apply()
                }
            } else {
                Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_SHORT).show()
                val sharedPref = getSharedPreferences("hui", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString(url, url)
                    apply()
                }
            }


        }
        finish()
        return super.onOptionsItemSelected(item)
    }
}