package ru.geekbrain.android.translator.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrain.android.translator.R
import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.model.ImageLoader
import ru.geekbrain.android.translator.utils.convertMeaningsToString
import javax.inject.Inject

class MainAdapter(
    private var onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    @Inject
    lateinit var imageLoader: ImageLoader<ImageView>

    private var words: List<Word> = arrayListOf()

    fun setData(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_recyclerview_item, parent, false)
                    as View )
    }

    override fun onBindViewHolder(
        holder: RecyclerItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.bind(words[position])
    }


    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(word: Word) {
            if(layoutPosition != RecyclerView.NO_POSITION ){
                itemView.findViewById<TextView>(R.id.header_textview_recycler_item).text =
                    word.text
                itemView.findViewById<TextView>(R.id.description_textview_recycler_item).text =
                    convertMeaningsToString(word.meanings!!)

                itemView.setOnClickListener({openInNewWindow(word)})
            }
        }

    }

    private fun openInNewWindow(word: Word) {
        onListItemClickListener.onItemClick(word)

    }



    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(words.get(position))
    }

    override fun getItemCount(): Int {
        return words.size
    }

    interface OnListItemClickListener {
        fun onItemClick(word: Word)

    }
}


