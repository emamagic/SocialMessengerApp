package com.emamagic.domain
// todo handle sentry here
interface Logger {

    fun setup(debugMode: Boolean)

    fun setUserId(id: String)

    /** Log a verbose message with optional format args.  */
    fun v(message: String, vararg args: Any?)

    /** Log a verbose exception and a message with optional format args.  */
    fun v(t: Throwable, message: String, vararg args: Any?)

    /** Log a verbose exception.  */
    fun v(t: Throwable)

    /** Log a debug message with optional format args.  */
    fun d(message: String, vararg args: Any?)

    /** Log a debug exception and a message with optional format args.  */
    fun d(t: Throwable, message: String, vararg args: Any?)

    /** Log a debug exception.  */
    fun d(t: Throwable)

    /** Log an info message with optional format args.  */
    fun i(message: String, vararg args: Any?)

    /** Log an info exception and a message with optional format args.  */
    fun i(t: Throwable, message: String, vararg args: Any?)

    /** Log an info exception.  */
    fun i(t: Throwable)

    /** Log a warning message with optional format args.  */
    fun w(message: String, vararg args: Any?)

    /** Log a warning exception and a message with optional format args.  */
    fun w(t: Throwable, message: String, vararg args: Any?)

    /** Log a warning exception.  */
    fun w(t: Throwable)

    /** Log an error message with optional format args.  */
    fun e(message: String, vararg args: Any?)

    /** Log an error exception and a message with optional format args.  */
    fun e(t: Throwable, message: String, vararg args: Any?)

    /** Log an error exception.  */
    fun e(t: Throwable)

    /** Log an assert message with optional format args.  */
    fun wtf(message: String, vararg args: Any?)

    /** Log an assert exception and a message with optional format args.  */
    fun wtf(t: Throwable, message: String, vararg args: Any?)

    /** Log an assert exception.  */
    fun wtf(t: Throwable)
}