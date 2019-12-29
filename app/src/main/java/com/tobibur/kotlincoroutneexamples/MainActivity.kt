package com.tobibur.kotlincoroutneexamples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    //private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val job = CoroutineScope(IO).launch {
            downloadUserData()
        }

        button.setOnClickListener {
            if(job.isActive)
                job.cancel()
        }

        button2.setOnClickListener {
            when {
                job.isActive -> {
                    textView2.text = "Active"
                }
                job.isCancelled -> {
                    textView2.text = "Cancelled"
                }
                job.isCompleted -> {
                    textView2.text = "Completed"
                }
                else -> textView2.text = "Not Running"
            }
        }
    }

    private suspend fun downloadUserData() {
        Log.d(TAG, "onCreate: Running on the ${Thread.currentThread().name}")
        for (i in 0..30) {
            withContext(Main) {
                textView.text = "Running $i on the ${Thread.currentThread().name}"
            }
            delay(2000)
        }

    }
}
