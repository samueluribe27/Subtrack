package com.example.subtrack.data.repository

import com.example.subtrack.data.dao.CategoryDao
import com.example.subtrack.data.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(
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

    suspend fun insertCategories(categories: List<Category>) {
        categoryDao.insertCategories(categories)
    }
}
