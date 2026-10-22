package com.offlinetelegram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.offlinetelegram.ui.theme.OfflineTelegramTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OfflineTelegramTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OfflineTelegramApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfflineTelegramApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "گفتگوها" to Icons.AutoMirrored.Filled.Chat,
        "گروه‌ها" to Icons.Default.Group,
        "کانال‌ها" to Icons.Default.Campaign,
        "واکی‌تاکی" to Icons.Default.Mic,
        "ربات‌ها" to Icons.Default.SmartToy
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "تلگرام آفلاین",
                        modifier = Modifier.semantics {
                            contentDescription = "عنوان برنامه: تلگرام آفلاین. پیام‌رسان کاملاً آفلاین و رمزنگاری‌شده"
                            heading()
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.semantics {
                            contentDescription = "جستجو در گفتگوها و کانال‌ها"
                        }
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier.semantics {
                            contentDescription = "تنظیمات برنامه. شامل زبان، تم، رمزنگاری و دسترسی‌پذیری"
                        }
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, (title, icon) ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(title) },
                        modifier = Modifier.semantics {
                            contentDescription = "تب $title. ${if (selectedTab == index) "انتخاب شده" else "انتخاب نشده"}"
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            when (selectedTab) {
                3 -> { // واکی‌تاکی
                    ExtendedFloatingActionButton(
                        onClick = { },
                        icon = { Icon(Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(32.dp)) },
                        text = { Text("نگه دارید و صحبت کنید") },
                        modifier = Modifier.semantics {
                            contentDescription = "دکمه اصلی واکی‌تاکی. انگشت را نگه دارید و صحبت کنید. با رها کردن ارسال می‌شود. کاملاً رمزنگاری‌شده"
                        }
                    )
                }
                else -> {
                    FloatingActionButton(
                        onClick = { },
                        modifier = Modifier.semantics {
                            contentDescription = "ایجاد گفتگوی جدید، گروه، کانال یا ربات"
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> ChatListScreen(Modifier.padding(padding))
            1 -> GroupsScreen(Modifier.padding(padding))
            2 -> ChannelsScreen(Modifier.padding(padding))
            3 -> WalkieTalkieScreen(Modifier.padding(padding))
            4 -> BotsScreen(Modifier.padding(padding))
        }
    }
}

@Composable
fun ChatListScreen(modifier: Modifier = Modifier) {
    val chats = listOf(
        Triple("علی", "آخرین پیام رمزنگاری‌شده • همین الان", true),
        Triple("گروه خانواده", "مامان: سلام بچه‌ها", false),
        Triple("@کانال_عمومی", "پست جدید منتشر شد", false),
        Triple("سارا", "ویسم رو گوش دادی؟", true),
        Triple("تیم کوهنوردی", "۳ نفر آنلاین در محدوده", false)
    )

    Column(modifier = modifier.fillMaxSize()) {
        // نوار وضعیت اتصال
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    liveRegion = LiveRegionMode.Polite
                    contentDescription = "وضعیت اتصال: در حال جستجوی دستگاه‌های نزدیک. همه پیام‌ها end-to-end رمزنگاری می‌شوند"
                }
        ) {
            Text(
                text = "🔍 در حال جستجوی دستگاه‌های نزدیک... • رمزنگاری AES-256 فعال",
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(chats) { (name, lastMsg, isPrivate) ->
                Card(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics {
                            contentDescription = "گفتگو با $name. آخرین پیام: $lastMsg. ${if (isPrivate) "چت خصوصی رمزنگاری‌شده" else "گروه یا کانال"}. دو بار ضربه بزنید تا باز شود"
                        }
                ) {
                    ListItem(
                        headlineContent = {
                            Text(name, style = MaterialTheme.typography.titleMedium)
                        },
                        supportingContent = {
                            Text(lastMsg, style = MaterialTheme.typography.bodyMedium)
                        },
                        leadingContent = {
                            Icon(
                                if (isPrivate) Icons.Default.Person else Icons.Default.Group,
                                contentDescription = null
                            )
                        },
                        trailingContent = {
                            if (isPrivate) {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "رمزنگاری‌شده",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GroupsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            "گروه‌های رمزنگاری‌شده",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.semantics { heading(); contentDescription = "عنوان بخش گروه‌ها" }
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "گروه‌ها کاملاً آفلاین کار می‌کنند. پیام‌ها از طریق رله بین اعضای نزدیک پخش می‌شوند.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(24.dp))

        // لیست گروه‌های نمونه
        listOf(
            "گروه خانواده" to "۵ عضو • ۳ آنلاین",
            "تیم کوهنوردی" to "۱۲ عضو • رله فعال",
            "همکلاسی‌ها" to "۸ عضو"
        ).forEach { (name, info) ->
            Card(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .semantics {
                        contentDescription = "گروه $name. $info. دو بار ضربه برای ورود"
                    }
            ) {
                ListItem(
                    headlineContent = { Text(name) },
                    supportingContent = { Text(info) },
                    leadingContent = { Icon(Icons.Default.Group, contentDescription = null) }
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "ساخت گروه جدید رمزنگاری‌شده" }
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("ساخت گروه جدید")
        }
    }
}

@Composable
fun ChannelsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            "کانال‌های عمومی",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.semantics { heading() }
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "کانال‌ها می‌توانند آیدی عمومی داشته باشند (مثل @news). همه می‌توانند عضو شوند و پیام‌ها از طریق مش رله پخش می‌شود.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(24.dp))

        listOf(
            "@اخبار_محلی" to "۱۲۰ مشترک",
            "@کوهنوردی_ایران" to "۴۵ مشترک • رله فعال",
            "@نکات_امنیت" to "۸۹ مشترک"
        ).forEach { (name, info) ->
            Card(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .semantics {
                        contentDescription = "کانال $name. $info. دو بار ضربه برای مشاهده"
                    }
            ) {
                ListItem(
                    headlineContent = { Text(name) },
                    supportingContent = { Text(info) },
                    leadingContent = { Icon(Icons.Default.Campaign, contentDescription = null) }
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "ساخت کانال جدید با آیدی عمومی" }
        ) {
            Text("ساخت کانال جدید")
        }
    }
}

@Composable
fun WalkieTalkieScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "حالت واکی‌تاکی",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.semantics {
                heading()
                contentDescription = "حالت واکی‌تاکی. ارتباط صوتی زنده و رمزنگاری‌شده"
            }
        )

        // وضعیت زنده
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    liveRegion = LiveRegionMode.Polite
                    contentDescription = "وضعیت فعلی: در حال اسکن دستگاه‌های نزدیک. رمزنگاری end-to-end فعال است"
                }
        ) {
            Column(Modifier = Modifier.padding(16.dp)) {
                Text("وضعیت: در حال اسکن دستگاه‌های نزدیک...", style = MaterialTheme.typography.titleMedium)
                Text("رمزنگاری: AES-256-GCM + Argon2id", style = MaterialTheme.typography.bodyMedium)
                Text("رله مش: آماده", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "دستگاه‌های نزدیک یافت‌شده",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.semantics { heading() }
        )

        // لیست دستگاه‌های نمونه
        listOf("علی (قوی)", "سارا (متوسط)", "گروه کوه (رله)").forEach { device ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "دستگاه $device. دو بار ضربه برای اتصال مستقیم"
                    }
            ) {
                ListItem(
                    headlineContent = { Text(device) },
                    leadingContent = { Icon(Icons.Default.PhoneAndroid, contentDescription = null) },
                    trailingContent = {
                        Icon(Icons.Default.SignalCellularAlt, contentDescription = "قدرت سیگنال")
                    }
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Text(
            "همه صداها end-to-end رمزنگاری می‌شوند و از طریق مش رله بین اعضا پخش می‌گردند.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BotsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            "ربات‌های محلی",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.semantics { heading() }
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "ربات‌ها کاملاً روی دستگاه شما اجرا می‌شوند و نیازی به اینترنت ندارند. می‌توانید ربات‌های کمکی، بازی، یا ابزار بسازید.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(24.dp))

        listOf(
            "ربات آب‌وهوا (محلی)" to "وضعیت آب‌وهوای ذخیره‌شده",
            "ربات یادآور" to "یادآوری‌های آفلاین",
            "ربات بازی ساده" to "بازی‌های گروهی آفلاین",
            "ربات ترجمه سریع" to "ترجمه‌های از پیش ذخیره‌شده"
        ).forEach { (name, desc) ->
            Card(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .semantics {
                        contentDescription = "$name. $desc. دو بار ضربه برای شروع گفتگو با ربات"
                    }
            ) {
                ListItem(
                    headlineContent = { Text(name) },
                    supportingContent = { Text(desc) },
                    leadingContent = { Icon(Icons.Default.SmartToy, contentDescription = null) }
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "ساخت ربات محلی جدید" }
        ) {
            Text("ساخت ربات جدید")
        }
    }
}
