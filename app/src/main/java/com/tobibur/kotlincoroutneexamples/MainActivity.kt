package com.tobibur.kotlincoroutneexamples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                downloadUserData()
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "onCreate: Running on the ${Thread.currentThread().name}")
        }

        button2.setOnClickListener {
            textView2.text = count++.toString()
        }
    }

    private suspend fun downloadUserData() {
        Log.d(TAG, "onCreate: Running on the ${Thread.currentThread().name}")
        for (i in 0..200000) {
            withContext(Dispatchers.Main) {
                textView.text = "Running $i on the ${Thread.currentThread().name}"
            }
        }

    }
}
