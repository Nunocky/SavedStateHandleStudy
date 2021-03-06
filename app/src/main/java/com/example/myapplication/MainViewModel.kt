package com.example.myapplication

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import java.io.File
import java.io.FileOutputStream

class MainViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    // LiveData
    val text: MutableLiveData<String> = savedStateHandle.getLiveData(KEY_TEXT)

    // non LiveData
    private var intVal: Int? = null
        get() {
            return savedStateHandle.get<Int?>(KEY_INT_VALUE)
        }
        set(value) {
            field = value
            intValueText.value = value?.toString() ?: ""
            savedStateHandle.set<Int?>(KEY_INT_VALUE, value)
        }
    val intValueText = MutableLiveData("") // for display

    // non Parcelable / Serializable
    private var _bitmap: Bitmap? = null
    val bitmap: MutableLiveData<Bitmap> = MutableLiveData(_bitmap) // for display

    init {
        savedStateHandle.get<Int>(KEY_INT_VALUE)?.let {
            Log.d(TAG, "RESTORING INT VALUE($it)")
            intVal = it
        }

        savedStateHandle.setSavedStateProvider(KEY_BITMAP) {
            _bitmap?.toBundle() ?: bundleOf()
        }

        savedStateHandle.get<Bundle>(KEY_BITMAP)?.let {
            _bitmap = restoreBitmap(it)
            bitmap.value = _bitmap
        }
    }

    // Used to save Bitmap
    private fun Bitmap.toBundle(): Bundle {
        val filename = "aaaaaa_temp.png" // 日付入れるとか重複しない仕組みもあっていいかも
        val file = File(getApplication<Application>().cacheDir, filename)

        FileOutputStream(file).use { oStream ->
            this.compress(Bitmap.CompressFormat.PNG, 100, oStream)
        }

        return bundleOf(BITMAP_TEMP_FILENAME to filename)
    }

    // Used to restore Bitmap
    private fun restoreBitmap(bundle: Bundle): Bitmap? {
        Log.d(TAG, "RESTORING BITMAP")
        var bitmap: Bitmap? = null
        bundle.getString(BITMAP_TEMP_FILENAME)?.let { filename ->
            val opts = BitmapFactory.Options().apply {
                inScaled = false
            }
            val file = File(getApplication<Application>().cacheDir, filename)
            bitmap = BitmapFactory.decodeFile(file.absolutePath, opts)
            file.delete()
        }
        return bitmap
    }

    fun incrementIntValue() {
        intVal = (intVal ?: 0) + 1
    }

    fun decrementIntValue() {
        intVal = (intVal ?: 0) - 1
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

    companion object {
        private const val TAG = "MainViewModel"

        private const val KEY_TEXT = "TEXT"
        private const val KEY_INT_VALUE = "KEY_INTVALUE"
        private const val KEY_BITMAP = "KEY_BITMAP"
        private const val BITMAP_TEMP_FILENAME = "bitmap_temp"
    }
}