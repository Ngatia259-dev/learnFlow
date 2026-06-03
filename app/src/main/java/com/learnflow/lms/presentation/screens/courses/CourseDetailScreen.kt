package com.learnflow.lms.presentation.screens.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnflow.lms.presentation.theme.DarkBrown
import com.learnflow.lms.presentation.theme.PrimaryAmber
import com.learnflow.lms.util.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    courseId: String,
    onNavigateToCoursePlayer: (String) -> Unit,
    onNavigateToQuiz: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val course = MockData.courses.find { it.id == courseId }
    val lessons = MockData.lessons.filter { it.courseId == courseId }
    val quizzes = MockData.quizzes.filter { it.courseId == courseId }

    var isEnrolled by remember { mutableStateOf(course?.isEnrolled ?: false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Share */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )
                    }
                    IconButton(onClick = { /* TODO: Bookmark */ }) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Course Header
            item {
                CourseHeader(
                    course = course,
                    isEnrolled = isEnrolled
                )
            }

            // Course Stats
            item {
                CourseStats(
                    duration = course?.duration ?: "",
                    lessons = lessons.size,
                    students = course?.enrolledCount ?: 0,
                    rating = course?.rating ?: 0f
                )
            }

            // Course Description
            item {
                CourseDescription(
                    description = course?.description ?: ""
                )
            }

            // Curriculum Section
            item {
                SectionTitle(title = "Curriculum")
            }

            items(lessons) { lesson ->
                LessonItem(
                    lesson = lesson,
                    onClick = {
                        if (isEnrolled) {
                            onNavigateToCoursePlayer(lesson.id)
                        }
                    }
                )
            }

            // Quizzes Section
            if (quizzes.isNotEmpty()) {
                item {
                    SectionTitle(title = "Quizzes")
                }

                items(quizzes) { quiz ->
                    QuizItem(
                        quiz = quiz,
                        onClick = {
                            if (isEnrolled) {
                                onNavigateToQuiz(quiz.id)
                            }
                        }
                    )
                }
            }

            // Instructor Section
            item {
                SectionTitle(title = "Instructor")
            }

            item {
                InstructorCard(
                    name = course?.instructorName ?: "",
                    title = "Professor"
                )
            }
        }

        // Bottom Action Button
        if (!isEnrolled) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = { isEnrolled = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryAmber
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Enroll Now - Free",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun CourseHeader(
    course: com.learnflow.lms.data.local.entity.CourseEntity?,
    isEnrolled: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkBrown, PrimaryAmber)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = course?.title ?: "",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = course?.category ?: "",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun CourseStats(
    duration: String,
    lessons: Int,
    students: Int,
    rating: Float
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
                icon = Icons.Default.AccessTime,
                value = duration,
                label = "Duration"
            )
            StatItem(
                icon = Icons.Default.MenuBook,
                value = "$lessons",
                label = "Lessons"
            )
            StatItem(
                icon = Icons.Default.People,
                value = "$students",
                label = "Students"
            )
            StatItem(
                icon = Icons.Default.Star,
                value = "$rating",
                label = "Rating"
            )
        }
    }
}

@Composable
fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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
            fontSize = 16.sp,
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
fun CourseDescription(
    description: String
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "About this course",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            lineHeight = 22.sp
        )
    }
}

@Composable
fun SectionTitle(
    title: String
) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun LessonItem(
    lesson: com.learnflow.lms.data.local.entity.LessonEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (lesson.isCompleted) Color(0xFF4CAF50).copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Lesson Type Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (lesson.isCompleted) Color(0xFF4CAF50)
                        else PrimaryAmber.copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (lesson.type) {
                        "video" -> Icons.Default.PlayArrow
                        "quiz" -> Icons.Default.Quiz
                        "document" -> Icons.Default.Description
                        else -> Icons.Default.Note
                    },
                    contentDescription = null,
                    tint = if (lesson.isCompleted) Color.White else PrimaryAmber,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Lesson Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = lesson.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = lesson.duration,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Completion Icon
            if (lesson.isCompleted) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(20.dp)
                )
            } else if (lesson.isLocked) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Start",
                    tint = PrimaryAmber,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun QuizItem(
    quiz: com.learnflow.lms.data.local.entity.QuizEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (quiz.isCompleted) Color(0xFF2196F3).copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Quiz Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (quiz.isCompleted) Color(0xFF2196F3)
                        else PrimaryAmber.copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Quiz,
                    contentDescription = null,
                    tint = if (quiz.isCompleted) Color.White else PrimaryAmber,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Quiz Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = quiz.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${quiz.questionCount} questions • ${quiz.timeLimit} min",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Score
            if (quiz.isCompleted) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF2196F3).copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "${quiz.bestScore}%",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2196F3)
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Start Quiz",
                    tint = PrimaryAmber,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun InstructorCard(
    name: String,
    title: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
            // Avatar
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PrimaryAmber, DarkBrown)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = title,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}
