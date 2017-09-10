package com.example.fd.phonebookkotlin

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.fd.phonebookkotlin.pages.ContactsPage
import com.example.fd.phonebookkotlin.pages.FavoritesPage
import com.example.fd.phonebookkotlin.pages.HistoryPage


class PhonePagesAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val CONTACTS_PAGE = 0
    val HISTORY_PAGE = 1
    val FAVORITES_PAGE = 2

    override fun getItem(i: Int): Fragment {
        val fragment: Fragment
        when (i) {
            CONTACTS_PAGE -> fragment = ContactsPage()
            HISTORY_PAGE -> fragment = HistoryPage()
            else -> fragment = FavoritesPage()
        }
        return fragment
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            CONTACTS_PAGE -> return "Контакты"
            HISTORY_PAGE -> return "История"
            FAVORITES_PAGE -> return "Избранные"
            else -> return "Fragment"
        }
    }
}
