package com.example.fd.phonebookkotlin.models

import android.content.Intent
import java.util.concurrent.atomic.AtomicInteger


class Contact(name: String, surname: String, number: String) {



    var incrementor : AtomicInteger? = AtomicInteger()
    var name : String = ""
    var surname : String = ""
    var phoneNumber : String = ""
    var isFavotite : Boolean = false
    var id : Long = -1L

    init {
        this.incrementor?.incrementAndGet()
        this.name = name
        this.surname = surname
        this.phoneNumber = number
    }

    fun fullName(): String{
        return surname + " " + name
    }

    companion object {

        val NAME : String = "name"
        val SURNAME : String = "surname"
        val PHONE : String = "phone number"
        val FAVORITE : String = "favorite"
        val ID : String = "id"

        fun packToIntent(contact: Contact?, intent: Intent){
            intent.putExtra(NAME, contact?.name)
            intent.putExtra(SURNAME, contact?.surname)
            intent.putExtra(PHONE, contact?.phoneNumber)
            intent.putExtra(FAVORITE, contact?.isFavotite)
            intent.putExtra(ID, contact?.id)
        }

        fun extractFromIntent(intent: Intent): Contact{
            if(checkIfIntentHasContactPacked(intent)){
                val name = intent.getStringExtra(NAME)
                val surname = intent.getStringExtra(SURNAME)
                val phone = intent.getStringExtra(PHONE)
                val fav = intent.getBooleanExtra(FAVORITE, false)
                val id = intent.getLongExtra(ID, -1)
                val contact: Contact = Contact(name, surname, phone)
                contact.isFavotite = fav
                contact.id = id
                return contact
            }else{
                throw Exception("Broken intent!")
            }
        }

        fun checkIfIntentHasContactPacked(intent: Intent): Boolean{
           return intent.hasExtra(NAME) && intent.hasExtra(SURNAME) && intent.hasExtra(PHONE) && intent.hasExtra(FAVORITE) && intent.hasExtra(ID)
        }
    }
}