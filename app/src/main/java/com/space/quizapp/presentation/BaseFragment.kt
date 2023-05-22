package com.space.quizapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.space.quizapp.Inflater


abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflater<VB>) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    abstract fun onBind()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}