package com.space.quizapp.common.regex

object RegexPattern {
    // regexPattern : minimum of 8 and maximum of 20 characters
    // containing at least one uppercase letter and one number
    // does not start with a period "." or an underscore "_"
    val usernamePattern = "[a-zA-Z0-9][a-zA-Z0-9_]{7,19}".toRegex()
}