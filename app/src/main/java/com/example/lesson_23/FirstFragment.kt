package com.example.lesson_23

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
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
            CoroutineScope(Dispatchers.Main).launch{
                bidding?.btnChangeText?.text = "It's Kotlin!"
            }
        }

        bidding?.btnLoading?.setOnClickListener {
            val result = CoroutineScope(Dispatchers.IO).async {
                loadingImg()
            }
            CoroutineScope(Dispatchers.Main).launch {
                Glide.with(requireContext())
                    .load(result.await())
                    .into(bidding!!.imGPicture)

            }
        }


    }

    private fun loadingImg():String{
        Thread.sleep(2500)
        return "https://cdn.mos.cms.futurecdn.net/s2RT5ASNjHeV2Eowgu4J3U-1920-80.jpg"
    }

    private  fun getSomeResult():String {
        Thread.sleep(1000)
        return "Timer start"
    }

//    private  fun handlerChangeText(flag:Boolean):String{
//        return if(flag){
//            Thread.sleep(2500)
//            arrayText.random().toString()
//        }else{
//
//            ""
//        }
//    }
}



