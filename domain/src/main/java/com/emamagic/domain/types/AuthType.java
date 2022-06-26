package com.emamagic.domain.types;

import androidx.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({})
@Retention(RetentionPolicy.SOURCE)
public @interface AuthType {
    String ALL = "all";
    String USER_PASS = "userpass";
    String PHONE_NUMBER = "phonenumber";
}
