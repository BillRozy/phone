package com.example.fd.phonebookkotlin

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.fd.phonebookkotlin.models.Contact
import kotlinx.android.synthetic.main.call_activity.*


class CallActivity : AppCompatActivity()  {

    var mActiveContact: Contact? = null
    val timeHandler : Handler = Handler()
    var secondsUptime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.call_activity)
        if(intent != null && Contact.checkIfIntentHasContactPacked(intent)){
            mActiveContact = Contact.extractFromIntent(intent)
            contactNameView.text = mActiveContact?.fullName()
            phoneNumberView.text = mActiveContact?.phoneNumber
        }
        callTimer.text = formatTime(secondsUptime)
        endCallBtn.setOnClickListener { _ -> onBackPressed() }
        start()
    }

    private val runnable = Runnable {
        secondsUptime++
        callTimer.text = formatTime(secondsUptime)
        start()
    }

    fun stop() {
//        started = false
//        handler.removeCallbacks(runnable)
    }

    fun start() {
        timeHandler.postDelayed(runnable, 1000)
    }

    fun formatTime(seconds: Int): String{
        val minutes : Int = seconds/60
        val secs : Int = seconds - minutes * 60
        val minString = if(minutes/10 >= 1)  minutes.toString() else "0" + minutes.toString()
        val secsString = if(secs/10 >= 1)  secs.toString() else "0" + secs.toString()
        return minString + ":" + secsString
    }

}