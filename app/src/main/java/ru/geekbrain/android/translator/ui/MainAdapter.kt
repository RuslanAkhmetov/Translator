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

class MainAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var words: List<Word>,
    private val imageLoader: ImageLoader<ImageView>
) : RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    fun setData(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }


    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(word: Word) {
            if(layoutPosition != RecyclerView.NO_POSITION ){
                itemView.findViewById<TextView>(R.id.header_textview_recycler_item).text =
                    word.text
                itemView.findViewById<TextView>(R.id.description_textview_recycler_item).text =
                    word.meanings?.get(0)?.translation?.text
                imageLoader.loadInto("https:"+word.meanings.get(0).imageUrl,
                    itemView.findViewById<ImageView>(R.id.imageview_picture))
                itemView.setOnClickListener({openInNewWindow(word)})
            }
        }

    }

    private fun openInNewWindow(word: Word) {
        onListItemClickListener.onItemClick(word)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_recyclerview_item, parent, false)
        as View )
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


