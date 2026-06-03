package com.learnflow.lms.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.learnflow.lms.presentation.screens.achievements.AchievementsScreen
import com.learnflow.lms.presentation.screens.auth.LoginScreen
import com.learnflow.lms.presentation.screens.auth.RegisterScreen
import com.learnflow.lms.presentation.screens.courseplayer.CoursePlayerScreen
import com.learnflow.lms.presentation.screens.courses.CourseCatalogScreen
import com.learnflow.lms.presentation.screens.courses.CourseDetailScreen
import com.learnflow.lms.presentation.screens.home.HomeScreen
import com.learnflow.lms.presentation.screens.profile.ProfileScreen
import com.learnflow.lms.presentation.screens.quiz.QuizScreen
import com.learnflow.lms.presentation.screens.splash.SplashScreen
import com.learnflow.lms.presentation.theme.PrimaryAmber

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object CourseCatalog : Screen("course_catalog")
    object CourseDetail : Screen("course_detail/{courseId}") {
        fun createRoute(courseId: String) = "course_detail/$courseId"
    }
    object CoursePlayer : Screen("course_player/{courseId}/{lessonId}") {
        fun createRoute(courseId: String, lessonId: String) = "course_player/$courseId/$lessonId"
    }
    object Quiz : Screen("quiz/{quizId}") {
        fun createRoute(quizId: String) = "quiz/$quizId"
    }
    object Profile : Screen("profile")
    object Achievements : Screen("achievements")
}

@Composable
fun LearnFlowNavigation(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val mainDestinations = listOf(
        Screen.Home.route,
        Screen.CourseCatalog.route,
        Screen.Achievements.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in mainDestinations) {
                LearnFlowBottomBar(
                    currentRoute = currentRoute,
                    onNavigateToRoute = { route ->
                        if (currentRoute != route) {
                            navController.navigate(route) {
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(paddingValues),
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            ) + fadeIn(animationSpec = tween(700))
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            ) + fadeOut(animationSpec = tween(700))
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            ) + fadeIn(animationSpec = tween(700))
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            ) + fadeOut(animationSpec = tween(700))
        }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToCourseCatalog = {
                    navController.navigate(Screen.CourseCatalog.route)
                },
                onNavigateToCourseDetail = { courseId ->
                    navController.navigate(Screen.CourseDetail.createRoute(courseId))
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onNavigateToAchievements = {
                    navController.navigate(Screen.Achievements.route)
                }
            )
        }

        composable(Screen.CourseCatalog.route) {
            CourseCatalogScreen(
                showBackButton = false,
                onNavigateToCourseDetail = { courseId ->
                    navController.navigate(Screen.CourseDetail.createRoute(courseId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.CourseDetail.route,
            arguments = listOf(
                navArgument("courseId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
            CourseDetailScreen(
                courseId = courseId,
                onNavigateToCoursePlayer = { lessonId ->
                    navController.navigate(Screen.CoursePlayer.createRoute(courseId, lessonId))
                },
                onNavigateToQuiz = { quizId ->
                    navController.navigate(Screen.Quiz.createRoute(quizId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.CoursePlayer.route,
            arguments = listOf(
                navArgument("courseId") { type = NavType.StringType },
                navArgument("lessonId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: ""
            CoursePlayerScreen(
                courseId = courseId,
                lessonId = lessonId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Quiz.route,
            arguments = listOf(
                navArgument("quizId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val quizId = backStackEntry.arguments?.getString("quizId") ?: ""
            QuizScreen(
                quizId = quizId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                showBackButton = false,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToAchievements = {
                    navController.navigate(Screen.Achievements.route)
                }
            )
        }

        composable(Screen.Achievements.route) {
            AchievementsScreen(
                showBackButton = false,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun LearnFlowBottomBar(
    currentRoute: String?,
    onNavigateToRoute: (String) -> Unit
) {
    val items = listOf(
        Triple(Screen.Home.route, Icons.Default.Home, "Home"),
        Triple(Screen.CourseCatalog.route, Icons.Default.Search, "Explore"),
        Triple(Screen.Achievements.route, Icons.Default.EmojiEvents, "Badges"),
        Triple(Screen.Profile.route, Icons.Default.Person, "Profile")
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEach { (route, icon, label) ->
            val selected = currentRoute == route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToRoute(route) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = PrimaryAmber,
                    indicatorColor = PrimaryAmber,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }
}
