package com.example.myapplication

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(
    application: Application,
) : AndroidViewModel(application) {

    val text = MutableLiveData("")

    var intVal: Int? = null
    val intValueText = MutableLiveData("") // for display

    private var _bitmap: Bitmap? = null
    val bitmap = MutableLiveData<Bitmap?>() // for display

    fun incrementIntValue() {
        intVal = (intVal ?: 0) + 1
        intValueText.value = intVal.toString()
    }

    fun decrementIntValue() {
        intVal = (intVal ?: 0) - 1
        intValueText.value = intVal.toString()
    }

    fun setDogImage() {
        _bitmap =
            BitmapFactory.decodeResource(getApplication<Application>().resources, R.drawable.dog)

        bitmap.value = _bitmap
    }

    fun setCatImage() {
        _bitmap =
            BitmapFactory.decodeResource(getApplication<Application>().resources, R.drawable.cat)
        bitmap.value = _bitmap
    }
}