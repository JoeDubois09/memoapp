package kr.hs.smc.app01_memo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper : SQLiteOpenHelper {
    constructor(context: Context) : super(context,"Memo.db", null, 1)

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = """
            create table MemoTable(
            memo_idx integer primary key autoincrement,
            memo_subject text not null,
            memo_text text not null,
            memo_date date not null
            )
        """.trimIndent()

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}