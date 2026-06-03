package com.learnflow.lms.util

import com.learnflow.lms.data.local.entity.AchievementEntity
import com.learnflow.lms.data.local.entity.CourseEntity
import com.learnflow.lms.data.local.entity.LessonEntity
import com.learnflow.lms.data.local.entity.QuestionEntity
import com.learnflow.lms.data.local.entity.QuizEntity
import com.learnflow.lms.data.local.entity.UserEntity

object MockData {

    val users = listOf(
        UserEntity(
            id = "user1",
            name = "Alex Johnson",
            email = "alex@example.com",
            avatarUrl = "",
            role = "student",
            points = 2450,
            level = 12,
            streak = 15,
            coursesEnrolled = 5,
            coursesCompleted = 3
        ),
        UserEntity(
            id = "user2",
            name = "Sarah Williams",
            email = "sarah@example.com",
            avatarUrl = "",
            role = "student",
            points = 3200,
            level = 15,
            streak = 22,
            coursesEnrolled = 6,
            coursesCompleted = 5
        ),
        UserEntity(
            id = "user3",
            name = "Michael Brown",
            email = "michael@example.com",
            avatarUrl = "",
            role = "student",
            points = 1800,
            level = 9,
            streak = 7,
            coursesEnrolled = 4,
            coursesCompleted = 2
        ),
        UserEntity(
            id = "instructor1",
            name = "Dr. Emily Chen",
            email = "emily@example.com",
            avatarUrl = "",
            role = "instructor",
            points = 5000,
            level = 20
        ),
        UserEntity(
            id = "admin1",
            name = "Admin User",
            email = "admin@example.com",
            avatarUrl = "",
            role = "admin",
            points = 0,
            level = 1
        )
    )

    val courses = listOf(
        CourseEntity(
            id = "course1",
            title = "Introduction to Mathematics",
            description = "Master the fundamentals of mathematics including algebra, geometry, and basic calculus. Perfect for beginners looking to build a strong mathematical foundation.",
            instructorId = "instructor1",
            instructorName = "Dr. Emily Chen",
            thumbnailUrl = "",
            category = "Mathematics",
            duration = "8 weeks",
            totalLessons = 24,
            completedLessons = 12,
            rating = 4.8f,
            enrolledCount = 1250,
            price = "Free",
            isEnrolled = true,
            difficulty = "Beginner",
            language = "English"
        ),
        CourseEntity(
            id = "course2",
            title = "English Literature Basics",
            description = "Explore classic and modern literature, develop critical reading skills, and learn to analyze texts effectively.",
            instructorId = "instructor1",
            instructorName = "Dr. Emily Chen",
            thumbnailUrl = "",
            category = "Literature",
            duration = "6 weeks",
            totalLessons = 18,
            completedLessons = 18,
            rating = 4.9f,
            enrolledCount = 890,
            price = "Free",
            isEnrolled = true,
            isCompleted = true,
            difficulty = "Beginner",
            language = "English"
        ),
        CourseEntity(
            id = "course3",
            title = "Science Fundamentals",
            description = "Discover the wonders of physics, chemistry, and biology through interactive lessons and experiments.",
            instructorId = "instructor1",
            instructorName = "Dr. Emily Chen",
            thumbnailUrl = "",
            category = "Science",
            duration = "10 weeks",
            totalLessons = 30,
            completedLessons = 8,
            rating = 4.7f,
            enrolledCount = 1100,
            price = "Free",
            isEnrolled = true,
            difficulty = "Intermediate",
            language = "English"
        ),
        CourseEntity(
            id = "course4",
            title = "History of World Civilizations",
            description = "Journey through time and explore the rise and fall of great civilizations from ancient Mesopotamia to modern day.",
            instructorId = "instructor1",
            instructorName = "Dr. Emily Chen",
            thumbnailUrl = "",
            category = "History",
            duration = "8 weeks",
            totalLessons = 24,
            completedLessons = 0,
            rating = 4.6f,
            enrolledCount = 750,
            price = "Free",
            difficulty = "Intermediate",
            language = "English"
        ),
        CourseEntity(
            id = "course5",
            title = "Computer Science 101",
            description = "Learn programming fundamentals, algorithms, and computational thinking. No prior experience required.",
            instructorId = "instructor1",
            instructorName = "Dr. Emily Chen",
            thumbnailUrl = "",
            category = "Technology",
            duration = "12 weeks",
            totalLessons = 36,
            completedLessons = 0,
            rating = 4.9f,
            enrolledCount = 2100,
            price = "Free",
            difficulty = "Beginner",
            language = "English"
        ),
        CourseEntity(
            id = "course6",
            title = "Art and Design Principles",
            description = "Unleash your creativity with fundamental art techniques, color theory, and design principles.",
            instructorId = "instructor1",
            instructorName = "Dr. Emily Chen",
            thumbnailUrl = "",
            category = "Arts",
            duration = "6 weeks",
            totalLessons = 18,
            completedLessons = 0,
            rating = 4.5f,
            enrolledCount = 650,
            price = "Free",
            difficulty = "Beginner",
            language = "English"
        ),
        CourseEntity(
            id = "course7",
            title = "Music Theory Basics",
            description = "Understand scales, chords, rhythm, and melody. Perfect for aspiring musicians and music lovers.",
            instructorId = "instructor1",
            instructorName = "Dr. Emily Chen",
            thumbnailUrl = "",
            category = "Music",
            duration = "8 weeks",
            totalLessons = 24,
            completedLessons = 0,
            rating = 4.7f,
            enrolledCount = 480,
            price = "Free",
            difficulty = "Beginner",
            language = "English"
        ),
        CourseEntity(
            id = "course8",
            title = "Physical Education",
            description = "Learn about fitness, nutrition, and healthy lifestyle habits through interactive lessons.",
            instructorId = "instructor1",
            instructorName = "Dr. Emily Chen",
            thumbnailUrl = "",
            category = "Health",
            duration = "4 weeks",
            totalLessons = 12,
            completedLessons = 0,
            rating = 4.4f,
            enrolledCount = 920,
            price = "Free",
            difficulty = "Beginner",
            language = "English"
        ),
        CourseEntity(
            id = "course9",
            title = "Environmental Science",
            description = "Explore ecosystems, climate change, and sustainability. Learn how to protect our planet.",
            instructorId = "instructor1",
            instructorName = "Dr. Emily Chen",
            thumbnailUrl = "",
            category = "Science",
            duration = "8 weeks",
            totalLessons = 24,
            completedLessons = 0,
            rating = 4.6f,
            enrolledCount = 580,
            price = "Free",
            difficulty = "Intermediate",
            language = "English"
        ),
        CourseEntity(
            id = "course10",
            title = "Business Studies",
            description = "Introduction to business concepts, entrepreneurship, marketing, and financial literacy.",
            instructorId = "instructor1",
            instructorName = "Dr. Emily Chen",
            thumbnailUrl = "",
            category = "Business",
            duration = "10 weeks",
            totalLessons = 30,
            completedLessons = 0,
            rating = 4.5f,
            enrolledCount = 720,
            price = "Free",
            difficulty = "Intermediate",
            language = "English"
        ),
        CourseEntity(
            id = "course11",
            title = "Psychology Introduction",
            description = "Discover the human mind through cognitive psychology, behavioral science, and mental health basics.",
            instructorId = "instructor1",
            instructorName = "Dr. Emily Chen",
            thumbnailUrl = "",
            category = "Psychology",
            duration = "8 weeks",
            totalLessons = 24,
            completedLessons = 0,
            rating = 4.8f,
            enrolledCount = 830,
            price = "Free",
            difficulty = "Beginner",
            language = "English"
        ),
        CourseEntity(
            id = "course12",
            title = "Creative Writing Workshop",
            description = "Develop your writing skills through fiction, poetry, and creative non-fiction exercises.",
            instructorId = "instructor1",
            instructorName = "Dr. Emily Chen",
            thumbnailUrl = "",
            category = "Literature",
            duration = "6 weeks",
            totalLessons = 18,
            completedLessons = 0,
            rating = 4.7f,
            enrolledCount = 410,
            price = "Free",
            difficulty = "Beginner",
            language = "English"
        )
    )

    val lessons = listOf(
        // Course 1 Lessons
        LessonEntity(id = "lesson1_1", courseId = "course1", title = "What is Mathematics?", description = "Introduction to mathematical thinking", type = "video", contentUrl = "", duration = "15 min", order = 1, isCompleted = true),
        LessonEntity(id = "lesson1_2", courseId = "course1", title = "Basic Algebra", description = "Variables and equations", type = "video", contentUrl = "", duration = "20 min", order = 2, isCompleted = true),
        LessonEntity(id = "lesson1_3", courseId = "course1", title = "Geometry Fundamentals", description = "Shapes and measurements", type = "video", contentUrl = "", duration = "25 min", order = 3, isCompleted = true),
        LessonEntity(id = "lesson1_4", courseId = "course1", title = "Practice Problems", description = "Apply what you learned", type = "quiz", contentUrl = "", duration = "30 min", order = 4, isCompleted = false),
        LessonEntity(id = "lesson1_5", courseId = "course1", title = "Advanced Algebra", description = "Quadratic equations", type = "video", contentUrl = "", duration = "20 min", order = 5, isCompleted = false),
        LessonEntity(id = "lesson1_6", courseId = "course1", title = "Course Notes", description = "Downloadable materials", type = "document", contentUrl = "", duration = "10 min", order = 6, isCompleted = false),

        // Course 2 Lessons
        LessonEntity(id = "lesson2_1", courseId = "course2", title = "Introduction to Literature", description = "What makes literature special", type = "video", contentUrl = "", duration = "15 min", order = 1, isCompleted = true),
        LessonEntity(id = "lesson2_2", courseId = "course2", title = "Reading Comprehension", description = "Techniques for understanding texts", type = "video", contentUrl = "", duration = "20 min", order = 2, isCompleted = true),
        LessonEntity(id = "lesson2_3", courseId = "course2", title = "Analyzing Poetry", description = "Understanding poetic devices", type = "video", contentUrl = "", duration = "25 min", order = 3, isCompleted = true),

        // Course 3 Lessons
        LessonEntity(id = "lesson3_1", courseId = "course3", title = "The Scientific Method", description = "How scientists think", type = "video", contentUrl = "", duration = "15 min", order = 1, isCompleted = true),
        LessonEntity(id = "lesson3_2", courseId = "course3", title = "Physics Basics", description = "Forces and motion", type = "video", contentUrl = "", duration = "25 min", order = 2, isCompleted = true),
        LessonEntity(id = "lesson3_3", courseId = "course3", title = "Chemistry 101", description = "Elements and compounds", type = "video", contentUrl = "", duration = "20 min", order = 3, isCompleted = false)
    )

    val quizzes = listOf(
        QuizEntity(
            id = "quiz1_1",
            courseId = "course1",
            title = "Algebra Basics Quiz",
            description = "Test your understanding of basic algebra",
            questionCount = 5,
            timeLimit = 10,
            passingScore = 70,
            attempts = 1,
            bestScore = 85,
            isCompleted = true
        ),
        QuizEntity(
            id = "quiz1_2",
            courseId = "course1",
            title = "Geometry Fundamentals Quiz",
            description = "Check your geometry knowledge",
            questionCount = 5,
            timeLimit = 15,
            passingScore = 70,
            attempts = 0,
            bestScore = 0,
            isCompleted = false
        ),
        QuizEntity(
            id = "quiz2_1",
            courseId = "course2",
            title = "Literature Appreciation Quiz",
            description = "Test your literature knowledge",
            questionCount = 5,
            timeLimit = 10,
            passingScore = 70,
            attempts = 1,
            bestScore = 90,
            isCompleted = true
        ),
        QuizEntity(
            id = "quiz3_1",
            courseId = "course3",
            title = "Science Basics Quiz",
            description = "Assess your science understanding",
            questionCount = 5,
            timeLimit = 15,
            passingScore = 70,
            attempts = 1,
            bestScore = 75,
            isCompleted = true
        )
    )

    val questions = listOf(
        // Quiz 1 Questions
        QuestionEntity(
            id = "q1_1",
            quizId = "quiz1_1",
            questionText = "What is the value of x in the equation 2x + 5 = 15?",
            questionType = "multiple_choice",
            options = "A) 3\nB) 5\nC) 7\nD) 10",
            correctAnswer = "B",
            explanation = "2x + 5 = 15, so 2x = 10, therefore x = 5",
            order = 1
        ),
        QuestionEntity(
            id = "q1_2",
            quizId = "quiz1_1",
            questionText = "Simplify: 3(x + 2)",
            questionType = "multiple_choice",
            options = "A) 3x + 2\nB) 3x + 5\nC) 3x + 6\nD) x + 6",
            correctAnswer = "C",
            explanation = "3(x + 2) = 3x + 6 using distributive property",
            order = 2
        ),
        QuestionEntity(
            id = "q1_3",
            quizId = "quiz1_1",
            questionText = "What is a variable in algebra?",
            questionType = "multiple_choice",
            options = "A) A fixed number\nB) A letter representing an unknown value\nC) A mathematical operation\nD) A type of equation",
            correctAnswer = "B",
            explanation = "Variables are letters or symbols that represent unknown values",
            order = 3
        ),
        QuestionEntity(
            id = "q1_4",
            quizId = "quiz1_1",
            questionText = "Solve for y: y - 7 = 12",
            questionType = "multiple_choice",
            options = "A) 5\nB) 12\nC) 19\nD) 84",
            correctAnswer = "C",
            explanation = "y - 7 = 12, so y = 12 + 7 = 19",
            order = 4
        ),
        QuestionEntity(
            id = "q1_5",
            quizId = "quiz1_1",
            questionText = "What does the expression 4² equal?",
            questionType = "multiple_choice",
            options = "A) 8\nB) 16\nC) 42\nD) 24",
            correctAnswer = "B",
            explanation = "4² means 4 × 4 = 16",
            order = 5
        ),

        // Quiz 3 Questions
        QuestionEntity(
            id = "q3_1",
            quizId = "quiz3_1",
            questionText = "What are the three states of matter?",
            questionType = "multiple_choice",
            options = "A) Hot, Cold, Warm\nB) Solid, Liquid, Gas\nC) Big, Small, Medium\nD) Fast, Slow, Medium",
            correctAnswer = "B",
            explanation = "The three primary states of matter are solid, liquid, and gas",
            order = 1
        ),
        QuestionEntity(
            id = "q3_2",
            quizId = "quiz3_1",
            questionText = "What is H2O commonly known as?",
            questionType = "multiple_choice",
            options = "A) Salt\nB) Sugar\nC) Water\nD) Oxygen",
            correctAnswer = "C",
            explanation = "H2O is the chemical formula for water",
            order = 2
        ),
        QuestionEntity(
            id = "q3_3",
            quizId = "quiz3_1",
            questionText = "What force keeps us on the ground?",
            questionType = "multiple_choice",
            options = "A) Magnetism\nB) Electricity\nC) Gravity\nD) Friction",
            correctAnswer = "C",
            explanation = "Gravity is the force that attracts objects toward the center of the Earth",
            order = 3
        )
    )

    val achievements = listOf(
        AchievementEntity(
            id = "ach1",
            title = "First Steps",
            description = "Enroll in your first course",
            iconUrl = "",
            category = "Getting Started",
            points = 50,
            unlocked = true,
            unlockedAt = System.currentTimeMillis() - 86400000 * 30,
            progress = 100,
            maxProgress = 100
        ),
        AchievementEntity(
            id = "ach2",
            title = "Quiz Master",
            description = "Score 100% on any quiz",
            iconUrl = "",
            category = "Achievement",
            points = 100,
            unlocked = true,
            unlockedAt = System.currentTimeMillis() - 86400000 * 15,
            progress = 100,
            maxProgress = 100
        ),
        AchievementEntity(
            id = "ach3",
            title = "On Fire",
            description = "Maintain a 7-day learning streak",
            iconUrl = "",
            category = "Streak",
            points = 150,
            unlocked = true,
            unlockedAt = System.currentTimeMillis() - 86400000 * 5,
            progress = 100,
            maxProgress = 7
        ),
        AchievementEntity(
            id = "ach4",
            title = "Course Completer",
            description = "Complete your first course",
            iconUrl = "",
            category = "Achievement",
            points = 200,
            unlocked = true,
            unlockedAt = System.currentTimeMillis() - 86400000 * 10,
            progress = 100,
            maxProgress = 100
        ),
        AchievementEntity(
            id = "ach5",
            title = "Discussion Champion",
            description = "Post 10 discussion comments",
            iconUrl = "",
            category = "Social",
            points = 75,
            unlocked = false,
            progress = 6,
            maxProgress = 10
        ),
        AchievementEntity(
            id = "ach6",
            title = "Knowledge Seeker",
            description = "Complete 50 lessons",
            iconUrl = "",
            category = "Progress",
            points = 300,
            unlocked = false,
            progress = 35,
            maxProgress = 50
        ),
        AchievementEntity(
            id = "ach7",
            title = "Perfect Score",
            description = "Get 100% on 5 quizzes",
            iconUrl = "",
            category = "Achievement",
            points = 250,
            unlocked = false,
            progress = 2,
            maxProgress = 5
        ),
        AchievementEntity(
            id = "ach8",
            title = "Social Butterfly",
            description = "Join 3 study groups",
            iconUrl = "",
            category = "Social",
            points = 100,
            unlocked = false,
            progress = 1,
            maxProgress = 3
        ),
        AchievementEntity(
            id = "ach9",
            title = "Weekend Warrior",
            description = "Study for 5 hours on weekends",
            iconUrl = "",
            category = "Streak",
            points = 125,
            unlocked = false,
            progress = 3,
            maxProgress = 5
        ),
        AchievementEntity(
            id = "ach10",
            title = "Early Bird",
            description = "Complete 10 lessons before their due date",
            iconUrl = "",
            category = "Progress",
            points = 175,
            unlocked = false,
            progress = 7,
            maxProgress = 10
        )
    )
}
