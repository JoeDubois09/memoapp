package kr.hs.smc.app01_memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kr.hs.smc.app01_memo.databinding.ActivityMemoReadBinding

class MemoReadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemoReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.memoReadToolbar)
        title = "메모 읽기"

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        val helper = DBHelper(this)

        val sql ="""
            select memo_subject, memo_date, memo_text
            from MemoTable
            where memo_idx = ?    
        """.trimIndent()

        //글 번호 추출
        val memo_idx = intent.getIntExtra("memo_idx", 0)

        val args = arrayOf(memo_idx.toString())
        val c1 = helper.writableDatabase.rawQuery(sql, args)
        c1.moveToNext()

        val idx1 = c1.getColumnIndex("memo_subject")
        val idx2 = c1.getColumnIndex("memo_date")
        val idx3 = c1.getColumnIndex("memo_text")

        val memo_subject = c1.getString(idx1)
        val memo_date = c1.getString(idx2)
        val memo_text = c1.getString(idx3)

        helper.writableDatabase.close()

        binding.memoReadSubject.text = "제목: $memo_subject"
        binding.memoReadDate.text = "작성날짜: $memo_date"
        binding.memoRearText.text = memo_text


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}