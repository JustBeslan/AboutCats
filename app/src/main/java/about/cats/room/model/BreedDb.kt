package about.cats.room.model

import about.cats.model.Breed
import about.cats.model.Weight
import androidx.room.*

@Entity(tableName = "Breeds", indices = [ Index("breed_id", unique = true) ])
data class BreedDb(
    @PrimaryKey var breed_id: String,
    var name: String? = null,
    @Embedded(prefix = "weight_") var weight: Weight? = null,
    var cfa_url: String? = null,
    var vetstreet_url: String? = null,
    var vcahospitals_url: String? = null,
    var temperament: String? = null,
    var origin: String? = null,
    var country_codes: String? = null,
    var country_code: String? = null,
    var description: String? = null,
    var life_span: String? = null,
    var indoor: Int? = null,
    var lap: Int? = null,
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
    var reference_image_id: String? = null,
) {
    @Ignore
    constructor(breed: Breed): this(
        breed_id = breed.id ?: "",
        name = breed.name,
        weight = breed.weight,
        cfa_url = breed.cfa_url,
        vetstreet_url = breed.vetstreet_url,
        vcahospitals_url = breed.vcahospitals_url,
        temperament = breed.temperament,
        origin = breed.origin,
        country_codes = breed.country_codes,
        country_code = breed.country_code,
        description = breed.description,
        life_span = breed.life_span,
        indoor = breed.indoor,
        lap = breed.lap,
        alt_names = breed.alt_names,
        adaptability = breed.adaptability,
        affection_level = breed.affection_level,
        child_friendly = breed.child_friendly,
        dog_friendly = breed.dog_friendly,
        energy_level = breed.energy_level,
        grooming = breed.grooming,
        health_issues = breed.health_issues,
        intelligence = breed.intelligence,
        shedding_level = breed.shedding_level,
        social_needs = breed.social_needs,
        stranger_friendly = breed.stranger_friendly,
        vocalisation = breed.vocalisation,
        experimental = breed.experimental,
        hairless = breed.hairless,
        natural = breed.natural,
        rare = breed.rare,
        rex = breed.rex,
        suppressed_tail = breed.suppressed_tail,
        short_legs = breed.short_legs,
        wikipedia_url = breed.wikipedia_url,
        hypoallergenic = breed.hypoallergenic,
        reference_image_id = breed.reference_image_id,
    )
}
