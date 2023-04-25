package about.cats.room

import about.cats.room.model.BreedDb
import about.cats.room.model.CatBreedCrossRef
import about.cats.room.model.CatDb
import about.cats.utils.DATABASE_NAME
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CatDb::class, BreedDb::class, CatBreedCrossRef::class], version = 4, exportSchema = false)
abstract class FavoriteCatsDatabase: RoomDatabase() {

    abstract fun loginDao(): DAOAccess

    companion object {

        @Volatile
        private var INSTANCE: FavoriteCatsDatabase? = null

        fun getInstance(context: Context): FavoriteCatsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null)
                    instance = Room
                        .databaseBuilder(context, FavoriteCatsDatabase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                return instance
            }
        }
    }
}