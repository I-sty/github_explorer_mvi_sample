package com.i_sz_app.githubexplorer.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BuildRepositoryQueryUseCaseTest {

    private lateinit var buildRepositoryQueryUseCase: BuildRepositoryQueryUseCase

    @Before
    fun setUp() {
        buildRepositoryQueryUseCase = BuildRepositoryQueryUseCase()
    }

    @Test
    fun `invoke with blank language should return default query`() {
        // Arrange
        val language = ""

        // Act
        val result = buildRepositoryQueryUseCase(language)

        // Assert
        assertEquals("size:>0", result)
    }

    @Test
    fun `invoke with spaces only should return default query`() {
        // Arrange
        val language = "   "

        // Act
        val result = buildRepositoryQueryUseCase(language)

        // Assert
        assertEquals("size:>0", result)
    }

    @Test
    fun `invoke with specific language should return query with language filter`() {
        // Arrange
        val language = "kotlin"

        // Act
        val result = buildRepositoryQueryUseCase(language)

        // Assert
        assertEquals("size:>0 language:kotlin", result)
    }

    @Test
    fun `invoke should trim and lowercase the language`() {
        // Arrange
        val language = "  KOTLIN  "

        // Act
        val result = buildRepositoryQueryUseCase(language)

        // Assert
        assertEquals("size:>0 language:kotlin", result)
    }
}
