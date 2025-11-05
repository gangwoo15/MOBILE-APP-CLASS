package com.example.w09

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.w09.ui.theme.W3Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            W3Theme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("기본 TopAppBar") },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("기본 TopAppBar 화면")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseAppScaffold(
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title) },
                navigationIcon = navigationIcon ?: {},
                actions = actions ?: {},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun BaseAppScaffoldPreview() {
    W3Theme {
        BaseAppScaffold(title = "기본 TopAppBar") { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("TopAppBar Content")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownMenuTopAppBar() {
    val context = LocalContext.current
    var menuExpanded by remember { mutableStateOf(false) }

    BaseAppScaffold(
        title = "Dropdown 메뉴",
        actions = {
            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "더보기")
                }
                DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("설정") },
                        onClick = {
                            Toast.makeText(context, "설정 선택", Toast.LENGTH_SHORT).show()
                            menuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("도움말") },
                        onClick = {
                            Toast.makeText(context, "도움말 선택", Toast.LENGTH_SHORT).show()
                            menuExpanded = false
                        }
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("DropdownMenu 예제")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationAndActions() {
    val context = LocalContext.current
    BaseAppScaffold(
        title = "내비게이션 & 검색",
        navigationIcon = {
            IconButton(onClick = {
                Toast.makeText(context, "메뉴 클릭", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.Menu, contentDescription = "메뉴")
            }
        },
        actions = {
            IconButton(onClick = {
                Toast.makeText(context, "검색 클릭", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.Search, contentDescription = "검색")
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("내비게이션 + 검색")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationSearchDropdown() {
    val context = LocalContext.current
    var menuExpanded by remember { mutableStateOf(false) }

    W3Theme {
        BaseAppScaffold(
            title = "내비 + 검색 + 드롭",
            navigationIcon = {
                IconButton(onClick = {
                    Toast.makeText(context, "내비게이션 클릭", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "내비게이션 메뉴 열기")
                }
            },
            actions = {
                IconButton(onClick = {
                    Toast.makeText(context, "검색 클릭", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Default.Search, contentDescription = "검색")
                }
                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "더보기")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("설정") },
                            onClick = {
                                Toast.makeText(context, "설정 선택", Toast.LENGTH_SHORT).show()
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("도움말") },
                            onClick = {
                                Toast.makeText(context, "도움말 선택", Toast.LENGTH_SHORT).show()
                                menuExpanded = false
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Navigation + 검색 + 드롭다운 메뉴 예제 화면")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationDrawer() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(Modifier.padding(16.dp)) {
                    Text("드로어 메뉴", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    Text("메뉴 1")
                    Text("메뉴 2")
                }
            }
        }
    ) {
        BaseAppScaffold(
            title = "Drawer 예제",
            navigationIcon = {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(Icons.Default.Menu, contentDescription = "메뉴")
                }
            }
        ) { padding ->
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Navigation Drawer 예제 화면")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun HorizontalPagerExample() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val pageItems = listOf(
        (1..25).map { "페이지 1 - 아이템 #$it" },
        (1..25).map { "페이지 2 - 아이템 #$it" },
        (1..25).map { "페이지 3 - 아이템 #$it" }
    )

    BaseAppScaffold(title = "Pager 예제") { padding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(padding)
        ) { page ->
            val itemsForPage = pageItems[page]
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(itemsForPage) { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(item)
                    }
                }
            }
        }
    }
}
