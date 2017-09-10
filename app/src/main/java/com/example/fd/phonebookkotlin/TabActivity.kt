package com.example.fd.phonebookkotlin

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.fd.phonebookkotlin.db.PhoneBookDbHelper
import kotlinx.android.synthetic.main.activity_tab.*


class TabActivity : FragmentActivity() {

    var mPagerAdapter: PhonePagesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        PhoneBookDbHelper(this).writableDatabase
        mPagerAdapter = PhonePagesAdapter(supportFragmentManager)
        viewPager.adapter = mPagerAdapter
        viewPager.currentItem = mPagerAdapter!!.CONTACTS_PAGE
    }
}
