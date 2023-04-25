package about.cats.repository

import about.cats.api.ApiService
import about.cats.api.RetrofitClient
import about.cats.model.Cat
import about.cats.utils.BASE_PATH
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitRepository {

    private val apiService: ApiService = RetrofitClient.instance

    fun loadCats(callback: (Set<Cat>?, String?) -> Unit) {
        apiService.getCats(BASE_PATH).enqueue(
            object: Callback<Set<Cat>> {
                override fun onResponse(call: Call<Set<Cat>>, response: Response<Set<Cat>>) {
                    if (response.isSuccessful)
                        callback(response.body(), null)
                    else
                        callback(null, "${response.code()}: ${response.message()}")
                }

                override fun onFailure(call: Call<Set<Cat>>, t: Throwable) {
                    t.message?.let { callback(null, it) }
                }
            }
        )
    }
}