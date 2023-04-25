package about.cats.room

import about.cats.room.model.BreedDb
import about.cats.room.model.CatBreedCrossRef
import about.cats.room.model.CatDb
import about.cats.room.model.CatWithBreeds
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCat(catDb: CatDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreed(breedDb: BreedDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatBreedCrossRef(catBreedCrossRef: CatBreedCrossRef)

    @Query("DELETE FROM FavoriteCats WHERE cat_id=:cat_id")
    suspend fun deleteCat(cat_id: String)

    @Query("DELETE FROM CatBreedCrossRef WHERE cat_id=:cat_id")
    suspend fun deleteCatBreedCrossRef(cat_id: String)

    @Transaction
    @Query("SELECT * FROM FavoriteCats")
    fun getAllFavoriteCatsWithBreeds(): Flow<List<CatWithBreeds>>
}
