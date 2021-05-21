package id.ac.umn.icemoney.utils

import androidx.lifecycle.MutableLiveData

object LiveDataUtil {
    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}