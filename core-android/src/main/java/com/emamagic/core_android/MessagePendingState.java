package com.emamagic.core_android;

import static com.emamagic.core_android.MessagePendingState.DEFAULT;
import static com.emamagic.core_android.MessagePendingState.DELETED;
import static com.emamagic.core_android.MessagePendingState.DELETING_FAILED;
import static com.emamagic.core_android.MessagePendingState.DONE;
import static com.emamagic.core_android.MessagePendingState.EDITED;
import static com.emamagic.core_android.MessagePendingState.EDITING_FAILED;
import static com.emamagic.core_android.MessagePendingState.PENDING_DELETE;
import static com.emamagic.core_android.MessagePendingState.PENDING_EDIT;
import static com.emamagic.core_android.MessagePendingState.PENDING_SEND;
import static com.emamagic.core_android.MessagePendingState.SEEN;
import static com.emamagic.core_android.MessagePendingState.SENDING_FAILED;

import androidx.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        DEFAULT,
        PENDING_DELETE,
        PENDING_SEND,
        PENDING_EDIT,
        SENDING_FAILED,
        EDITING_FAILED,
        DELETING_FAILED,
        DELETED,
        DONE,
        EDITED,
        SEEN
})
@Retention(RetentionPolicy.SOURCE)
public @interface MessagePendingState {
    String DEFAULT = "unknown";
    String PENDING_DELETE = "pending_delete";
    String PENDING_SEND = "pending_send";
    String PENDING_EDIT = "pending_edit";
    String SENDING_FAILED = "sending_failed";
    String EDITING_FAILED = "editing_failed";
    String DELETING_FAILED = "deleting_failed";
    String DELETED = "deleted";
    String DONE = "done";
    String EDITED = "edited";
    String SEEN = "seen";
}