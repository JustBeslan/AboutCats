package about.cats.model

import about.cats.room.model.CatWithBreeds
import android.os.Parcelable
import android.text.SpannableStringBuilder
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cat(
    val id: String? = null,
    val url: String? = null,
    val breeds: List<Breed>? = null,
    val width: Long? = null,
    val height: Long? = null,
    var isFavorite: Boolean = false,
): Parcelable {

    constructor(catWithBreeds: CatWithBreeds): this(
        id = catWithBreeds.cat.cat_id,
        url = catWithBreeds.cat.url,
        breeds = catWithBreeds.breeds.map { Breed(it) },
        width = catWithBreeds.cat.width,
        height = catWithBreeds.cat.height,
        isFavorite = true
    )

    fun getName(): String? = breeds?.map { it.name }?.joinToString(limit = 2, truncated = "...")
    fun getCatBreeds() =
        if (breeds?.size == 1) breeds[0].toSpannableStringBuilder()
        else SpannableStringBuilder().apply {
            breeds?.forEach { this.append(it.toSpannableStringBuilder()) }
        }

}