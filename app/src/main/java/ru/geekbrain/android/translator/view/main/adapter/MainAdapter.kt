package ru.geekbrain.android.translator.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrain.android.model.userdata.Word
import ru.geekbrain.android.repository.convertMeaningsToString
import ru.geekbrains.android.translator.R

class MainAdapter(
    private var onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {


    private var word: List<Word> = arrayListOf()

    fun setData(word: List<Word>) {
        this.word = word
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate( R.layout.activity_main_recyclerview_item, parent, false)
                    as View
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.bind(word[position])
    }


    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(word: Word) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.header_textview_recycler_item).text =
                    word.text
                itemView.findViewById<TextView>(R.id.description_textview_recycler_item).text =
                    convertMeaningsToString(word.meanings!!)

                itemView.setOnClickListener { openInNewWindow(word) }
            }
        }

    }

    private fun openInNewWindow(word: Word) {
        onListItemClickListener.onItemClick(word)

    }


    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(word.get(position))
    }

    override fun getItemCount(): Int {
        return word.size
    }



    interface OnListItemClickListener {
        fun onItemClick(word: Word)

    }

}


