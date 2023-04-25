package about.cats.room.model

import androidx.room.Entity

@Entity(primaryKeys = ["cat_id", "breed_id"])
data class CatBreedCrossRef(
    val cat_id: String,
    val breed_id: String,
)
