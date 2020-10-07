package pl.karkaminski.smalltalk.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.karkaminski.smalltalk.model.DateFact
import pl.karkaminski.smalltalk.model.DateFactRepository
import pl.karkaminski.smalltalk.model.OWMWeather
import pl.karkaminski.smalltalk.model.OWMWeatherRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        private const val TAG = "MainViewModel"
    }

    private val dateFactRepository : DateFactRepository
    var dateFact : MutableLiveData<DateFact>

    private val owmWeatherRepository : OWMWeatherRepository
    var currentWeather : MutableLiveData<OWMWeather>

    init{
        Log.d(TAG, "MainViewModel init block")
        dateFactRepository = DateFactRepository()
        dateFact = MutableLiveData<DateFact>()
        dateFact = dateFactRepository.dateFact

        owmWeatherRepository = OWMWeatherRepository()
        currentWeather = MutableLiveData<OWMWeather>()
        currentWeather = owmWeatherRepository.currentWeather
    }

    fun refreshDateFact() {
        dateFactRepository.setDateFact()
        Log.d(TAG, "refreshDateFact: ")
    }

}