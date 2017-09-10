package com.example.fd.phonebookkotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.content.ContentValues
import com.example.fd.phonebookkotlin.models.Contact




class PhoneBookDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        seed(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(PhoneBookEntry.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun seed(db: SQLiteDatabase){
        // Create a new map of values, where column names are the keys
        var values = ContentValues()
        values.put(PhoneBookEntry.COLUMN_NAME_NAME, "Олег")
        values.put(PhoneBookEntry.COLUMN_NAME_SURNAME, "Будылин")
        values.put(PhoneBookEntry.COLUMN_NAME_PHONE, "89101216460")
        values.put(PhoneBookEntry.COLUMN_NAME_FAVORITE, "false")

        // Insert the new row, returning the primary key value of the new row
        db.insert(
                PhoneBookEntry.TABLE_NAME,
                null,
                values)

        values = ContentValues()
        values.put(PhoneBookEntry.COLUMN_NAME_NAME, "Юлия")
        values.put(PhoneBookEntry.COLUMN_NAME_SURNAME, "Лебедева")
        values.put(PhoneBookEntry.COLUMN_NAME_PHONE, "89087517220")
        values.put(PhoneBookEntry.COLUMN_NAME_FAVORITE, "false")

        db.insert(
                PhoneBookEntry.TABLE_NAME,
                null,
                values)
    }

    fun parseContacts(): List<Contact>{
        val contacts : ArrayList<Contact> = ArrayList()
        val projection : Array<String> = arrayOf(
                BaseColumns._ID,
            PhoneBookEntry.COLUMN_NAME_NAME,
            PhoneBookEntry.COLUMN_NAME_SURNAME,
            PhoneBookEntry.COLUMN_NAME_PHONE,
            PhoneBookEntry.COLUMN_NAME_FAVORITE
        )

        val sortOrder = PhoneBookEntry.COLUMN_NAME_SURNAME + " ASC"

        val c = readableDatabase.query(
                PhoneBookEntry.TABLE_NAME,  // The table to query
        projection,                               // The columns to return
        null,                                // The columns for the WHERE clause
        null,                            // The values for the WHERE clause
        null,                                     // don't group the rows
        null,                                     // don't filter by row groups
        sortOrder                                 // The sort order
        )

        c.moveToFirst()
        do{
            val name = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_NAME))
            val surname = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_SURNAME))
            val phone = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_PHONE))
            val fav = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_FAVORITE))
            val id = c.getLong(c.getColumnIndexOrThrow(BaseColumns._ID))
            val contact = Contact(name, surname, phone)
            contact.isFavotite = "true" == fav
            contact.id = id

            contacts.add(contact)
        } while(c.moveToNext())
        return contacts
    }

    fun saveContact(contact : Contact?): Boolean{
        val values = ContentValues()
        values.put(PhoneBookEntry.COLUMN_NAME_NAME, contact?.name)
        values.put(PhoneBookEntry.COLUMN_NAME_SURNAME, contact?.surname)
        values.put(PhoneBookEntry.COLUMN_NAME_PHONE, contact?.phoneNumber)
        values.put(PhoneBookEntry.COLUMN_NAME_FAVORITE, contact?.isFavotite)

        val success = writableDatabase.insert(
                PhoneBookEntry.TABLE_NAME,
                null,
                values)

        return success != -1L
    }

    fun updateContact(contact : Contact?): Boolean{
        val values = ContentValues()
        values.put(PhoneBookEntry.COLUMN_NAME_NAME, contact?.name)
        values.put(PhoneBookEntry.COLUMN_NAME_SURNAME, contact?.surname)
        values.put(PhoneBookEntry.COLUMN_NAME_PHONE, contact?.phoneNumber)
        values.put(PhoneBookEntry.COLUMN_NAME_FAVORITE, contact?.isFavotite)

        val selection = BaseColumns._ID + " LIKE ?"
        val selectionArgs = arrayOf(contact?.id.toString())

        val count = readableDatabase.update(
                TABLE_NAME,
                values,
                selection,
                selectionArgs)
        return count > 0
    }



    companion object  PhoneBookEntry : BaseColumns {
        val TABLE_NAME : String = "entry"
        val COLUMN_NAME_NAME : String = "name"
        val COLUMN_NAME_SURNAME : String = "surname"
        val COLUMN_NAME_PHONE : String = "phone"
        val COLUMN_NAME_FAVORITE : String = "favorite"
        val DATABASE_VERSION = 2
        val DATABASE_NAME = "PhoneBook.db"
        val TEXT_TYPE = " TEXT"
        val COMMA_SEP = ","
        val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        BaseColumns._ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_SURNAME + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_FAVORITE + TEXT_TYPE +
                        " )"

        val SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME
    }
}