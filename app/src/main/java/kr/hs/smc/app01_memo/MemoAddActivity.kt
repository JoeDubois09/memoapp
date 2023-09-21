package kr.hs.smc.app01_memo

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import kr.hs.smc.app01_memo.databinding.ActivityMemoAddBinding
import java.util.*

class MemoAddActivity : AppCompatActivity() {
    
    private lateinit var binding:ActivityMemoAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.MemoAddToolbar)
        title = "메모 추가"

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Thread{
            SystemClock.sleep(500)

            runOnUiThread{
                binding.addMemoSubject.requestFocus()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.addMemoSubject, InputMethodManager.SHOW_IMPLICIT)
            }
        }.start()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu_write, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                finish()
            }
            R.id.add_menu_write -> {
                val memo_subject = binding.addMemoSubject.text
                val memo_text = binding.addMemoText.text

                val sql = """
                    insert into MemoTable(memo_subject, memo_text, memo_date)
                    values(?, ?, ?)
                """.trimIndent()

                val helper = DBHelper(this)
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val now = sdf.format(Date())

                var arg1 = arrayOf(memo_subject, memo_text, now)

                helper.writableDatabase.execSQL(sql, arg1)
                helper.writableDatabase.close()

                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}