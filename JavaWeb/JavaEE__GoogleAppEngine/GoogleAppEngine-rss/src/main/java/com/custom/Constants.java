package com.custom;

import com.google.api.server.spi.Constant;

/**
 * Contains the client IDs and scopes for allowed clients consuming your API.
 */
public class Constants {
  public static final String WEB_CLIENT_ID = "60969034300-tmgrpmgjpkfjgo0p3rkis4ptrqekj1hm.apps.googleusercontent.com";
  public static final String API_EXPLORER_CLIENT_ID = Constant.API_EXPLORER_CLIENT_ID;

  public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";

  public static final String MEMCACHE_COUNTER_KEY = "counter";
}
