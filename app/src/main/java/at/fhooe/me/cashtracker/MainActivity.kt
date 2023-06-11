package at.fhooe.me.cashtracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import at.fhooe.me.cashtracker.databinding.ActivityMainBinding
import java.io.*

const val TAG = "katze"
const val DB_NAME = "projectdata"

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityMainAdd.setOnClickListener(this)
        binding.activityMainSearch.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.activity_main_add -> {
                Log.i(TAG, "add")
                val i = Intent(this, TrackActivity::class.java)
                startActivity(i)
            }
            R.id.activity_main_search -> {
                val i = Intent(this, SearchActivity::class.java)
                startActivity(i)
            }
            else -> {
                Log.i(TAG, "unexpected")
            }
        }

    }
}