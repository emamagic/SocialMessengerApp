package com.emamagic.navigation.route;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        DeepLink.ACTION_TO_LOGIN,
        DeepLink.ACTION_TO_SIGNUP,
        DeepLink.ACTION_TO_CONVERSATIONS,
        DeepLink.ACTION_TO_WORKSPACE_SELECT,
        DeepLink.ACTION_TO_WORKSPACE_CREATE,
        DeepLink.ACTION_TO_WORKSPACE_ADD_MEMBER,
        DeepLink.ACTION_TO_CHAT,
        DeepLink.ACTION_TO_ALL_THREAD
})
@Retention(RetentionPolicy.SOURCE)
public @interface DeepLink {
    String ACTION_TO_LOGIN = InternalDeepLink.DOMAIN + "login";
    String ACTION_TO_SIGNUP = InternalDeepLink.DOMAIN + "signup";
    String ACTION_TO_CONVERSATIONS = InternalDeepLink.DOMAIN + "conversations";
    String ACTION_TO_WORKSPACE_SELECT = InternalDeepLink.DOMAIN + "workspace_select";
    String ACTION_TO_WORKSPACE_CREATE = InternalDeepLink.DOMAIN + "workspace_create";
    String ACTION_TO_WORKSPACE_ADD_MEMBER = InternalDeepLink.DOMAIN + "workspace_add_member";
    String ACTION_TO_CHAT = InternalDeepLink.DOMAIN + "chat";
    String ACTION_TO_ALL_THREAD = InternalDeepLink.DOMAIN + "all_thread";
}
