package com.tobibur.kotlincoroutneexamples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    //private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            CoroutineScope(IO).launch {
                downloadUserData()
            }
        }

        button2.setOnClickListener {
            //textView2.text = count++.toString()
            CoroutineScope(Main).launch {
                Log.d(TAG, "onCreate: Computation started...")

                val score1 = async(IO) { getScore1() }
                val score2 = async(IO) { getScore2() }

                val total = score1.await() + score2.await()
                textView2.text = "Score total = $total "
            }
        }
    }

    private suspend fun getScore2(): Int {
        delay(7000)
        Log.d(TAG, "getScore2: finished score 2 calculation")
        return 560
    }

    private suspend fun getScore1(): Int {
        delay(10000)
        Log.d(TAG, "getScore1: finished score 1 calculation")
        return 550
    }

    private suspend fun downloadUserData() {
        Log.d(TAG, "onCreate: Running on the ${Thread.currentThread().name}")
        for (i in 0..200000) {
            withContext(Main) {
                textView.text = "Running $i on the ${Thread.currentThread().name}"
            }
        }

    }
}
