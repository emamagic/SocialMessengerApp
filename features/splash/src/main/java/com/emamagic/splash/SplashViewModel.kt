package com.emamagic.splash

import com.emamagic.base.base.BaseViewModel
import android.util.Log
import com.emamagic.core.succeeded
import com.emamagic.domain.entities.OrganizationEntity
import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.interactors.GetCurrentUser
import com.emamagic.domain.interactors.GetOrganizations
import com.emamagic.domain.interactors.GetWorkspaces
import com.emamagic.splash.contract.SplashEvent
import com.emamagic.splash.contract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val getWorkspaces: GetWorkspaces,
    private val getOrganizations: GetOrganizations,
) : BaseViewModel<SplashState, SplashEvent, SplashRouter.Routes>() {

    override fun createInitialState(): SplashState = SplashState.initialize()

    override fun handleEvent(event: SplashEvent) {}


    init {
        withLoadingScope {
                getCurrentUser(Unit).manageResult(success = {
                    // todo check -> get workspaces and check for signup ...
                    flowOf(getWorkspaces(Unit)).zip(flowOf(getOrganizations(Unit))) { workspacesResult, organizationResult ->
                         if (workspacesResult.succeeded && organizationResult.succeeded) {
                             val workspaces = workspacesResult.data!!
                             val organization = organizationResult.data!!
                             if (isItFromWorkspaceLink()) {
                                 // todo goto select workspace
                             } else if (hasAnyAcceptedWorkspace(workspaces) || hasAnyOrganization(organization)) {
                                 routerDelegate.pushRoute(SplashRouter.Routes.ToConversations)
                             } else { // undefined state
                                 setState { copy(closeApp = true) }
                             }
                         } else {
                             setState { copy(closeApp = true) }
                         }
                     }.collect()
                }, failed = {
                    if (it.statusCode == 427) { // user need to signup
                        routerDelegate.pushRoute(SplashRouter.Routes.ToLogin)
                    }
                })
        }
    }


    private fun hasAnyAcceptedWorkspace(workspaces: List<WorkspaceEntity>): Boolean {
        val invitedWorkspace = workspaces.filter { it.isInvitation }
        return workspaces.size > invitedWorkspace.size
    }

    private fun isItFromWorkspaceLink(): Boolean {
        return false
    }

    private fun hasAnyOrganization(organizations: List<OrganizationEntity>): Boolean {
        return organizations.isNotEmpty()
    }

}