package com.emamagic.core_android;

import static com.emamagic.core_android.ConversationType.BOTS;
import static com.emamagic.core_android.ConversationType.DIRECT_MESSAGE;
import static com.emamagic.core_android.ConversationType.PINNED;
import static com.emamagic.core_android.ConversationType.PRIVATE_GROUP;
import static com.emamagic.core_android.ConversationType.PUBLIC_GROUP;
import androidx.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        PINNED,
        PUBLIC_GROUP,
        PRIVATE_GROUP,
        DIRECT_MESSAGE,
        BOTS
})
@Retention(RetentionPolicy.SOURCE)
public @interface ConversationType {
    String PINNED = "pinned";
    String PUBLIC_GROUP = "public";
    String PRIVATE_GROUP = "private";
    String DIRECT_MESSAGE = "direct";
    String BOTS = "bots";
}