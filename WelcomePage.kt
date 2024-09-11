package com.music.player.presentation.welcomePage

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toLowerCase
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.music.player.R
import com.music.player.presentation.musicPlayer.MusicPlayer
import com.music.player.presentation.savedPlayList.SavedPlayList
import java.util.Locale

//Activity for  Welcome Page
private const val STORAGE_PERMISSION_REQUEST_CODE=1001
class WelcomePage : AppCompatActivity() {
  private  lateinit var menuItem: MenuItem
    var searchView: SearchView?=null
    lateinit var likeBtn:ImageView
    private lateinit var newArrayList:ArrayList<DataModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_page)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
//        val bar: ActionBar? = actionBar
//        bar.setBackgroundDrawable( Color());
        supportActionBar?.setIcon(R.drawable.baseline_menu_24)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.red)))
        supportActionBar?.title = "  Libary"
        window.statusBarColor=resources.getColor(R.color.red)



        if (checkStoragePermission()) {
            songsFromMusicAdapter()
//            musicSongFromDevicedapterAudioFiles()
        } else {
            requestStoragePermission()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fav_icon->{
                val intent=Intent(this,SavedPlayList::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext,"dat",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.search_action ->{
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val searchView=item.actionView as SearchView
                // Configure the search view (e.g., add listeners)

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        // Handle query submission
                        val data=getMusicFromDevice(applicationContext)
                        MusicAdatsater.filter(query.toString(),data)
                        return true
                    }
                    override fun onQueryTextChange(newText: String?): Boolean {
                        // Handle query text change


                        val data=getMusicFromDevice(applicationContext)
                        MusicAdatsater.filter(newText.toString(),data)
                        Toast.makeText(applicationContext,"",Toast.LENGTH_SHORT).show()
                        return true
                    }
                })

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun songsFromMusicAdapter() {
        val data=getMusicFromDevice(applicationContext)
        val adapter=MusicAdatsater(data,applicationContext,this)
        findViewById<RecyclerView>(R.id.welcome_page_recycler).adapter=adapter
    }


    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_REQUEST_CODE
        )
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               songsFromMusicAdapter()
            } else {
                Toast.makeText(
                    this,
                    "Permission denied. Cannot access audio files without storage permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun movetoMusicplayer(positiondata: DataModel) {
        val intent= Intent(this, MusicPlayer::class.java)
        intent.putExtra("data",positiondata)
        if (intent.resolveActivity(packageManager) != null)
            startActivity(intent)
    }


}