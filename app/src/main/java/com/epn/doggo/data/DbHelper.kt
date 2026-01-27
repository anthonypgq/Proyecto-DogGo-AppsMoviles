package com.epn.doggo.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.epn.doggo.UserApi

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "DogGo.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USER = "usuarios"
        private const val COLUMN_ID = "id"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_NOMBRE = "nombre_completo"
        private const val COLUMN_TELEFONO = "telefono"
        private const val COLUMN_DIRECCION = "direccion"
        private const val COLUMN_ROL = "rol"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_USER (" +
                "$COLUMN_ID TEXT PRIMARY KEY," +
                "$COLUMN_EMAIL TEXT," +
                "$COLUMN_NOMBRE TEXT," +
                "$COLUMN_TELEFONO TEXT," +
                "$COLUMN_DIRECCION TEXT," +
                "$COLUMN_ROL TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    fun saveUser(user: UserApi) {
        val db = this.writableDatabase
        db.delete(TABLE_USER, null, null) // Keep only one session for now
        val values = ContentValues().apply {
            put(COLUMN_ID, user.id)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_NOMBRE, user.nombre_completo)
            put(COLUMN_TELEFONO, user.telefono)
            put(COLUMN_DIRECCION, user.direccion)
            put(COLUMN_ROL, user.rol)
        }
        db.insert(TABLE_USER, null, values)
        db.close()
    }

    fun getUser(): UserApi? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_USER, null, null, null, null, null, null)
        var user: UserApi? = null
        if (cursor.moveToFirst()) {
            user = UserApi(
                id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                nombre_completo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                telefono = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONO)),
                direccion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIRECCION)),
                rol = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROL))
            )
        }
        cursor.close()
        db.close()
        return user
    }

    fun logout() {
        val db = this.writableDatabase
        db.delete(TABLE_USER, null, null)
        db.close()
    }
}
