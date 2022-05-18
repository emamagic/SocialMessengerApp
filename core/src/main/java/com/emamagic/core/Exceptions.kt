package com.emamagic.core

import java.io.IOException

class NoInternetException(message: String? = null): IOException(message)
class ServerConnectionException(message: String? = null): IOException(message)

/** for defining custom exception you should extend from Exception */
class NoCurrentUserFoundException(message: String? = null): Exception(message)
class UserShouldNotBeLoginException(message: String? = null): Exception(message)