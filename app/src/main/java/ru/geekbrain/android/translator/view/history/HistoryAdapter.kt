package ru.geekbrain.android.translator.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrain.android.translator.R
import ru.geekbrain.android.translator.model.data.Word
import ru.geekbrain.android.translator.utils.convertMeaningsToString

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(word: Word) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.header_history_textview_recycler_item).text =
                    word.text
                word.meanings?.let {
                    itemView.findViewById<TextView>(R.id.description_textview_recycler_item).text =
                        convertMeaningsToString(it)
                }

                itemView.setOnClickListener {
                    Toast.makeText(itemView.context, "Word id: ${word.id}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private var listWord: List<Word> = arrayListOf()

    fun setData(data: List<Word>) {
        this.listWord = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_history_recycler_view_item, parent, false)
         as View)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(listWord[position])
    }

    override fun getItemCount(): Int = listWord.size

}