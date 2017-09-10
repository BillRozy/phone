package com.example.fd.phonebookkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.fd.phonebookkotlin.db.PhoneBookDbHelper
import com.example.fd.phonebookkotlin.models.Contact
import kotlinx.android.synthetic.main.contact_activity.*

class ContactActivity : AppCompatActivity()  {

    val mDbHelper : PhoneBookDbHelper = PhoneBookDbHelper(this)
    var mContact : Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_activity)
        if(intent != null && Contact.checkIfIntentHasContactPacked(intent)){
            mContact = Contact.extractFromIntent(intent)
            nameEditText.setText( mContact?.name)
            surnameEditText.setText(mContact?.surname)
            phoneEditText.setText(mContact?.phoneNumber)
        }
        saveBtn.setOnClickListener {
            buildContact()
            saveContact()
            startActivity(Intent(this, TabActivity::class.java))
        }
    }

    fun buildContact(){
        val name = nameEditText.text.toString()
        val surname = surnameEditText.text.toString()
        val phone = phoneEditText.text.toString()
        if(mContact == null){
            mContact = Contact(name, surname, phone)
        }else{
            mContact?.name = name
            mContact?.surname = surname
            mContact?.phoneNumber = phone
        }
    }

    fun saveContact(){
        if(mContact?.id == -1L) {
            mDbHelper.saveContact(mContact)
        }else{
            mDbHelper.updateContact(mContact)
        }
    }



}