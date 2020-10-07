package pl.karkaminski.smalltalk.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import pl.karkaminski.smalltalk.R
import pl.karkaminski.smalltalk.model.DateFact
import pl.karkaminski.smalltalk.model.OWMWeather
import pl.karkaminski.smalltalk.viewmodel.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var mainViewModel : MainViewModel

    //Views Weather
    private lateinit var textViewWeatherDescription : TextView
    private lateinit var textViewWeatherLocation : TextView
    private lateinit var textViewWeatherTemperature : TextView
    private lateinit var imageViewWeatherIcon : ImageView
    private lateinit var progressBarWeather : ProgressBar
    private lateinit var progressBarFactRefresh : ProgressBar

    //Views DateFact
    private lateinit var progressBarToday : ProgressBar
    private lateinit var textViewTodayDate : TextView
    private lateinit var textViewTodayText : TextView
    private lateinit var textViewTodayCountText : TextView
    private lateinit var imageViewRefreshFact : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")

        //Views Weather
        textViewWeatherDescription = findViewById(R.id.textView_weatherDescription)
        textViewWeatherTemperature = findViewById(R.id.textView_weatherTemperature)
        textViewWeatherLocation = findViewById(R.id.textView_weatherLocation)
        imageViewWeatherIcon = findViewById(R.id.imageView_weatherIcon)
        progressBarWeather = findViewById(R.id.progressBar_weather)

        //Views DateFact
        progressBarToday = findViewById(R.id.progressBar_today)
        textViewTodayDate = findViewById(R.id.textView_todayDate)
        textViewTodayText = findViewById(R.id.textView_todayText)
        textViewTodayCountText = findViewById(R.id.textView_todayCountText)
        imageViewRefreshFact = findViewById(R.id.imageView_todayRefresh)
        progressBarFactRefresh = findViewById(R.id.progressBar_factRefresh)


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        mainViewModel.currentWeather.observe(this, object : Observer<OWMWeather> {
            override fun onChanged(t: OWMWeather?) {
                Log.d(TAG, "onChanged: current weather changed")
                setCurrentWeather(t!!)
            }
        })

        mainViewModel.dateFact.observe(this, object : Observer<DateFact> {
            override fun onChanged(t: DateFact?) {
                Log.d(TAG, "onChanged: date fact changed")
                setTodayFactCardView(t!!)
            }
        })

        imageViewRefreshFact.setOnClickListener {
            mainViewModel.refreshDateFact()
            progressBarFactRefresh.visibility = View.VISIBLE
            imageViewRefreshFact.visibility = View.GONE
            Log.d(TAG, "onCreate: REFRESH Pushed")
        }
    }

    private fun setCurrentWeather(owmWeather: OWMWeather) {
        progressBarWeather.visibility = View.GONE
        textViewWeatherLocation.visibility = View.VISIBLE
        textViewWeatherDescription.visibility = View.VISIBLE
        textViewWeatherTemperature.visibility = View.VISIBLE
        imageViewWeatherIcon.visibility = View.VISIBLE

        textViewWeatherLocation.text = owmWeather.name.toString()
        textViewWeatherDescription.text = owmWeather.weather!![0].description.toString()
        textViewWeatherTemperature.text = owmWeather.main!!.temp.toString() + "\u2103"

        Picasso.get().load(owmWeather.iconURL).into(imageViewWeatherIcon)

    }

    private fun setTodayFactCardView(dateFact: DateFact){
        Log.d(TAG, "setTodayFactCardView: ")
        progressBarFactRefresh.visibility = View.GONE
        
        progressBarToday.visibility = View.GONE
        textViewTodayDate.visibility = View.VISIBLE
        textViewTodayText.visibility = View.VISIBLE
        textViewTodayCountText.visibility = View.VISIBLE
        imageViewRefreshFact.visibility = View.VISIBLE

        textViewTodayText.text = dateFact.text
        textViewTodayCountText.text = "Today is " + dateFact.number.toString() + "th day of a year"

        var date = LocalDate.now()
        var formatter = DateTimeFormat.forPattern("EEEE, d MMMM, yyyy").withLocale(Locale("en"))
        textViewTodayDate.text = date.toString(formatter)

    }


}