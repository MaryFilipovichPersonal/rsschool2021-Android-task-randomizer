package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.rsschool.android2021.databinding.FragmentSecondBinding
import com.rsschool.android2021.interfaces.LastResultKeeper

class SecondFragment : Fragment() {

    //view binding
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private var onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (requireActivity() as LastResultKeeper).lastResult = generatedValue
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private var generatedValue = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        generatedValue = generate(min, max)
        binding.result.text = generatedValue.toString()

        setListeners()
    }

    override fun onDestroyView() {
        onBackPressedCallback.isEnabled = false
        onBackPressedCallback.remove()
        _binding = null
        super.onDestroyView()
    }

    private fun setListeners() {
        binding.back.setOnClickListener {
            (requireActivity() as LastResultKeeper).lastResult = generatedValue
            requireActivity().supportFragmentManager.popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(),
            onBackPressedCallback
        )
    }

    private fun generate(min: Int, max: Int) = (min..max).random()

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}