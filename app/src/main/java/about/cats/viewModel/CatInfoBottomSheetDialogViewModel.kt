package about.cats.viewModel

import about.cats.model.Cat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CatInfoBottomSheetDialogViewModel: ViewModel() {

    var catInfo: Cat? = null

    companion object {
        class CatInfoBottomSheetDialogFactory: ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(CatInfoBottomSheetDialogViewModel::class.java))
                    return CatInfoBottomSheetDialogViewModel() as T
                throw java.lang.IllegalArgumentException("Unknown viewModel class")
            }
        }
    }

}