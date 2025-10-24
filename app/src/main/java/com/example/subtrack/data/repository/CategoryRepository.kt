package com.example.subtrack.data.repository

import com.example.subtrack.data.dao.CategoryDao
import com.example.subtrack.data.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }

    suspend fun getCategoryByName(name: String): Category? {
        return categoryDao.getCategoryByName(name)
    }

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun insertDefaultCategories() {
        val defaultCategories = listOf(
            Category("Entretenimiento", "#FF6B6B", "🎬"),
            Category("Productividad", "#4ECDC4", "💼"),
            Category("Almacenamiento", "#45B7D1", "☁️"),
            Category("Noticias", "#96CEB4", "📰"),
            Category("Otros", "#FFEAA7", "📦")
        categoryDao.insertCategories(defaultCategories)
    }
}
