package about.cats.room.model

import about.cats.model.Cat
import androidx.room.*

@Entity(tableName = "FavoriteCats", indices = [ Index("cat_id", unique = true) ])
data class CatDb(
    @PrimaryKey var cat_id: String,
    val url: String? = null,
    @ColumnInfo(name = "width_avatar") val width: Long? = null,
    @ColumnInfo(name = "height_avatar") val height: Long? = null,
) {
    @Ignore
    constructor(cat: Cat): this(
        cat_id = cat.id!!,
        url = cat.url,
        width = cat.width,
        height = cat.height,
    )
}
