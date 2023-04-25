package about.cats.api

import about.cats.model.Cat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    fun getCats(@Url url: String): Call<Set<Cat>>
}