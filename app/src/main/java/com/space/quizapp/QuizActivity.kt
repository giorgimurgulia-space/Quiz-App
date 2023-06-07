package com.space.quizapp

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.space.quizapp.common.regex.RegexPattern
import com.space.quizapp.databinding.ActivityQuizBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
