package about.cats.view.adapters

import about.cats.databinding.CatItemBinding
import about.cats.model.Cat
import about.cats.utils.ItemClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavoriteCatsAdapter(private val itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<FavoriteCatsAdapter.ViewHolder>() {

    private val favoriteCats = mutableSetOf<Cat>()

    inner class ViewHolder(private val binding: CatItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: Cat) {
            binding.cat = cat
            binding.listener = itemClickListener
            binding.executePendingBindings()

            binding.isFavoriteCatCheckBox.setOnCheckedChangeListener { _, isChecked ->
                delete(cat)
                itemClickListener.onClick(cat, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = CatItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(favoriteCats.elementAt(position))

    override fun getItemCount() = favoriteCats.size

    fun addAll(vararg newCats: Cat) =
        newCats.forEach {
            favoriteCats.add(it)
            notifyItemInserted(favoriteCats.size - 1)
        }

    fun delete(cat: Cat) =
        favoriteCats.remove(cat)
//        favoriteCats.indexOf(cat).also { position ->
//            favoriteCats.remove(cat)
//            notifyItemRemoved(position)
//            notifyItemRangeChanged(position, favoriteCats.size)
//        }

    fun clear() = favoriteCats.clear()
}
