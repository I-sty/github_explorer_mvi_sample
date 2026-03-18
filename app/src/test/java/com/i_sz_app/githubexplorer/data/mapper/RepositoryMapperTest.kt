package com.i_sz_app.githubexplorer.data.mapper

import com.i_sz_app.core.domain.model.RepositoryDetailsModel
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryEntity
import com.i_sz_app.githubexplorer.data.remote.dto.LicenseDTO
import com.i_sz_app.githubexplorer.data.remote.dto.OwnerDTO
import com.i_sz_app.githubexplorer.data.remote.dto.RepositoryDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class RepositoryMapperTest {
    @Test
    fun `RepositoryDto toEntity maps all fields correctly`() {
        // Arrange
        val dto: RepositoryDTO = fakeRepositoryDto()
        val entity: RepositoryEntity = dto.toEntity("kotlin")

        // Assert
        assertEquals(dto.id, entity.id)
        assertEquals("kotlin", entity.query)
        assertEquals(dto.owner.login, entity.ownerName)
        assertEquals(dto.owner.avatarUrl, entity.ownerAvatar)
    }

    @Test
    fun `RepositoryEntity toDomain maps all fields correctly`() {
        // Arrange
        val entity: RepositoryEntity = fakeRepositoryEntity()
        val model: RepositoryDetailsModel = entity.toDomain()

        // Assert
        assertEquals(entity.id, model.id)
        assertEquals(entity.stargazersCount, model.stargazersCount)
        assertEquals(entity.ownerName, model.ownerName)
        assertEquals(entity.ownerAvatar, model.ownerAvatar)
    }

    private fun fakeOwnerDto() = OwnerDTO(
        login = "John Doe",
        avatarUrl = "avatar",
        url = "url",
        type = "type",
        siteAdmin = false,
        id = 1,
        nodeId = "node_id"
    )

    private fun fakeRepositoryDto() = RepositoryDTO(
        id = 1,
        name = "repo",
        owner = fakeOwnerDto(),
        stargazersCount = 10,
        forksCount = 5,
        nodeId = "node_id",
        fullName = "full_repo_name",
        private = false,
        htmlUrl = "fake_url",
        description = "fake_description",
        fork = false,
        url = "fake_url",
        createdAt = "yesterday",
        updatedAt = "today",
        pushedAt = "today",
        homepage = "fake_homepage",
        size = 123,
        watchersCount = 123,
        language = "kotlin",
        openIssuesCount = 123,
        masterBranch = "main",
        defaultBranch = "develop",
        score = 123.0,
        forks = 123,
        openIssues = 123,
        watchers = 123,
        hasIssues = true,
        hasProjects = true,
        hasPages = true,
        hasWiki = true,
        hasDownloads = true,
        archived = false,
        disabled = false,
        visibility = "visible",
        license = LicenseDTO(
            key = "key",
            name = "name",
            url = "url",
            spdxId = "id",
            nodeId = "node_id"
        )
    )

    private fun fakeRepositoryEntity() = RepositoryEntity(
        id = 1,
        createdAt = "yestarday",
        defaultBranchName = "main",
        description = "fake_description",
        forksCount = 123,
        isForked = true,
        name = "repo_name",
        openIssuesCount = 123,
        ownerAvatar = "fake_owner_avatar",
        ownerName = "John Doe",
        size = 123,
        stargazersCount = 123,
        updatedAt = "today",
        watchersCount = 123,
        query = "fake_query"
    )
}
