# LearnFlow LMS

A modern Learning Management System Android app built with Jetpack Compose, Room Database, and Clean Architecture.

## Features

- **Splash Screen** - Animated logo with gradient background
- **Authentication** - Login/Register with mock authentication
- **Dashboard** - Student, Instructor, and Admin views
- **Course Management** - Browse, enroll, and track courses
- **Course Player** - Video player with progress tracking
- **Quizzes** - Interactive quiz system with scoring
- **Achievements** - Gamification with badges and rewards
- **Profile** - User settings and statistics

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Local Storage**: Room Database
- **Dependency Injection**: Hilt
- **Navigation**: Compose Navigation

## Color Scheme

- Primary Amber: `#C67000` (R198, G112, B0)
- Dark Brown: `#2B1100` (R43, G17, B0)
- Off-White: `#FAFAFA`

## Getting Started

1. Open the project in Android Studio
2. Sync Gradle files
3. Run on an emulator or device (API 24+)

## Demo Credentials

- **Email**: Any email address
- **Password**: `password`

## Project Structure

```
app/src/main/java/com/learnflow/lms/
├── data/
│   ├── local/
│   │   ├── dao/          # Data Access Objects
│   │   ├── database/     # Room Database
│   │   └── entity/       # Database Entities
│   └── repository/       # Data Repositories
├── presentation/
│   ├── components/       # Reusable UI Components
│   ├── navigation/       # Navigation Graph
│   ├── screens/          # Screen Composables
│   └── theme/            # Theme Configuration
├── di/                   # Dependency Injection
└── util/                 # Utilities and Constants
```

## Mock Data

The app comes pre-populated with:
- 12 courses across different categories
- 15+ lessons with varied content types
- 4 quizzes with 15+ questions
- 10 achievements
- 5 user profiles

## License

This is a demo application for marketing purposes.
# learnFlow
