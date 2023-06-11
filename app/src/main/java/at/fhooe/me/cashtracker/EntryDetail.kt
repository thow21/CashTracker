package at.fhooe.me.cashtracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import at.fhooe.me.cashtracker.databinding.ActivityEntryDetailBinding

class EntryDetail : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEntryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEntryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityEntryDetailDelete.setOnClickListener(this)
        binding.activityEntryDetailBack.setOnClickListener(this)

        val position = intent.getIntExtra("position", -1)

        val dataB = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null)

        val resultSet = dataB.rawQuery("SELECT * FROM datatable", null)
        resultSet.moveToFirst()

        for (i in 1..position) {
            resultSet.moveToNext()
        }

        binding.activityErntyDetailTagOne.text = resultSet.getString(0)
        binding.activityEntryDetailTagTwo.text = resultSet.getString(1)
        binding.activityEntryDetailDate.text = resultSet.getString(3)
        binding.activityEntryDetailPrice.text = resultSet.getFloat(4).toString()

        if (resultSet.getString(2) != null){
            binding.activityEntryDetailNotes.text = resultSet.getString(2)
        }

        dataB.close()

        resultSet.close()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.activity_entry_detail_back -> {
                finish()
            }
            R.id.activity_entry_detail_delete -> {

                val position = intent.getIntExtra("position", -1)

                val dataB = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null)

                val resultSet = dataB.rawQuery("SELECT * FROM datatable", null)
                resultSet.moveToFirst()

                for (i in 1..position) {
                    resultSet.moveToNext()
                }

                val dateString = resultSet.getString(3)

                val text = "DELETE FROM datatable WHERE Time = '$dateString'"

                dataB.execSQL(text)

                dataB.close()

                resultSet.close()

                finish()

                val i = Intent(this, SearchActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                i.apply {
                    startActivity(this)
                }



                Toast.makeText(this, "GELÖSCHT", Toast.LENGTH_SHORT).show()


            }
            else -> {
                Log.i(TAG, "unexpected")
            }
        }
    }
}