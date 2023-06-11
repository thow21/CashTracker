package at.fhooe.me.cashtracker


import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import at.fhooe.me.cashtracker.databinding.ActivityTrackBinding
import java.util.*


class TrackActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityTrackBinding
    private lateinit var tag1: String
    private lateinit var tag2: String
    private lateinit var date: Date
    private lateinit var suggestions1 : Array<String?>
    private lateinit var suggestions2 : Array<String?>
    private var price = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataBank1 = openOrCreateDatabase("spinnerdata1", MODE_PRIVATE, null)
        dataBank1.execSQL("CREATE TABLE IF NOT EXISTS suggestion1(suggestion TEXT);")

        val spinnerresult1 = dataBank1.rawQuery("SELECT * FROM suggestion1", null)
        spinnerresult1.moveToFirst()

        suggestions1 = arrayOfNulls(spinnerresult1.count)

        for (i in 1..spinnerresult1.count) {
            suggestions1[i - 1] = spinnerresult1.getString(0)
            spinnerresult1.moveToNext()
        }


        val adapter1: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            suggestions1
        )

        spinnerresult1.close()
        dataBank1.close()

        val auto1 = findViewById<View>(R.id.activity_track_spinner_location) as AutoCompleteTextView

        auto1.setAdapter(adapter1)
        auto1.hint = "Ort"



        val dataBank2 = openOrCreateDatabase("spinnerdata2", MODE_PRIVATE, null)
        dataBank2.execSQL("CREATE TABLE IF NOT EXISTS suggestion2(suggestion TEXT);")

        val spinnerresult2 = dataBank2.rawQuery("SELECT * FROM suggestion2", null)
        spinnerresult2.moveToFirst()

        suggestions2 = arrayOfNulls(spinnerresult2.count)

        for (i in 1..spinnerresult2.count) {
            suggestions2[i - 1] = spinnerresult2.getString(0)
            spinnerresult2.moveToNext()
        }

        val auto2 = findViewById<View>(R.id.activity_track_spinner_product) as AutoCompleteTextView

        val adapter2: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            suggestions2
        )

        spinnerresult2.close()
        dataBank2.close()

        auto2.setAdapter(adapter2)
        auto2.hint = "Produkt"


        val editText = findViewById<EditText>(R.id.activity_track_notes_tv)
        editText.hint = "Weitere Notizen"

        val pricetv = findViewById<EditText>(R.id.activity_track_price)
        pricetv.hint = "Preis"

        binding.activityTrackSave.setOnClickListener(this)


    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.activity_track_save -> {

                val inputPrice = findViewById<EditText>(R.id.activity_track_price)
                price = inputPrice.text.toString().toFloat()

                val calendar = Calendar.getInstance()

                date = calendar.time

                updateResources()

                save()

                finish()
                Toast.makeText(this, "Gespeichert", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Log.i(TAG, "unexpected")
            }
        }
    }

    private fun updateResources() {
        val tv1 = findViewById<View>(R.id.activity_track_spinner_location) as AutoCompleteTextView
        tag1 = tv1.text.toString()

        if (!suggestions1.contains(tag1)){
            val dataBank1 = openOrCreateDatabase("spinnerdata1", MODE_PRIVATE, null)
            dataBank1.execSQL("CREATE TABLE IF NOT EXISTS suggestion1(suggestion TEXT);")

            val insert = ContentValues()

            insert.put("suggestion", tag1)

            dataBank1.insert("suggestion1", null, insert)

            dataBank1.close()
        }

        val tv2 = findViewById<View>(R.id.activity_track_spinner_product) as AutoCompleteTextView
        tag2 = tv2.text.toString()

        if (!suggestions2.contains(tag2)){
            val dataBank2 = openOrCreateDatabase("spinnerdata2", MODE_PRIVATE, null)
            dataBank2.execSQL("CREATE TABLE IF NOT EXISTS suggestion2(suggestion TEXT);")

            val insert = ContentValues()

            insert.put("suggestion", tag2)

            dataBank2.insert("suggestion2", null, insert)

            dataBank2.close()
        }

    }

    private fun save() {

        val dataB = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null)
        dataB.execSQL("CREATE TABLE IF NOT EXISTS datatable(Place TEXT, Product TEXT, Notes TEXT, Time TEXT, Cost REAL);")

        val dateString : String = date.toString()
        val dateShort = dateString.subSequence(0, 19).toString().lowercase()

        val textView : EditText = findViewById(R.id.activity_track_notes_tv)



        val insert = ContentValues()
        insert.put("Place", tag1)
        insert.put("Product", tag2)
        insert.put("Notes", textView.text.toString())
        insert.put("Time", dateShort)
        insert.put("Cost", price)

        dataB.insert("datatable", null, insert)

        dataB.close()

    }

}