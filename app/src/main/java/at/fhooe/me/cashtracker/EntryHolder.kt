package at.fhooe.me.cashtracker

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryHolder(root: View, listener : EntryAdapter.OnItemClickListener) : RecyclerView.ViewHolder(root){
    var tag1 : TextView
    var date : TextView

    init {
        tag1 = root.findViewById(R.id.entry_element_tag_one)
        date = root.findViewById(R.id.entry_element_date)

        root.setOnClickListener {
            listener.OnItemClick(adapterPosition)
        }


    }

}