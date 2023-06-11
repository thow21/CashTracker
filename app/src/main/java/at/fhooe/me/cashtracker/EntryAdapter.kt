package at.fhooe.me.cashtracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class EntryAdapter(val mData : MutableList<Entry>, val mContext : Context) : RecyclerView.Adapter<EntryHolder>() {

    lateinit var mListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun OnItemClick(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryHolder {
        val inflater: LayoutInflater = LayoutInflater.from(mContext)
        val view: View = inflater.inflate((R.layout.entry_element), null)
        val holder = EntryHolder(view, mListener)
        return holder
    }

    override fun onBindViewHolder(holder: EntryHolder, position: Int) {
        holder.tag1.text = mData[position].tag1
        holder.date.text = mData[position].date.toString()
    }

    override fun getItemCount(): Int = mData.size
}