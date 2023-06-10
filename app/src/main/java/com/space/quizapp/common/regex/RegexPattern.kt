package com.space.quizapp.common.regex

object RegexPattern {
    // regexPattern : minimum of 8 and maximum of 20 characters
    // containing at least one uppercase letter and one number
    // does not start with a period "." or an underscore "_"

    //todo
    val usernamePattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20}$".toRegex()
}