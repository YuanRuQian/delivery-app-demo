package lydia.yuan.deliveryappdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.android.libraries.places.api.Places
import lydia.yuan.deliveryappdemo.composable.DeliveryApp
import lydia.yuan.deliveryappdemo.ui.theme.DeliveryAppDemoTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiKey = BuildConfig.MAPS_API_KEY

        // Log an error if apiKey is not set.
        if (apiKey.isEmpty()) {
            Log.e("Places test", "No api key")
            return
        }

        // Initialize the SDK
        Places.initializeWithNewPlacesApiEnabled(applicationContext, apiKey)


        setContent {
            DeliveryAppDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DeliveryApp()
                }
            }
        }
    }
}
