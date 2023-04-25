package about.cats.model

import about.cats.room.model.BreedDb
import about.cats.utils.*
import android.graphics.Color
import android.graphics.Typeface
import android.os.Parcelable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.View
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Breed", indices = [ Index("id", unique = true) ])
data class Breed(
    val weight: Weight? = null,
    val id: String? = null,
    val name: String? = null,
    val cfa_url: String? = null,
    val vetstreet_url: String? = null,
    val vcahospitals_url: String? = null,
    val temperament: String? = null,
    val origin: String? = null,
    val country_codes: String? = null,
    val country_code: String? = null,
    val description: String? = null,
    val life_span: String? = null,
    val indoor: Int? = null,
    val lap: Int? = null,
    var alt_names: String? = null,
    var adaptability: String? = null,
    var affection_level: Int? = null,
    var child_friendly: Int? = null,
    var dog_friendly: Int? = null,
    var energy_level: Int? = null,
    var grooming: Int? = null,
    var health_issues: Int? = null,
    var intelligence: Int? = null,
    var shedding_level: Int? = null,
    var social_needs: Int? = null,
    var stranger_friendly: Int? = null,
    var vocalisation: Int? = null,
    var experimental: Int? = null,
    var hairless: Int? = null,
    var natural: Int? = null,
    var rare: Int? = null,
    var rex: Int? = null,
    var suppressed_tail: Int? = null,
    var short_legs: Int? = null,
    var wikipedia_url: String? = null,
    var hypoallergenic: Int? = null,
    var reference_image_id: String? = null
): Parcelable {

    constructor(breedDb: BreedDb): this(
        weight = breedDb.weight,
        id = breedDb.breed_id,
        name = breedDb.name,
        cfa_url = breedDb.cfa_url,
        vetstreet_url = breedDb.vetstreet_url,
        vcahospitals_url = breedDb.vcahospitals_url,
        temperament = breedDb.temperament,
        origin = breedDb.origin,
        country_codes = breedDb.country_codes,
        country_code = breedDb.country_code,
        description = breedDb.description,
        life_span = breedDb.life_span,
        indoor = breedDb.indoor,
        lap = breedDb.lap,
        alt_names = breedDb.alt_names,
        adaptability = breedDb.adaptability,
        affection_level = breedDb.affection_level,
        child_friendly = breedDb.child_friendly,
        dog_friendly = breedDb.dog_friendly,
        energy_level = breedDb.energy_level,
        grooming = breedDb.grooming,
        health_issues = breedDb.health_issues,
        intelligence = breedDb.intelligence,
        shedding_level = breedDb.shedding_level,
        social_needs = breedDb.social_needs,
        stranger_friendly = breedDb.stranger_friendly,
        vocalisation = breedDb.vocalisation,
        experimental = breedDb.experimental,
        hairless = breedDb.hairless,
        natural = breedDb.natural,
        rare = breedDb.rare,
        rex = breedDb.rex,
        suppressed_tail = breedDb.suppressed_tail,
        short_legs = breedDb.short_legs,
        wikipedia_url = breedDb.wikipedia_url,
        hypoallergenic = breedDb.hypoallergenic,
        reference_image_id = breedDb.reference_image_id,
    )

    @Suppress("UNCHECKED_CAST")
    fun toSpannableStringBuilder(): SpannableStringBuilder {

        fun transformationNestedMap(key: String, fieldMap: Map<String, Any?>): Map<String, String> {
            val resultMap = mutableMapOf<String, String>()
            fieldMap.forEach { (nestedFieldName, nestedFieldValue) ->
                val newKey = "$key->$nestedFieldName"
                if (nestedFieldValue is Map<*, *>) resultMap.putAll(
                    transformationNestedMap(newKey, nestedFieldValue as Map<String, Any?>)
                )
                else resultMap[newKey] = nestedFieldValue.toString()
            }
            return resultMap
        }

        fun concatAndStylingPair(key: String, value: Any): SpannableStringBuilder {
            val spannableStringFieldName = SpannableString(key).apply {
                setTextSize(90)
                setTextStyle(Typeface.BOLD_ITALIC)
                setTextColor(Color.RED)
            }
            val spannableStringFieldValue = SpannableString(value.toString()).apply {
                setTextSize(90)
                if (this.startsWith("http")) setTextClickListener(object: ClickableSpan() {
                    override fun onClick(view: View) {
                        putStringToClipboard(view.context, key, value.toString())
                    }
                })
                else setTextColor(if (value is String) Color.GREEN else Color.BLUE)
            }
            return SpannableStringBuilder(spannableStringFieldName)
                .append("\n\t\t", spannableStringFieldValue)
        }

        val breedMap = this.toMap() as MutableMap<String, Any?>
        val resultSpannableStringBuilder = SpannableStringBuilder(
            SpannableString(breedMap["name"] as String).apply {
                setTextStyle(Typeface.BOLD_ITALIC)
                setTextSize(120)
                setTextColor(Color.BLACK)
            }
        )
        breedMap.remove("name")

        val body = SpannableStringBuilder()
        breedMap.forEach { (fieldName, fieldValue) ->
            if (fieldValue is Map<*, *>)
                transformationNestedMap(fieldName, fieldValue as Map<String, Any?>)
                    .forEach { (key, value) ->
                        body.append(concatAndStylingPair(key, value), "\n")
                    }
            else body.append(
                concatAndStylingPair(fieldName, fieldValue ?: "Unknown"), "\n")
        }
        return resultSpannableStringBuilder.append("\n", body)
    }
}