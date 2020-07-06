package com.beniezsche.junoassignment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.beniezsche.junoassignment.model.APOD
import com.beniezsche.junoassignment.viewmodel.ApodViewModel
import com.beniezsche.junoassignment.views.ImageVideoFullscreenActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var apodViewModel: ApodViewModel? = null
    private val TAG = this.javaClass.name

    private var URI: String? = "nodata"
    private var media_type: String? = Constant.IMAGE.value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apodViewModel = ViewModelProviders.of(this).get(ApodViewModel::class.java)

        getPhotoOfTheDay()

    }


    fun getPhotoOfTheDay() {

        apodViewModel?.getPhotoOfDay()?.observe(this, Observer {

            if (it != null) {
                setup(it)
            }
        })
    }

    fun setup(apod: APOD) {


        tv_title.text = apod.title
        tv_desc.text = apod.explanation


        if (apod.hdurl != null) {

            iv_play_zoom.visibility = View.VISIBLE
            URI = apod.hdurl

            if (apod.media_type!!.equals(Constant.IMAGE.value)) {
                Glide.with(this).load(apod.hdurl).centerCrop().into(iv_image)
                Glide.with(this).load(R.drawable.lens).into(iv_play_zoom)
            } else if (apod.media_type!!.equals(Constant.VIDEO.value)) {
                Glide.with(this).asBitmap().load(apod.hdurl).centerCrop().into(iv_image)
                Glide.with(this).load(R.drawable.play_button).into(iv_play_zoom)
                media_type = Constant.VIDEO.value
            }
        } else {
            iv_image.setBackgroundColor(Color.BLACK)
            iv_play_zoom.visibility = View.GONE
        }

    }

    fun getPhotoOfTheDayByDate(date: String) {

        apodViewModel?.getPhotoOfDayByDate(date)?.observe(this, Observer {

            if (it != null) {
                setup(it)
            }
        })

    }

    fun onSelectDate(view: View) {

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")

        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
                this,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    c.set(year, monthOfYear, dayOfMonth)
                    val date = c.time
                    val dateString = dateFormatter.format(date)

                    Log.d(TAG, dateString)

                    getPhotoOfTheDayByDate(dateString)

                },
                mYear,
                mMonth,
                mDay
        )
        datePickerDialog.datePicker.maxDate = c.timeInMillis
        datePickerDialog.show()

    }

    fun onPlayZoom(view: View) {

        val intent = Intent(this, ImageVideoFullscreenActivity::class.java)
        intent.putExtra("URI", URI)
        intent.putExtra("media_type", media_type)
        startActivity(intent)
    }
}
