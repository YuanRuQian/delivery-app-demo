package lydia.yuan.deliveryappdemo.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import lydia.yuan.deliveryappdemo.R

@Composable
fun TestImageCachingWithAsyncImageScreen() {
    val imageUrlList = listOf(
        "https://umfa.utah.edu/sites/default/files/styles/featured2x/public/2023-10/salt14-Yang-Yongliang%201680x912.png",
        "https://umfa.utah.edu/sites/default/files/styles/featured2x/public/2023-11/202201-salt15-038%201680x912_0.png",
        "https://umfa.utah.edu/sites/default/files/styles/featured2x/public/2023-10/salt_13_Totality-50%201680x912.png",
        "https://umfa.utah.edu/sites/default/files/styles/featured2x/public/2023-10/2015_Salt%2011_056%201680x912.png",
        "https://umfa.utah.edu/sites/default/files/styles/featured2x/public/2023-10/Salt10_Conrad_Bakker_027%201680x912.png",
        "https://umfa.utah.edu/sites/default/files/styles/featured2x/public/2023-10/2014-salt-9-Jillian-Mayer_21%201680x912.png",
        "https://umfa.utah.edu/sites/default/files/styles/featured2x/public/2023-10/salt-6-Emre-Huner_5%201680x912.png"
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Test Image Caching")

        LazyColumn(content = {
            items(imageUrlList.size) { index ->
                AsyncImageItem(imageUrl = imageUrlList[index])
            }
        })
    }
}

@Composable
fun AsyncImageItem(imageUrl: String) {
    Box(modifier = Modifier.padding(8.dp)) {
        AsyncImage(model = imageUrl,
            placeholder = painterResource(R.drawable.image_placeholder),
            contentDescription = "Image")
    }
}