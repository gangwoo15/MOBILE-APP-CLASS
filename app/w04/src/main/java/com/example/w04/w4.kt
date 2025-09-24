package com.example.w04

import android.R.attr.name
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.w04.ui.theme.W3Theme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key.Companion.W
import androidx.compose.ui.res.painterResource

class w4 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            W3Theme {
                    HomeScreen()
            }
        }
    }
}

data class Message(val name: String, val msg: String)
data class Profile(val name: String, val intro: String)

@Composable
fun HomeScreen() {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
             MessageCard(Message("Android", "Jetpack Compose"))
             ProfileCard(Profile("김태원", "안드로이드 앱 개발자"))
        }
    }
}

@Composable
fun ProfileCard(data: Profile) {
    Row(
    // Row 자체에 패딩을 주어 다른 Composable과 간격을 둡니다.
    modifier = Modifier.padding(all = 8.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Image(
        // painterResource를 사용해 drawable 리소스를 불러옵니다.
        painter = painterResource(R.drawable.profile_picture),
        contentDescription = "연락처 프로필 사진",
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
    )
    Spacer(modifier = Modifier.width(8.dp)) // 이미지와 텍스트 사이에 수평 간격을 추가합니다.
    Column {
        Text(
            text = data.name,
            // MaterialTheme의 색상표를 사용해 다크모드에 자동 대응합니다.
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = data.intro,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}}

@Composable
fun MessageCard(me: Message) { Card(
    colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    modifier = Modifier.padding(8.dp)
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = me.name,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = me.msg,
        style = MaterialTheme.typography.titleLarge
        )
    }
}}

@Preview(
    name = "Profile Card Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun PreviewProfileCard() {
    W3Theme {
        ProfileCard(Profile("김태원", "앱 만들어 대박 난다."))
    }
}

@Preview(
    name = "Message Card Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)

@Composable
fun PreviewMessageCard() {
    W3Theme {
        MessageCard(Message("Android", "Jetpack Compose"))
    }
}

@Composable
fun GreetingPreview() {
    W3Theme {
        HomeScreen()
    }
}