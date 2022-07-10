package com.emamagic.domain.interactors.workspace

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.repositories.WorkspaceRepository
import javax.inject.Inject

class GetCurrentWorkspace @Inject constructor(
   @Bridge
   private val workspaceRepository: WorkspaceRepository,
   dispatchers: AppCoroutineDispatchers
) {

   suspend operator fun invoke(): ResultWrapper<WorkspaceEntity> {
      return ResultWrapper.Success(null)
   }

}