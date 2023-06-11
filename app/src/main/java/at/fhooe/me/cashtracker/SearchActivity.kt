package at.fhooe.me.cashtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import at.fhooe.me.cashtracker.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    var data: MutableList<Entry> = mutableListOf()

    lateinit var binding: ActivitySearchBinding
    var entryAdapter: EntryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataB = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null)
        dataB.execSQL("CREATE TABLE IF NOT EXISTS datatable(Place TEXT, Product TEXT, Time TEXT, Cost REAL);")


        val resultSet = dataB.rawQuery("SELECT * FROM datatable", null)
        resultSet.moveToFirst()

        for (i in 1..resultSet.count) {
            data.add(
                Entry(
                    tag1 = resultSet.getString(0),
                    tag2 = resultSet.getString(1),
                    notes = resultSet.getString(2),
                    date = resultSet.getString(3),
                    price = resultSet.getFloat(4)
                )
            )
            resultSet.moveToNext()
        }

        resultSet.close()

        dataB.close()

        entryAdapter = EntryAdapter(data, this)
        entryAdapter!!.setOnItemClickListener(object : EntryAdapter.OnItemClickListener {
            override fun OnItemClick(position: Int) {

                val i = Intent(binding.recyclerView.context, EntryDetail::class.java)
                i.putExtra("position", position)
                startActivity(i)
            }
        })

        with(binding.recyclerView) {
            adapter = entryAdapter
            layoutManager = GridLayoutManager(this@SearchActivity, 1)
            val deco = DividerItemDecoration(
                this@SearchActivity,
                DividerItemDecoration.VERTICAL
            )
            getDrawable(R.drawable.ic_baseline_horizontal_rule_24)?.let {
                deco.setDrawable(it)
            }
            addItemDecoration(deco)
        }


    }


}
