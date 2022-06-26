package com.emamagic.navigation.route;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        DeepLink.ACTION_TO_LOGIN
})
@Retention(RetentionPolicy.SOURCE)
public @interface DeepLink {
    String ACTION_TO_LOGIN = InternalDeepLink.DOMAIN + "login";
}
