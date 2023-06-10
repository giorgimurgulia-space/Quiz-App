package com.space.quizapp.presentation.quiz

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.space.quizapp.R
import com.space.quizapp.databinding.FragmentQuizBinding
import com.space.quizapp.presentation.base.fragment.BaseFragment
import com.space.quizapp.presentation.model.AnswerStatus
import com.space.quizapp.presentation.model.AnswerUIModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizFragment :
    BaseFragment<FragmentQuizBinding, QuizViewModel>(FragmentQuizBinding::inflate) {

    override val viewModel: QuizViewModel by viewModels()
    private val adapter = AnswerAdapter()


    override fun onBind() {
        //todo
        val subjectId = arguments?.getString("subjectID")
        binding.titleText.text = "პროგრამირებ"

        binding.mainRecycler.layoutManager =
            LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter
    }

    override fun setObserves() {
        binding.questionText.text = "რომელია ყველაზე პოპულარული პროგრამირების ენა?"
        binding.submitButton.text = "შემდეგი"


        adapter.submitList(
            listOf(
                AnswerUIModel("1", "gio", AnswerStatus.CORRECT),
                AnswerUIModel("2", "gio", AnswerStatus.NEGATIVE),
                AnswerUIModel("3", "gio", AnswerStatus.POSITIVE),
                AnswerUIModel("4", "gio", null)
            )
        )
    }

    override fun setListeners() {
        binding.cancelImage.setOnClickListener {
            showQuestionDialog(R.string.cancel_quiz, onPositiveButtonClick = {
                viewModel.navigateBack()
            })
        }
    }

}