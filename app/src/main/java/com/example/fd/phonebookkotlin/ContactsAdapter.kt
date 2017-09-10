package com.example.fd.phonebookkotlin

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.fd.phonebookkotlin.models.Contact
import android.widget.TextView
import android.view.LayoutInflater


class ContactsAdapter(context: Context, contacts: List<Contact>) : BaseAdapter() {

    var mContacts: List<Contact>? = contacts
    val mInflator: LayoutInflater? = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return  mContacts!!.size
    }

    override fun getItem(p0: Int): Any {
        return  mContacts!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return  mContacts?.get(p0)!!.id
    }

    fun getContact(p: Int): Contact{
        return  mContacts!![p]
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1
        if (view == null) {
            view = mInflator?.inflate(R.layout.contacts_item, p2, false)
        }

        val p = getContact(p0)
        view!!.findViewById<TextView>(R.id.contactName)?.text = p.surname + " " + p.name
        view.findViewById<TextView>(R.id.contactPhone)?.text = p.phoneNumber + ""
        return view
    }

}

