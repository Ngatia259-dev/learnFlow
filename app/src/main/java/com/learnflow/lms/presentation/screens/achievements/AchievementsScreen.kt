package com.learnflow.lms.presentation.screens.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnflow.lms.presentation.theme.DarkBrown
import com.learnflow.lms.presentation.theme.PrimaryAmber
import com.learnflow.lms.util.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(
    showBackButton: Boolean = true,
    onNavigateBack: () -> Unit
) {
    val achievements = MockData.achievements
    val unlockedCount = achievements.count { it.unlocked }
    val totalAchievements = achievements.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Achievements") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Progress Header
            item {
                AchievementsHeader(
                    unlockedCount = unlockedCount,
                    totalAchievements = totalAchievements
                )
            }

            // Categories
            item {
                Text(
                    text = "Achievements by Category",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Achievement Categories
            val categories = achievements.map { it.category }.distinct()
            items(categories) { category ->
                val categoryAchievements = achievements.filter { it.category == category }
                AchievementCategoryCard(
                    category = category,
                    achievements = categoryAchievements
                )
            }
        }
    }
}

@Composable
fun AchievementsHeader(
    unlockedCount: Int,
    totalAchievements: Int
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
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "$unlockedCount / $totalAchievements",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Achievements Unlocked",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LinearProgressIndicator(
                    progress = { unlockedCount.toFloat() / totalAchievements },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFFFFD700),
                    trackColor = Color.White.copy(alpha = 0.3f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${((unlockedCount.toFloat() / totalAchievements) * 100).toInt()}% Complete",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun AchievementCategoryCard(
    category: String,
    achievements: List<com.learnflow.lms.data.local.entity.AchievementEntity>
) {
    val unlockedInCategory = achievements.count { it.unlocked }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Category Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PrimaryAmber.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = "$unlockedInCategory/${achievements.size}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryAmber
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Achievement Items
            achievements.forEach { achievement ->
                AchievementItem(achievement = achievement)
                if (achievement != achievements.last()) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}

@Composable
fun AchievementItem(
    achievement: com.learnflow.lms.data.local.entity.AchievementEntity
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Achievement Icon
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    if (achievement.unlocked) {
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFFFD700), Color(0xFFFFA000))
                        )
                    } else {
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (achievement.unlocked) Icons.Default.EmojiEvents else Icons.Default.Lock,
                contentDescription = null,
                tint = if (achievement.unlocked) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Achievement Info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = achievement.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (achievement.unlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = achievement.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            // Progress Bar (if not unlocked)
            if (!achievement.unlocked && achievement.maxProgress > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { achievement.progress.toFloat() / achievement.maxProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = PrimaryAmber,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Text(
                    text = "${achievement.progress}/${achievement.maxProgress}",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Points
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (achievement.unlocked) Color(0xFFFFD700).copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
        ) {
            Text(
                text = "+${achievement.points}",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (achievement.unlocked) Color(0xFFFFA000) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
