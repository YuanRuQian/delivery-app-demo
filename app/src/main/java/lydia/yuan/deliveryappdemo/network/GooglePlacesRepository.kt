package lydia.yuan.deliveryappdemo.network

import android.util.Log

class NetworkPlaceRepository(
    private val placeApiServices: ApiServices
) : GooglePlacesRepository {
    override suspend fun getPredictions(input: String): Resource<GooglePredictionsResponse> {
        val response = try {
            placeApiServices.getPredictions(input = input)
        } catch (e: Exception) {
            Log.d("GooglePlacesRepository", "Exception: ${e}")
            return Resource.Error("Failed prediction")
        }

        return Resource.Success(response)
    }
}

interface GooglePlacesRepository {
    suspend fun getPredictions(input: String): Resource<GooglePredictionsResponse>
}