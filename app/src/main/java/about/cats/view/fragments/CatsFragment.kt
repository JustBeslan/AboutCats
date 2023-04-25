package about.cats.view.fragments

import about.cats.R
import about.cats.databinding.FragmentCatsBinding
import about.cats.repository.RoomRepository
import about.cats.utils.*
import about.cats.viewModel.GeneralCatsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch

class CatsFragment : Fragment() {

    private val catsViewModel: GeneralCatsViewModel by viewModels {
        GeneralCatsViewModel.Companion.GeneralCatsViewModelFactory()
    }

    private val recyclerViewOnScrollListener = object: PaginationScrollListener() {
        override fun isLoading() = catsViewModel.isLoading
        override fun loadMoreItems() {
            when (NetworkStatus.getNetworkStatus(requireContext())) {
                is Avaliable -> catsViewModel.loadCatsFromNetwork { error ->
                    if (error != null)
                        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                }
                is Unavaliable -> Toast.makeText(
                    requireContext(),
                    getString(R.string.phone_has_no_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private lateinit var binding: FragmentCatsBinding

    override fun onStart() {
        requireActivity().intent.apply {
            this.extras
                ?.takeIf { it.containsKey("idsDeletedCats") }
                ?.getStringArray("idsDeletedCats")
                ?.let { catsViewModel.catsAdapter.changeStatusCats(*it) }
            this.removeExtra("idsDeletedCats")
        }

        when (NetworkStatus.getNetworkStatus(requireContext())) {
            is Avaliable ->
                if (catsViewModel.getCatsCount() == 0) catsViewModel.loadCatsFromNetwork { error ->
                    if (error == null) binding.progressBar.visibility = View.GONE
                    else {
                        showNotCancelableBottomSheetDialog<CatsErrorLoadFragment>(
                            fragmentManager = parentFragmentManager,
                            arguments = Bundle().apply {
                                putString("error", error)
                            },
                            tag = CatsErrorLoadFragment.TAG
                        )
                    }
                }
            is Unavaliable -> showNotCancelableBottomSheetDialog<CatsErrorLoadFragment>(
                fragmentManager = parentFragmentManager,
                tag = CatsErrorLoadFragment.TAG
            )
        }
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.catsViewModel = catsViewModel
        binding.progressBar.apply {
            visibility = if (catsViewModel.getCatsCount() == 0) View.VISIBLE else View.GONE
        }

        binding.catsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addOnScrollListener(recyclerViewOnScrollListener)
        }

        catsViewModel.itemClickedLiveData.observe(viewLifecycleOwner) { cat ->
            if (cat != null) showNotCancelableBottomSheetDialog<CatInfoBottomSheetDialog>(
                fragmentManager = parentFragmentManager,
                arguments = Bundle().apply {
                    putParcelable("Cat", cat)
                },
                tag = CatInfoBottomSheetDialog.TAG
            )
        }

        catsViewModel.checkedChangedLiveData.observe(viewLifecycleOwner) { cat ->
            if (cat != null) lifecycle.coroutineScope.launch {
                val context = view.context
                if (cat.isFavorite) {
                    RoomRepository.insertCat(context, cat)
//                    Toast.makeText(context, "The cat has been added to favorites", Toast.LENGTH_SHORT).show()
                } else {
                    RoomRepository.deleteCat(context, cat.id!!)
//                    Toast.makeText(context, "The cat has been removed from favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}