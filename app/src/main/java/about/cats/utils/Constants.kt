package about.cats.utils

import about.cats.BuildConfig


const val BASE_URL = "https://api.thecatapi.com/v1/images/"
const val BASE_PATH = "search?limit=10&has_breeds=1&api_key=${BuildConfig.CAT_API_KEY}"
const val DATABASE_NAME = "FAVORITE_CATS_DATABASE"
