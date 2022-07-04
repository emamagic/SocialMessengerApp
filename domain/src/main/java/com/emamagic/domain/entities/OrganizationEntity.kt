package com.emamagic.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "organization")
data class OrganizationEntity(
    @PrimaryKey
    override val id: String,
    val name: String?,
    val description: String?,
    val iconHash: String?,
    val bannerHash: String?,
    val workspaceInvitationPermissionMode: String?,
    val myMembership: MyMembership?
): BaseEntity