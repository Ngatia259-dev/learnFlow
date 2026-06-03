package com.learnflow.lms.data.local.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.learnflow.lms.data.local.dao.AchievementDao
import com.learnflow.lms.data.local.dao.CourseDao
import com.learnflow.lms.data.local.dao.LessonDao
import com.learnflow.lms.data.local.dao.QuestionDao
import com.learnflow.lms.data.local.dao.QuizDao
import com.learnflow.lms.data.local.dao.UserDao
import com.learnflow.lms.util.MockData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseCallback(
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope.launch(Dispatchers.IO) {
            // Database will be populated here if needed
        }
    }
}
