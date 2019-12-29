package com.tobibur.kotlincoroutneexamples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {

    private lateinit var deferred: Deferred<Int>

    companion object {
        private const val TAG = "MainActivity"
    }

    //private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        CoroutineScope(IO).launch {
            deferred = async { downloadUserData() }

            withContext(Main) {
                textView.text = "Downloaded data = ${deferred.await()}"
            }
        }

        button.setOnClickListener {
            if(deferred.isActive)
                deferred.cancel()
        }

        button2.setOnClickListener {
            when {
                deferred.isActive -> {
                    textView2.text = "Active"
                }
                deferred.isCancelled -> {
                    textView2.text = "Cancelled"
                }
                deferred.isCompleted -> {
                    textView2.text = "Completed"
                }
                else -> textView2.text = "Not Running"
            }
        }
    }

    private suspend fun downloadUserData(): Int {
        Log.d(TAG, "onCreate: Running on the ${Thread.currentThread().name}")
        var data = 0
        for (i in 0..30) {
            withContext(Main){
                textView.text = "Running $i on the ${Thread.currentThread().name}"
            }
            delay(2000)
            data += i+2000
        }
        return data
    }
}
