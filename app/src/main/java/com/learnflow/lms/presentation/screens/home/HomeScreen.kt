package com.learnflow.lms.presentation.screens.home

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnflow.lms.presentation.theme.DarkBrown
import com.learnflow.lms.presentation.theme.PrimaryAmber
import com.learnflow.lms.util.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCourseCatalog: () -> Unit,
    onNavigateToCourseDetail: (String) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAchievements: () -> Unit
) {
    val currentUser = MockData.users.first { it.id == "user1" }
    val enrolledCourses = MockData.courses.filter { it.isEnrolled }
    val availableCourses = MockData.courses.filter { !it.isEnrolled }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Hello, ${currentUser.name.split(" ")[0]}!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Level ${currentUser.level} • ${currentUser.points} points",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Stats Card
            item {
                StatsCard(
                    streak = currentUser.streak,
                    coursesEnrolled = currentUser.coursesEnrolled,
                    coursesCompleted = currentUser.coursesCompleted
                )
            }

            // Continue Learning Section
            item {
                SectionHeader(
                    title = "Continue Learning",
                    onViewAll = null
                )
            }

            items(enrolledCourses.filter { !it.isCompleted }) { course ->
                CourseCard(
                    course = course,
                    onClick = { onNavigateToCourseDetail(course.id) }
                )
            }

            // Achievements Preview
            item {
                AchievementsPreviewCard(
                    onClick = onNavigateToAchievements
                )
            }

            // Explore Courses Section
            item {
                SectionHeader(
                    title = "Explore Courses",
                    onViewAll = onNavigateToCourseCatalog
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(availableCourses.take(5)) { course ->
                        CourseCardHorizontal(
                            course = course,
                            onClick = { onNavigateToCourseDetail(course.id) }
                        )
                    }
                }
            }

            // Categories Section
            item {
                SectionHeader(
                    title = "Categories",
                    onViewAll = null
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(MockData.courses.map { it.category }.distinct()) { category ->
                        CategoryChip(category = category)
                    }
                }
            }

            // Bottom Spacing
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun StatsCard(
    streak: Int,
    coursesEnrolled: Int,
    coursesCompleted: Int
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
                    Brush.horizontalGradient(
                        colors = listOf(DarkBrown, PrimaryAmber)
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = Icons.Default.LocalFireDepartment,
                    value = "$streak",
                    label = "Day Streak",
                    color = Color(0xFFFF6B35)
                )
                StatItem(
                    icon = Icons.Default.MenuBook,
                    value = "$coursesEnrolled",
                    label = "Enrolled",
                    color = Color(0xFF4CAF50)
                )
                StatItem(
                    icon = Icons.Default.CheckCircle,
                    value = "$coursesCompleted",
                    label = "Completed",
                    color = Color(0xFF2196F3)
                )
            }
        }
    }
}

@Composable
fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun SectionHeader(
    title: String,
    onViewAll: (() -> Unit)?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        onViewAll?.let {
            TextButton(onClick = it) {
                Text(
                    text = "View All",
                    color = PrimaryAmber
                )
            }
        }
    }
}

@Composable
fun CourseCard(
    course: com.learnflow.lms.data.local.entity.CourseEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Course Icon
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PrimaryAmber, DarkBrown)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Course Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = course.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${course.completedLessons}/${course.totalLessons} lessons",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Progress Bar
                LinearProgressIndicator(
                    progress = {
                        if (course.totalLessons > 0) {
                            course.completedLessons.toFloat() / course.totalLessons
                        } else {
                            0f
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = PrimaryAmber,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Arrow
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Go to course",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun CourseCardHorizontal(
    course: com.learnflow.lms.data.local.entity.CourseEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable(onClick = onClick),
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
            // Course Icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PrimaryAmber, DarkBrown)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = course.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = course.category,
                fontSize = 12.sp,
                color = PrimaryAmber
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${course.rating}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${course.enrolledCount} students",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun AchievementsPreviewCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryAmber.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = PrimaryAmber,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Your Achievements",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "4 of 10 unlocked",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = PrimaryAmber
            )
        }
    }
}

@Composable
fun CategoryChip(
    category: String
) {
    val categoryColors = mapOf(
        "Mathematics" to Color(0xFF4CAF50),
        "Literature" to Color(0xFF2196F3),
        "Science" to Color(0xFFFF9800),
        "History" to Color(0xFF9C27B0),
        "Technology" to Color(0xFF00BCD4),
        "Arts" to Color(0xFFE91E63),
        "Music" to Color(0xFF3F51B5),
        "Health" to Color(0xFF009688),
        "Business" to Color(0xFF795548),
        "Psychology" to Color(0xFF607D8B)
    )

    val color = categoryColors[category] ?: PrimaryAmber

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = category,
                fontSize = 14.sp,
                color = color,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
