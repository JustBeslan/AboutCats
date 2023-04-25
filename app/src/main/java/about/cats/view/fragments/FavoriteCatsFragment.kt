package about.cats.view.fragments

import about.cats.databinding.FragmentFavoriteCatsBinding
import about.cats.repository.RoomRepository
import about.cats.utils.showNotCancelableBottomSheetDialog
import about.cats.viewModel.GeneralCatsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch

class FavoriteCatsFragment: Fragment() {

    private lateinit var binding: FragmentFavoriteCatsBinding

    private val favoriteCatsViewModel: GeneralCatsViewModel by viewModels {
        GeneralCatsViewModel.Companion.GeneralCatsViewModelFactory()
    }

    private val idsDeletedCats = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteCatsBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.favoriteCatsViewModel = favoriteCatsViewModel
        binding.favoriteCatsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        favoriteCatsViewModel.loadFavoriteCats(view.context, lifecycle.coroutineScope)

        favoriteCatsViewModel.isEmptyListFavoriteCats.observe(viewLifecycleOwner) {
            binding.layoutNoCats.visibility = if (it) View.VISIBLE else View.GONE
            binding.progressBar.visibility = View.GONE
        }

        favoriteCatsViewModel.itemClickedLiveData.observe(viewLifecycleOwner) { cat ->
            if (cat != null) showNotCancelableBottomSheetDialog<CatInfoBottomSheetDialog>(
                fragmentManager = parentFragmentManager,
                arguments = Bundle().apply {
                    putParcelable("Cat", cat)
                },
                tag = CatInfoBottomSheetDialog.TAG
            )
        }

        favoriteCatsViewModel.checkedChangedLiveData.observe(viewLifecycleOwner) { cat ->
            if (cat != null) lifecycle.coroutineScope.launch {
                RoomRepository.deleteCat(view.context, cat.id!!)
//                Toast.makeText(view.context, "The cat has been removed from favorites", Toast.LENGTH_SHORT).show()
                if (cat.isFavorite) idsDeletedCats.remove(cat.id)
                else idsDeletedCats.add(cat.id)
//                if (favoriteCatsViewModel.getFavoriteCatsCount() == 0) {
//                    binding.layoutNoCats.visibility = View.VISIBLE
//                    binding.progressBar.visibility = View.GONE
//                }
            }
        }
    }

    override fun onStop() {
        requireActivity().intent.putExtra("idsDeletedCats", idsDeletedCats.toTypedArray())
        favoriteCatsViewModel.favoriteCatsAdapter.clear()
        super.onStop()
    }
}
