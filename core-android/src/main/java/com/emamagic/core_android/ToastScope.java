package com.emamagic.core_android;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ToastScope.VIEW_SCOPE,
        ToastScope.MODULE_SCOPE
})

@Retention(RetentionPolicy.SOURCE)
public @interface ToastScope {
    int VIEW_SCOPE = 0;
    int MODULE_SCOPE = 1;
}