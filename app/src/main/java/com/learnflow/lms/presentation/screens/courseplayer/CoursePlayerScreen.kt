package com.learnflow.lms.presentation.screens.courseplayer

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnflow.lms.presentation.theme.DarkBrown
import com.learnflow.lms.presentation.theme.PrimaryAmber
import com.learnflow.lms.util.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursePlayerScreen(
    courseId: String,
    lessonId: String,
    onNavigateBack: () -> Unit
) {
    val lesson = MockData.lessons.find { it.id == lessonId }
    val course = MockData.courses.find { it.id == courseId }
    val lessons = MockData.lessons.filter { it.courseId == courseId }

    var isPlaying by remember { mutableStateOf(false) }
    var currentTime by remember { mutableIntStateOf(0) }
    var isBookmarked by remember { mutableStateOf(false) }
    var showNotes by remember { mutableStateOf(false) }
    var notes by remember { mutableStateOf("") }

    val totalDuration = 1200 // 20 minutes in seconds
    val progress = if (totalDuration > 0) currentTime.toFloat() / totalDuration else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(lesson?.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { isBookmarked = !isBookmarked }) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) PrimaryAmber else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = { showNotes = !showNotes }) {
                        Icon(
                            imageVector = Icons.Default.Note,
                            contentDescription = "Notes"
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
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Video Player Area
            item {
                VideoPlayerArea(
                    isPlaying = isPlaying,
                    onPlayPause = { isPlaying = !isPlaying },
                    progress = progress
                )
            }

            // Playback Controls
            item {
                PlaybackControls(
                    currentTime = currentTime,
                    totalTime = totalDuration,
                    onSeek = { currentTime = it },
                    onPlayPause = { isPlaying = !isPlaying },
                    isPlaying = isPlaying
                )
            }

            // Lesson Info
            item {
                LessonInfo(
                    title = lesson?.title ?: "",
                    description = lesson?.description ?: "",
                    duration = lesson?.duration ?: "",
                    type = lesson?.type ?: ""
                )
            }

            // Progress
            item {
                ProgressSection(
                    completed = lesson?.isCompleted ?: false,
                    onComplete = { /* TODO: Mark complete */ }
                )
            }

            // Notes Section
            if (showNotes) {
                item {
                    NotesSection(
                        notes = notes,
                        onNotesChange = { notes = it }
                    )
                }
            }

            // Related Lessons
            item {
                Text(
                    text = "Next Lessons",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }

            items(lessons.filter { it.id != lessonId }) { relatedLesson ->
                RelatedLessonItem(
                    lesson = relatedLesson,
                    isCurrentLesson = relatedLesson.id == lessonId
                )
            }
        }

        // Bottom Action Button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { /* TODO: Mark complete and go to next */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryAmber
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (lesson?.isCompleted == true) "Completed" else "Mark as Complete",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun VideoPlayerArea(
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    progress: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkBrown, PrimaryAmber)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Play Button
        IconButton(
            onClick = onPlayPause,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying) "Pause" else "Play",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }

        // Progress Bar at Bottom
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .align(Alignment.BottomCenter),
            color = PrimaryAmber,
            trackColor = Color.White.copy(alpha = 0.3f)
        )
    }
}

@Composable
fun PlaybackControls(
    currentTime: Int,
    totalTime: Int,
    onSeek: (Int) -> Unit,
    onPlayPause: () -> Unit,
    isPlaying: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Time Display
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(currentTime),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = formatTime(totalTime),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Controls Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rewind
            IconButton(onClick = { onSeek((currentTime - 10).coerceAtLeast(0)) }) {
                Icon(
                    imageVector = Icons.Default.Replay10,
                    contentDescription = "Rewind 10s",
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Play/Pause
            IconButton(
                onClick = onPlayPause,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(PrimaryAmber)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Forward
            IconButton(onClick = { onSeek((currentTime + 10).coerceAtMost(totalTime)) }) {
                Icon(
                    imageVector = Icons.Default.Forward10,
                    contentDescription = "Forward 10s",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun LessonInfo(
    title: String,
    description: String,
    duration: String,
    type: String
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = PrimaryAmber
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = duration,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (type) {
                        "video" -> Icons.Default.PlayArrow
                        "quiz" -> Icons.Default.Quiz
                        "document" -> Icons.Default.Description
                        else -> Icons.Default.Lesson
                    },
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = PrimaryAmber
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = type.replaceFirstChar { it.uppercase() },
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            lineHeight = 22.sp
        )
    }
}

@Composable
fun ProgressSection(
    completed: Boolean,
    onComplete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (completed) Color(0xFF4CAF50).copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (completed) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (completed) Color(0xFF4CAF50) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (completed) "Completed" else "Mark as Complete",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (completed) "You've finished this lesson" else "Tap to mark this lesson as complete",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun NotesSection(
    notes: String,
    onNotesChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "My Notes",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = onNotesChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            placeholder = { Text("Take notes for this lesson...") },
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun RelatedLessonItem(
    lesson: com.learnflow.lms.data.local.entity.LessonEntity,
    isCurrentLesson: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrentLesson) PrimaryAmber.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Lesson Type Icon
            Box(
                modifier = Modifier
                    .size(36.dp)
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
                        else -> Icons.Default.Lesson
                    },
                    contentDescription = null,
                    tint = if (lesson.isCompleted) Color.White else PrimaryAmber,
                    modifier = Modifier.size(18.dp)
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
                    fontWeight = FontWeight.Medium,
                    color = if (isCurrentLesson) PrimaryAmber else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = lesson.duration,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            if (lesson.isCompleted) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", mins, secs)
}
