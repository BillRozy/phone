package com.example.fd.phonebookkotlin.pages

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.example.fd.phonebookkotlin.CallActivity
import com.example.fd.phonebookkotlin.ContactActivity
import com.example.fd.phonebookkotlin.ContactsAdapter
import com.example.fd.phonebookkotlin.R
import com.example.fd.phonebookkotlin.db.PhoneBookDbHelper
import com.example.fd.phonebookkotlin.models.Contact


class ContactsPage : Fragment() {

    var mContactsList : ListView? = null
    var mContactsAdapter : ContactsAdapter? = null
    var mDbHelper : PhoneBookDbHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        mDbHelper = PhoneBookDbHelper(activity)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.contacts_page, null)
        mContactsList = view?.findViewById<ListView>(R.id.contactsList)
        val contacts = mDbHelper!!.parseContacts()
        mContactsAdapter = ContactsAdapter(context, contacts)
        mContactsList?.adapter = mContactsAdapter
        mContactsList?.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, view, i, l ->  editContact(i)}
        mContactsList?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->  startCall(i)}
        val fab : FloatingActionButton? = view?.findViewById<FloatingActionButton>(R.id.addContactBtn)
        val addContactIntent: Intent = Intent(activity, ContactActivity::class.java)
        fab?.setOnClickListener({v -> startActivity(addContactIntent)})
        return view
    }

    fun editContact(cPos: Int):Boolean{
        val intent: Intent = Intent(activity, ContactActivity::class.java)
        Contact.packToIntent(mContactsAdapter?.getContact(cPos), intent)
        startActivity(intent)
        return true
    }

    fun startCall(cPos: Int):Boolean{
        val intent: Intent = Intent(activity, CallActivity::class.java)
        Contact.packToIntent(mContactsAdapter?.getContact(cPos), intent)
        startActivity(intent)
        return true
    }
}

