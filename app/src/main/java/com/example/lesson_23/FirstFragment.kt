package com.example.lesson_23

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lesson_23.databinding.FragmentFirstBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.timerTask

class FirstFragment : Fragment() {

    private  var bidding: FragmentFirstBinding? = null

    private  val arrayText: List<String> = listOf(
        "Text ver 1",
        "Text ver 2",
        "Text ver 3"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bidding = FragmentFirstBinding.inflate(inflater, container, false)
        return  bidding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickOnListeners()
    }

    private fun setOnClickOnListeners() {


        bidding?.btnChangeText?.setOnClickListener {
            val result = CoroutineScope(Dispatchers.IO).async {
                handlerChangeText(true)
            }
           CoroutineScope(Dispatchers.Main).launch {
               bidding?.arrayText?.text = result.await()
           }
        }

        bidding?.btnClearText?.setOnClickListener {
            bidding?.arrayText?.text = handlerChangeText(false)
        }

       bidding?.btnCouritine?.setOnClickListener {

          val deferredSomeResult = CoroutineScope(Dispatchers.IO).async {
              getSomeResult()
          }

           CoroutineScope(Dispatchers.Main).launch {
               Log.e("Couroutines", "getSomeresylt : ${deferredSomeResult.await()}")
               bidding?.tvText?.text = deferredSomeResult.await()
           }

           CoroutineScope(Dispatchers.IO).launch {
               //выполняет в новым потоке
//             for (i in 0 ..1000){
//                 delay(1000)
//                 Handler(Looper.getMainLooper()).postDelayed({
//
//                 }, 1000)
//                 Log.e("Couroutines", "i equals ${i}")
//
//             }
//               while (true){
//                   Log.e("Couroutines", "i equals")
//               }
           }

       }
    }

    private  fun getSomeResult():String {
        Thread.sleep(1000)
        return "Timer start"
    }

    private  fun handlerChangeText(flag:Boolean):String{
        return if(flag){
            Thread.sleep(2500)
            arrayText.random().toString()
        }else{

            ""
        }
    }
}

