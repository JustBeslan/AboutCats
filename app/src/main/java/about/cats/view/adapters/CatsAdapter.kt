package about.cats.view.adapters

import about.cats.databinding.CatItemBinding
import about.cats.model.Cat
import about.cats.utils.ItemClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CatsAdapter(private val itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<CatsAdapter.ViewHolder>() {

    private val cats = mutableSetOf<Cat>()

    inner class ViewHolder(private val binding: CatItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: Cat) {
            binding.cat = cat
            binding.listener = itemClickListener
            binding.executePendingBindings()

            binding.isFavoriteCatCheckBox.setOnClickListener { view ->
                itemClickListener.onClick(cat, true)
            }
//            binding.isFavoriteCatCheckBox.setOnCheckedChangeListener { _, isChecked ->
//                itemClickListener.onClick(cat, isChecked)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = CatItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(cats.elementAt(position))

    override fun getItemCount() = cats.size

    fun addAll(vararg newCats: Cat) {
        newCats.forEach {
            cats.add(it)
            notifyItemInserted(cats.size - 1)
        }
    }

    fun changeStatusCats(vararg ids: String) {
        ids.forEach { id -> cats.firstOrNull { it.id == id }?.isFavorite = false }
    }
}
