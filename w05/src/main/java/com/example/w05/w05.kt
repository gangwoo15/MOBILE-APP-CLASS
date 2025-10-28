package com.example.w05

import com.example.w05.ui.theme.GWTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GWTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CounterApp()    // 상태는 내부에서 관리
                    Spacer(modifier = Modifier.height(32.dp))
                    StopWatchApp()  // 나중에 상태 추가 가능
                }
            }
        }
    }
}

@Composable
fun CounterApp() {
    // 상태 변수는 Composable 내부에서 정의
    var count by remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Count: $count",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = { count++ }) {
                Text("Increase")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { count = 0 }) {
                Text("Reset")
            }
        }
    }
}

@Composable
fun StopWatchApp() {
    // 1. 시간(밀리초)과 타이머 실행 여부를 기억할 State 변수 추가
    var timeInMillis by remember { mutableStateOf(1234L) }
    var isRunning by remember { mutableStateOf(false) }

    // 1. isRunning 상태가 true일 때만 실행되는 LaunchedEffect 추가
    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (true) {
                delay(10L) // 10밀리초마다
                timeInMillis += 10L // 시간을 10밀리초씩 증가
            }
        }
    }
    StopwatchScreen(
        timeInMillis = timeInMillis,
        onStartClick = { isRunning = true },
        onStopClick = { isRunning = false },
        onResetClick = {
            isRunning = false
            timeInMillis = 0L
        }
    )
}

@Composable
fun StopwatchScreen(
    timeInMillis: Long, // 3. 상태를 직접 소유하지 않고 파라미터로 받습니다.
    onStartClick: () -> Unit, // 4. 이벤트가 발생했을 때 호출할 람다 함수를 받습니다.
    onStopClick: () -> Unit,
    onResetClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatTime(timeInMillis), // 전달받은 상태로 UI를 그립니다.
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            // 5. 버튼 클릭 시, 상태를 직접 변경하는 대신 전달받은 람다 함수를 호출합니다.
            Button(onClick = onStartClick) { Text("Start") }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onStopClick) { Text("Stop") }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onResetClick) { Text("Reset") }
        }
    }
}

fun formatTime(timeInMillis: Long): String {
    val minutes = (timeInMillis / 1000) / 60
    val seconds = (timeInMillis / 1000) % 60
    val centiseconds = (timeInMillis % 1000) / 10
    return "%02d:%02d:%02d".format(minutes, seconds, centiseconds)
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun CounterAppPreview() {
    GWTheme {
        CounterApp()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun StopWatchPreview() {
    GWTheme {
        StopWatchApp()
    }
}
