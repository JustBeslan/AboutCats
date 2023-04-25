package about.cats.room.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CatWithBreeds(
    @Embedded val cat: CatDb,
    @Relation(
        parentColumn = "cat_id",
        entityColumn = "breed_id",
        associateBy = Junction(CatBreedCrossRef::class)
    )
    val breeds: List<BreedDb>
)
