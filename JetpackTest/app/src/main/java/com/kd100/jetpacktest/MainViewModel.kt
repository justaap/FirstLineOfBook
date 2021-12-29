package com.kd100.jetpacktest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModel(counterReserved: Int):ViewModel() {
    //    var counter = counterReserved
    val show = MutableLiveData<String>()

    //使_counter只读，保证viewModel的数据封装性
    val counter: LiveData<Int>
        get() = _counter

    //liveData.1，通过MutableLiveData定义变量
    private val _counter = MutableLiveData<Int>()

    init {
        //liveData.2.通过setValue和getValue读写数据（主线程），非主线程用postValue
        _counter.value = counterReserved
    }

    fun plusone() {
        val count = _counter.value ?: 0
        _counter.value = count + 1
    }

    fun clear() {
        _counter.value = 0
    }


}

class MainViewModelFactory(private val counterReserved: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(counterReserved) as T
    }
}