package lydia.yuan.deliveryappdemo.network

import lydia.yuan.deliveryappdemo.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("maps/api/place/autocomplete/json")
    suspend fun getPredictions(
        @Query("key") key: String = BuildConfig.MAPS_API_KEY,
        @Query("types") types: String = "address",
        @Query("input") input: String
    ): GooglePredictionsResponse
}