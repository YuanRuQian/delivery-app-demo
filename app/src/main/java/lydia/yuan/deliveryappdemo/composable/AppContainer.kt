package lydia.yuan.deliveryappdemo.composable

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import lydia.yuan.deliveryappdemo.network.ApiServices
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val placesClient: PlacesClient
}

class DefaultAppContainer(context: Context): AppContainer {
    override val placesClient: PlacesClient by lazy {
        Places.createClient(context)
    }

    private val baseUrl = "https://maps.googleapis.com"
    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/java".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService by lazy {
        retrofit.create(ApiServices::class.java)
    }
}