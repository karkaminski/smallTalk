package pl.karkaminski.smalltalk.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.karkaminski.smalltalk.model.DateFact
import pl.karkaminski.smalltalk.model.DateFactRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        private const val TAG = "MainViewModel"
    }

    var dateFact : DateFact
    private val dateFactRepository : DateFactRepository

    init{
        Log.i(TAG, "MainViewModel init block")
        dateFactRepository = DateFactRepository()
        dateFact = DateFact()
        dateFact = dateFactRepository.dateFact
    }

    fun refreshFact() {
        dateFactRepository.setDateFact()
    }

}