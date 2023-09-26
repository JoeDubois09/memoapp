package kr.hs.smc.app01_memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.hs.smc.app01_memo.databinding.ActivityMainBinding
import kr.hs.smc.app01_memo.databinding.MainRecyclerRowBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //제목을 담을 ArrayList
    val subject_list = ArrayList<String>()
    //작성 날짜를 담을 ArrayList
    val date_list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemClock.sleep(1000)
        setTheme(R.style.Theme_App01_memo)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val helper = DBHelper(this)
        //helper.writableDatabase.close()

        setSupportActionBar(binding.mainToolbar)
        title = "메모앱"

        val main_recycler_adapter = MainRecyclerAdapter()
        binding.mainRecycler.adapter = main_recycler_adapter

        binding.mainRecycler.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()

        subject_list.clear()
        date_list.clear()

        val helper = DBHelper(this)

        val sql = """
            select memo_subject, memo_date
            from MemoTable
            order by memo_idx desc
        """.trimIndent()

        val c1 = helper.writableDatabase.rawQuery(sql, null)

        while(c1.moveToNext()) {
            val idx1 = c1.getColumnIndex("memo_subject")
            val idx2 = c1.getColumnIndex("memo_date")

            val memo_subject = c1.getString(idx1)
            val memo_date = c1.getString(idx2)

            //Log.d("memo_app", memo_subject)
            //Log.d("memo_app", memo_date)
            //Log.d("memo_app","-----------------")

            subject_list.add(memo_subject)
            date_list.add(memo_date)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.main_menu_add -> {
                val memo_add_intent = Intent(this, MemoAddActivity::class.java)
                startActivity(memo_add_intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolderClass>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val mainRecyclerBinding = MainRecyclerRowBinding.inflate(layoutInflater)
            val holder = ViewHolderClass(mainRecyclerBinding)

            return holder
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowMemoSubject.text = subject_list[position]
            holder.rowMemoDate.text = date_list[position]
        }

        override fun getItemCount(): Int {
            return subject_list.size
        }

        inner class ViewHolderClass(mainRecyclerBinding: MainRecyclerRowBinding) : RecyclerView.ViewHolder(mainRecyclerBinding.root){
            val rowMemoSubject = mainRecyclerBinding.memoSubject
            val rowMemoDate = mainRecyclerBinding.memoDate
        }
    }
}