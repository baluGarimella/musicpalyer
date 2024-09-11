package com.music.player.presentation.savedPlayList

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.music.player.R
import com.music.player.SongsDataBase
import com.music.player.data.repository.MainRepositoryImpl
import com.music.player.domain.use_case.welcomeMusicList.MyViewModelFactory
import com.music.player.domain.use_case.welcomeMusicList.WelcomePageViewModel
import com.music.player.presentation.musicPlayer.MusicPlayer
import com.music.player.presentation.welcomePage.DataModel
import com.music.player.presentation.welcomePage.MusicAdatsater
import com.music.player.presentation.welcomePage.SavedPageAdapter
import com.music.player.presentation.welcomePage.getMusicFromDevice
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//Activity for showing saved playlist

class SavedPlayList : AppCompatActivity() {
    private lateinit var welcomePageViewModel: WelcomePageViewModel
    private var musicData:DataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.saved_playlist)
        supportActionBar?.hide()
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.red)))
        window.statusBarColor=resources.getColor(R.color.red)
        val dao = SongsDataBase.getInstance(applicationContext).EventDao()
        val mainRepository = MainRepositoryImpl(dao)
        val myViewModelFactory = MyViewModelFactory(mainRepository)
        welcomePageViewModel =
            ViewModelProvider(this, myViewModelFactory).get(WelcomePageViewModel::class.java)

        welcomePageViewModel.getAllNotes().observe(this, Observer {
            val adapter= SavedPageAdapter(it,applicationContext, this)
            findViewById<RecyclerView>(R.id.saving_recycleview).adapter=adapter
        })

    }




    fun movetoMusicplayer(positiondata: DataModel) {
        val intent= Intent(this, MusicPlayer::class.java)
        intent.putExtra("data",positiondata)
        if (intent.resolveActivity(packageManager) != null)
            startActivity(intent)
    }
}