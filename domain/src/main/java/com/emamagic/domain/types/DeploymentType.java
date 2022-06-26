package com.emamagic.domain.types;

import androidx.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({DeploymentType.SAAS, DeploymentType.ON_PREMISE})
@Retention(RetentionPolicy.SOURCE)
public @interface DeploymentType {
    String SAAS = "saas";
    String ON_PREMISE = "on-premise";
}
