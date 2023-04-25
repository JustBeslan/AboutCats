package about.cats.view.fragments

import about.cats.R
import about.cats.utils.Avaliable
import about.cats.utils.NetworkStatus
import about.cats.utils.Unavaliable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CatsErrorLoadFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "DisableInternetFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_cats_error_load, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val iconError = view.findViewById<ImageView>(R.id.iconError)
        val messageError = view.findViewById<TextView>(R.id.messageError)

        when (NetworkStatus.getNetworkStatus(requireContext())) {
            is Avaliable -> {
                iconError.setImageDrawable(
                    ResourcesCompat.getDrawable(resources, R.drawable.error, null)
                )
                messageError.text = arguments?.takeIf { it.containsKey("error") }
                    ?.getString("error") ?: "Unknown error"
            }
            is Unavaliable -> {
                iconError.setImageDrawable(
                    ResourcesCompat.getDrawable(resources, R.drawable.offline, null)
                )
                messageError.text = getString(R.string.phone_has_no_internet_connection)
            }
        }

        view.findViewById<Button>(R.id.tryAgainButton).setOnClickListener {
            when (NetworkStatus.getNetworkStatus(requireContext())) {
                is Avaliable -> {
                    parentFragmentManager.findFragmentById(R.id.navigationHostFragment)?.onStart()
//                    parentFragmentManager.findFragmentById(R.id.navigationHostFragment)?.onCreate(null)
                    dismiss()
                }
                is Unavaliable -> Toast.makeText(
                    requireContext(),
                    getString(R.string.phone_has_no_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}