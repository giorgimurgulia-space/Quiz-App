package com.space.quizapp

import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflater<T> = (inflater: LayoutInflater, view: ViewGroup?, attach: Boolean) -> T