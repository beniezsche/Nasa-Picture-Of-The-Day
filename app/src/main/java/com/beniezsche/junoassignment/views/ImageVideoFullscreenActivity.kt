package com.beniezsche.junoassignment.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.beniezsche.junoassignment.Constant
import com.beniezsche.junoassignment.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image_video_fullscreen.*

class ImageVideoFullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_image_video_fullscreen)

        supportActionBar?.hide()
        setup()

    }

    private fun setup() {

        val URI = intent.getStringExtra("URI")
        val media_type = intent.getStringExtra("media_type")

        if (media_type.equals(Constant.IMAGE.value)) {
            video.visibility = View.GONE
            image.visibility = View.VISIBLE
            Glide.with(this).load(URI).into(image)
        } else {
            video.visibility = View.VISIBLE
            image.visibility = View.GONE
            video.setVideoPath(URI)
            video.start()
        }

    }

    override fun onPause() {
        super.onPause()

        if (video.isPlaying)
            video.stopPlayback()

    }
}
