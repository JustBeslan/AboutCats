package about.cats.repository

import about.cats.model.Cat
import about.cats.room.FavoriteCatsDatabase
import about.cats.room.model.BreedDb
import about.cats.room.model.CatBreedCrossRef
import about.cats.room.model.CatDb
import about.cats.room.model.CatWithBreeds
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RoomRepository {

    companion object {

        var favoriteCatsDatabase: FavoriteCatsDatabase? = null

        private fun initializeDb(context: Context) = FavoriteCatsDatabase.getInstance(context)

        suspend fun insertCat(context: Context, cat: Cat) {

            if (favoriteCatsDatabase == null) favoriteCatsDatabase = initializeDb(context)
            val dao = favoriteCatsDatabase!!.loginDao()

            CoroutineScope(Dispatchers.IO).launch {

                dao.insertCat(CatDb(cat))
                cat.breeds?.forEach { breed ->
                    dao.insertBreed(BreedDb(breed))
                    dao.insertCatBreedCrossRef(
                        CatBreedCrossRef(cat_id = cat.id!!, breed_id = breed.id!!)
                    )
                }
            }
        }

        suspend fun deleteCat(context: Context, id: String) {

            if (favoriteCatsDatabase == null) favoriteCatsDatabase = initializeDb(context)
            val dao = favoriteCatsDatabase!!.loginDao()

            CoroutineScope(Dispatchers.IO).launch {
                dao.deleteCat(id)
                dao.deleteCatBreedCrossRef(id)
            }
        }

        fun getAllFavoriteCatsWithBreeds(context: Context? = null): Flow<List<CatWithBreeds>> {
            if (favoriteCatsDatabase == null) {
                if (context == null) throw java.lang.IllegalArgumentException("Db is not initialized")
                else favoriteCatsDatabase = initializeDb(context)
            }
            val dao = favoriteCatsDatabase!!.loginDao()
            return dao.getAllFavoriteCatsWithBreeds()
        }
    }
}