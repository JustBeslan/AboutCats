package about.cats.view.fragments

import about.cats.R
import about.cats.databinding.CatInfoBinding
import about.cats.model.Cat
import about.cats.utils.Avaliable
import about.cats.utils.NetworkStatus
import about.cats.utils.Unavaliable
import about.cats.utils.downloadImage
import about.cats.viewModel.CatInfoBottomSheetDialogViewModel
import android.Manifest
import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CatInfoBottomSheetDialog: BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CatInfoBottomSheetDialog"
    }

    private val viewModel: CatInfoBottomSheetDialogViewModel by viewModels {
        CatInfoBottomSheetDialogViewModel.Companion.CatInfoBottomSheetDialogFactory()
    }

    private lateinit var binding: CatInfoBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) viewModel.catInfo?.url?.let { downloadImage(requireContext(), it) }
            else Toast.makeText(requireContext(), getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CatInfoBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey("Cat") }?.apply {
            viewModel.catInfo =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    this.getParcelable("Cat", Cat::class.java)
                else
                    this.getParcelable("Cat")
        }

        if (viewModel.catInfo == null) {
            Toast.makeText(requireContext(), getString(R.string.no_cat_info), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.cat = viewModel.catInfo
        binding.breeds.movementMethod = LinkMovementMethod()
        binding.close.setOnClickListener { dismiss() }

        binding.downloadImage.setOnClickListener {
            when (NetworkStatus.getNetworkStatus(requireContext())) {
                is Avaliable -> permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                is Unavaliable -> Toast.makeText(
                    requireContext(),
                    getString(R.string.phone_has_no_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}