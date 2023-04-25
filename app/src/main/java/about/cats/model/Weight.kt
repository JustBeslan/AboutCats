package about.cats.model

import android.os.Parcel
import android.os.Parcelable

data class Weight(
    val imperial: String? = null,
    val metric: String? = null,
): Parcelable {

    constructor(parcel: Parcel): this(
        parcel.readString(),
        parcel.readString(),
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(imperial)
        dest.writeString(metric)
    }

    companion object CREATOR : Parcelable.Creator<Weight> {
        override fun createFromParcel(parcel: Parcel): Weight {
            return Weight(parcel)
        }

        override fun newArray(size: Int): Array<Weight?> {
            return arrayOfNulls(size)
        }
    }
}
