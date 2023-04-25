package about.cats.viewModel

import about.cats.R
import about.cats.model.Cat
import about.cats.repository.RetrofitRepository
import about.cats.repository.RoomRepository
import about.cats.utils.ItemClickListener
import about.cats.view.adapters.CatsAdapter
import about.cats.view.adapters.FavoriteCatsAdapter
import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@BindingAdapter("adapter")
fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
    recyclerView.adapter = adapter
}

@BindingAdapter("imageUrl")
fun setImage(imageView: ImageView, url: String) =
    Glide.with(imageView.context).load(url)
        .error(R.drawable.cat_shape)
        .placeholder(R.drawable.cat_shape)
        .into(imageView)

class GeneralCatsViewModel: ViewModel() {

    companion object {

        class GeneralCatsViewModelFactory: ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(GeneralCatsViewModel::class.java))
                    return GeneralCatsViewModel() as T
                throw java.lang.IllegalArgumentException("Unknown viewModel class")
            }
        }
    }

    val itemClickedLiveData = MutableLiveData<Cat?>(null)
    val checkedChangedLiveData = MutableLiveData<Cat?>(null)

    private val itemClickListener: ItemClickListener = ItemClickListener { cat, newState ->
        if (newState != null) {
//            cat.isFavorite = newState
            cat.isFavorite = !cat.isFavorite
//                checkedChangedLiveData.postValue(cat)
//                checkedChangedLiveData.postValue(null)
            checkedChangedLiveData.value = cat
            checkedChangedLiveData.value = null
        } else {
//                itemClickedLiveData.postValue(cat)
//                itemClickedLiveData.postValue(null)
            itemClickedLiveData.value = cat
            itemClickedLiveData.value = null
        }
    }

    var isLoading = false
    val catsAdapter = CatsAdapter(itemClickListener)
    fun loadCatsFromNetwork(callback: (String?) -> Unit) {
        isLoading = true
        RetrofitRepository.loadCats { data, error ->
            isLoading = false
            if (data != null) catsAdapter.addAll(*data.toTypedArray())
            callback(error)
        }
    }
    fun getCatsCount() = catsAdapter.itemCount

    val isEmptyListFavoriteCats = MutableLiveData<Boolean>()
    val favoriteCatsAdapter = FavoriteCatsAdapter(itemClickListener)
    fun loadFavoriteCats(context: Context, coroutineScope: LifecycleCoroutineScope) =
        coroutineScope.launch {
            RoomRepository.getAllFavoriteCatsWithBreeds(context).first {
                isEmptyListFavoriteCats.value = it.isEmpty()
                if (it.isNotEmpty())
                    favoriteCatsAdapter.addAll(*it.map { dbCat -> Cat(dbCat) }.toTypedArray())
                true
            }
        }
    fun getFavoriteCatsCount() = favoriteCatsAdapter.itemCount
}