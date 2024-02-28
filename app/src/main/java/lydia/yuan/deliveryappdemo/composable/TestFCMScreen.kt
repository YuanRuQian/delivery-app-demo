package lydia.yuan.deliveryappdemo.composable

import android.Manifest
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.squareup.picasso.Picasso
import lydia.yuan.deliveryappdemo.MainActivity
import lydia.yuan.deliveryappdemo.R

@Composable
fun TestFCMScreen(
    onNavigateToTestImageCachingScreen: () -> Unit
) {

    val context = LocalContext.current

    var hasNotificationPermission = remember {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            hasNotificationPermission = it
            if (it) {
                val currentActivity = context as MainActivity
                currentActivity.setupNotifications()
                Toast.makeText(context, "Notification permission granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    )


    LaunchedEffect(key1 = hasNotificationPermission) {
        if (!hasNotificationPermission) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            val currentActivity = context as MainActivity
            currentActivity.setupNotifications()
        }
    }

    fun logToken() {
        Firebase.messaging.token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                val msg = "Token: $token"
                Log.d(TAG, msg)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            },
        )
    }

    fun showNotification() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = context.getString(R.string.default_notification_channel_id)
        val notificationBuilder =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentTitle("メール通知")
                .setContentText("わたし、気になります！")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)

        notificationManager.notify(666, notificationBuilder.build())
    }

    fun subscribeToTestTopic() {
        // [START subscribe_topics]
        Firebase.messaging.subscribeToTopic(context.getString(R.string.default_notification_channel_name))
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(context, "Failed to subscribe to test topic", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, "Subscribed to test topic", Toast.LENGTH_SHORT).show()
                }
            }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = { subscribeToTestTopic() }) {
            Text(text = "Subscribe to Test Topic to Receive Notifications")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { logToken() }) {
            Text(text = "Log Token ")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { showNotification() }) {
            Text(text = "Show Notification")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            onNavigateToTestImageCachingScreen()
        }) {
            Text(text = "Test Image Caching with AsyncImage")
        }

    }
}