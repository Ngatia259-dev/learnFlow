package com.learnflow.lms.presentation.screens.quiz

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun QuizScreen(
    quizId: String,
    onNavigateBack: () -> Unit
) {
    val quiz = MockData.quizzes.find { it.id == quizId }
    val questions = MockData.questions.filter { it.quizId == quizId }

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var answeredQuestions by remember { mutableIntStateOf(0) }
    var showResults by remember { mutableStateOf(false) }

    val currentQuestion = questions.getOrNull(currentQuestionIndex)
    val totalQuestions = questions.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(quiz?.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        if (showResults) {
            // Results Screen
            QuizResultsScreen(
                score = score,
                totalQuestions = totalQuestions,
                quiz = quiz,
                onNavigateBack = onNavigateBack
            )
        } else {
            // Quiz in Progress
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Progress Bar
                QuizProgressBar(
                    currentQuestion = currentQuestionIndex + 1,
                    totalQuestions = totalQuestions
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Timer
                QuizTimer(
                    timeLimit = quiz?.timeLimit ?: 10
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Question Card
                currentQuestion?.let { question ->
                    QuestionCard(
                        question = question.questionText,
                        options = question.options.split("\n"),
                        selectedAnswer = selectedAnswer,
                        isAnswerSubmitted = isAnswerSubmitted,
                        correctAnswer = question.correctAnswer,
                        onAnswerSelected = { answer ->
                            if (!isAnswerSubmitted) {
                                selectedAnswer = answer
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Explanation (if answer submitted)
                    if (isAnswerSubmitted && selectedAnswer != null) {
                        ExplanationCard(
                            explanation = question.explanation,
                            isCorrect = selectedAnswer == question.correctAnswer
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (!isAnswerSubmitted) {
                            // Submit Button
                            Button(
                                onClick = {
                                    isAnswerSubmitted = true
                                    answeredQuestions++
                                    if (selectedAnswer == currentQuestion?.correctAnswer) {
                                        score++
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PrimaryAmber
                                ),
                                shape = RoundedCornerShape(16.dp),
                                enabled = selectedAnswer != null
                            ) {
                                Text(
                                    text = "Submit Answer",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        } else {
                            // Next Button
                            Button(
                                onClick = {
                                    if (currentQuestionIndex < totalQuestions - 1) {
                                        currentQuestionIndex++
                                        selectedAnswer = null
                                        isAnswerSubmitted = false
                                    } else {
                                        showResults = true
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PrimaryAmber
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(
                                    text = if (currentQuestionIndex < totalQuestions - 1) "Next Question" else "See Results",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuizProgressBar(
    currentQuestion: Int,
    totalQuestions: Int
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Question $currentQuestion of $totalQuestions",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${((currentQuestion.toFloat() / totalQuestions) * 100).toInt()}%",
                fontSize = 14.sp,
                color = PrimaryAmber,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { currentQuestion.toFloat() / totalQuestions },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = PrimaryAmber,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun QuizTimer(
    timeLimit: Int
) {
    var timeRemaining by remember { mutableIntStateOf(timeLimit * 60) }

    LaunchedEffect(Unit) {
        while (timeRemaining > 0) {
            kotlinx.coroutines.delay(1000L)
            timeRemaining--
        }
    }

    val minutes = timeRemaining / 60
    val seconds = timeRemaining % 60

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (timeRemaining < 60) Color(0xFFF44336).copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = null,
                tint = if (timeRemaining < 60) Color(0xFFF44336) else PrimaryAmber,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (timeRemaining < 60) Color(0xFFF44336) else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun QuestionCard(
    question: String,
    options: List<String>,
    selectedAnswer: String?,
    isAnswerSubmitted: Boolean,
    correctAnswer: String,
    onAnswerSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Question Text
            Text(
                text = question,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 26.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Options
            options.forEach { option ->
                val optionLetter = option.substringBefore(")").trim()
                val optionText = option.substringAfter(")").trim()

                val isSelected = selectedAnswer == optionLetter
                val isCorrect = optionLetter == correctAnswer

                val backgroundColor = when {
                    isAnswerSubmitted && isSelected && isCorrect -> Color(0xFF4CAF50)
                    isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFF44336)
                    isAnswerSubmitted && isCorrect -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                    isSelected -> PrimaryAmber.copy(alpha = 0.1f)
                    else -> MaterialTheme.colorScheme.surface
                }

                val borderColor = when {
                    isAnswerSubmitted && isSelected && isCorrect -> Color(0xFF4CAF50)
                    isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFF44336)
                    isAnswerSubmitted && isCorrect -> Color(0xFF4CAF50)
                    isSelected -> PrimaryAmber
                    else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(backgroundColor)
                        .border(
                            width = 2.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onAnswerSelected(optionLetter) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Option Letter
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isAnswerSubmitted && isSelected && isCorrect -> Color(0xFF4CAF50)
                                    isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFF44336)
                                    isAnswerSubmitted && isCorrect -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                                    isSelected -> PrimaryAmber
                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isAnswerSubmitted && isSelected && isCorrect) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        } else if (isAnswerSubmitted && isSelected && !isCorrect) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        } else {
                            Text(
                                text = optionLetter,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Option Text
                    Text(
                        text = optionText,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun ExplanationCard(
    explanation: String,
    isCorrect: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) Color(0xFF4CAF50).copy(alpha = 0.1f) else Color(0xFFF44336).copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                contentDescription = null,
                tint = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = if (isCorrect) "Correct!" else "Incorrect",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = explanation,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun QuizResultsScreen(
    score: Int,
    totalQuestions: Int,
    quiz: com.learnflow.lms.data.local.entity.QuizEntity?,
    onNavigateBack: () -> Unit
) {
    val percentage = if (totalQuestions > 0) (score.toFloat() / totalQuestions * 100).toInt() else 0
    val passed = percentage >= (quiz?.passingScore ?: 70)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Result Icon
        Icon(
            imageVector = if (passed) Icons.Default.CheckCircle else Icons.Default.Cancel,
            contentDescription = null,
            tint = if (passed) Color(0xFF4CAF50) else Color(0xFFF44336),
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Result Title
        Text(
            text = if (passed) "Congratulations!" else "Keep Practicing!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (passed) "You passed the quiz" else "You didn't pass this time",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Score Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            colors = CardDefaults.cardColors(
                containerColor = if (passed) Color(0xFF4CAF50).copy(alpha = 0.1f) else Color(0xFFF44336).copy(alpha = 0.1f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your Score",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$percentage%",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (passed) Color(0xFF4CAF50) else Color(0xFFF44336)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$score out of $totalQuestions correct",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ResultStat(
                label = "Correct",
                value = "$score",
                color = Color(0xFF4CAF50)
            )
            ResultStat(
                label = "Incorrect",
                value = "${totalQuestions - score}",
                color = Color(0xFFF44336)
            )
            ResultStat(
                label = "Passing",
                value = "${quiz?.passingScore ?: 70}%",
                color = PrimaryAmber
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { /* TODO: Retry quiz */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryAmber
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Try Again",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            OutlinedButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Back to Course",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun ResultStat(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}
