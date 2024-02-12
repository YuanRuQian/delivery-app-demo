package lydia.yuan.deliveryappdemo.composable

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import lydia.yuan.deliveryappdemo.MainActivity
import lydia.yuan.deliveryappdemo.R
import lydia.yuan.deliveryappdemo.databinding.FragmentAddressAutoCompleteBinding

@Composable
fun AutoCompleteFragmentScreen() {
    Log.i(TAG, "AutoCompleteFragmentScreen context: ${LocalContext.current}")
    val activity = LocalContext.current as? MainActivity ?: return

    Log.i(TAG, "AutoCompleteFragmentScreen has activity: $activity")

    AndroidViewBinding(FragmentAddressAutoCompleteBinding::inflate) {

        val autoCompleteFragment =
            activity.supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?

        autoCompleteFragment?.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autoCompleteFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }
}

