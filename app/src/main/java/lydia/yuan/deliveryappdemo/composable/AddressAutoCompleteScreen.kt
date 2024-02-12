package lydia.yuan.deliveryappdemo.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.libraries.places.api.model.AutocompletePrediction

@Composable
fun AddressAutoCompleteScreen(
    onSearchAddressChange: (String) -> Unit,
    predictions: List<AutocompletePrediction>
) {
    val (address, setAddress) = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            SearchAppBar(
                address = address,
                setAddress = setAddress,
                onSearchAddressChange = onSearchAddressChange
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn(content = {
                items(predictions.size) { index ->
                    Row(
                        Modifier.clickable {
                            setAddress(predictions[index].getFullText(null).toString())
                        }
                    ) {
                        Text(text = predictions[index].getFullText(null).toString())
                    }
                }
            })
        }
    }
}

@Composable
fun SearchAppBar(
    address: String,
    setAddress: (String) -> Unit,
    onSearchAddressChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = address,
        onValueChange = {
            setAddress(it)
            onSearchAddressChange(it)
        },
        label = { Text("Enter Address") },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }
    )
}
