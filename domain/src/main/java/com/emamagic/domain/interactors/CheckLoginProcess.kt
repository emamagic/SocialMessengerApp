package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.succeeded
import com.emamagic.domain.entities.OrganizationEntity
import com.emamagic.domain.entities.WorkspaceEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckLoginProcess @Inject constructor(
    private val getWorkspaces: GetWorkspaces,
    private val getOrganizations: GetOrganizations,
    private val dispatchers: AppCoroutineDispatchers
) {

    suspend operator fun invoke(): LoginProcessResult = withContext(dispatchers.io) {
        flowOf(getWorkspaces(Unit)).zip(flowOf(getOrganizations(Unit))) { workspacesResult, organizationResult ->
            if (workspacesResult.succeeded && hasAnyWorkspace(workspacesResult.data!!)) {
                if (hasAnyAcceptedWorkspace(workspacesResult.data!!)) {
                    LoginProcessResult.GoToConversation
                } else {
                    if (organizationResult.succeeded && hasAnyOrganization(organizationResult.data!!)) {
                        LoginProcessResult.GoToConversation
                    } else {
                        LoginProcessResult.GoToWorkspaceSelect
                    }
                }
            } else {
                if (organizationResult.succeeded && hasAnyOrganization(organizationResult.data!!)) {
                    LoginProcessResult.GoToConversation
                } else {
                    LoginProcessResult.GoToWorkspaceCreate
                }
            }
        }.first()
    }


    private fun hasAnyAcceptedWorkspace(workspaces: List<WorkspaceEntity>): Boolean {
        val invitedWorkspace = workspaces.filter { it.isInvitation }
        return workspaces.size > invitedWorkspace.size
    }

    private fun hasAnyOrganization(organizations: List<OrganizationEntity>): Boolean {
        return organizations.isNotEmpty()
    }

    private fun hasAnyWorkspace(workspaces: List<WorkspaceEntity>): Boolean {
        return workspaces.isNotEmpty()
    }

    sealed class LoginProcessResult {
        object GoToConversation: LoginProcessResult()
        object GoToWorkspaceSelect: LoginProcessResult()
        object GoToWorkspaceCreate: LoginProcessResult()
    }

}