package com.learnflow.lms.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnflow.lms.presentation.theme.DarkBrown
import com.learnflow.lms.presentation.theme.PrimaryAmber
import com.learnflow.lms.util.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    showBackButton: Boolean = true,
    onNavigateBack: () -> Unit,
    onNavigateToAchievements: () -> Unit
) {
    val currentUser = MockData.users.first { it.id == "user1" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Header
            item {
                ProfileHeader(
                    name = currentUser.name,
                    email = currentUser.email,
                    level = currentUser.level,
                    points = currentUser.points,
                    streak = currentUser.streak
                )
            }

            // Stats Card
            item {
                StatsCard(
                    coursesEnrolled = currentUser.coursesEnrolled,
                    coursesCompleted = currentUser.coursesCompleted,
                    totalHours = 45
                )
            }

            // Menu Items
            item {
                ProfileSection(title = "Learning") {
                    ProfileMenuItem(
                        icon = Icons.Default.MenuBook,
                        title = "My Courses",
                        subtitle = "${currentUser.coursesEnrolled} courses enrolled",
                        onClick = { }
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.EmojiEvents,
                        title = "Achievements",
                        subtitle = "4 of 10 unlocked",
                        onClick = onNavigateToAchievements
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.Leaderboard,
                        title = "Leaderboard",
                        subtitle = "You're ranked #3",
                        onClick = { }
                    )
                }
            }

            item {
                ProfileSection(title = "Settings") {
                    ProfileMenuItem(
                        icon = Icons.Default.Notifications,
                        title = "Notifications",
                        subtitle = "Manage your notifications",
                        onClick = { }
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.DarkMode,
                        title = "Dark Mode",
                        subtitle = "Off",
                        onClick = { }
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.Language,
                        title = "Language",
                        subtitle = "English",
                        onClick = { }
                    )
                }
            }

            item {
                ProfileSection(title = "Support") {
                    ProfileMenuItem(
                        icon = Icons.Default.Help,
                        title = "Help Center",
                        subtitle = "Get help with the app",
                        onClick = { }
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.Feedback,
                        title = "Send Feedback",
                        subtitle = "Help us improve",
                        onClick = { }
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.Info,
                        title = "About",
                        subtitle = "Version 1.0.0",
                        onClick = { }
                    )
                }
            }

            // Logout Button
            item {
                OutlinedButton(
                    onClick = { /* TODO: Logout */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Log Out",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Bottom Spacing
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ProfileHeader(
    name: String,
    email: String,
    level: Int,
    points: Int,
    streak: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(DarkBrown, PrimaryAmber)
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = PrimaryAmber,
                        modifier = Modifier.size(60.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = email,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileStat(
                        value = "Level $level",
                        label = "Level",
                        color = Color(0xFF4CAF50)
                    )
                    ProfileStat(
                        value = "$points pts",
                        label = "Points",
                        color = Color(0xFF2196F3)
                    )
                    ProfileStat(
                        value = "$streak days",
                        label = "Streak",
                        color = Color(0xFFFF6B35)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileStat(
    value: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun StatsCard(
    coursesEnrolled: Int,
    coursesCompleted: Int,
    totalHours: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                icon = Icons.Default.MenuBook,
                value = "$coursesEnrolled",
                label = "Enrolled"
            )
            StatItem(
                icon = Icons.Default.CheckCircle,
                value = "$coursesCompleted",
                label = "Completed"
            )
            StatItem(
                icon = Icons.Default.AccessTime,
                value = "$totalHours",
                label = "Hours"
            )
        }
    }
}

@Composable
fun StatItem(
    icon: ImageVector,
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryAmber,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun ProfileSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryAmber,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}
