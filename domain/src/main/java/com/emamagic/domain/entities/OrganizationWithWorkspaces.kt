package com.emamagic.domain.entities

import androidx.room.Embedded
import androidx.room.Relation

data class OrganizationWithWorkspaces(
    @Embedded
    val organizations: OrganizationEntity,
    @Relation(parentColumn = "id", entityColumn = "organizationId")
    val workspaces: List<WorkspaceEntity>
)