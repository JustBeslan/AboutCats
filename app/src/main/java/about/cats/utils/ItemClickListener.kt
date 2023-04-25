package about.cats.utils

import about.cats.model.Cat

class ItemClickListener(val itemClickedListener: (Cat, Boolean?) -> Unit) {
    @JvmOverloads
    fun onClick(cat: Cat, newState: Boolean? = null) = itemClickedListener(cat, newState)
}
