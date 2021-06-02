package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.android2021.databinding.FragmentFirstBinding
import com.rsschool.android2021.interfaces.LastResultKeeper
import com.rsschool.android2021.interfaces.OnGenerateRandomClickListener
import java.lang.NumberFormatException


class FirstFragment : Fragment() {

    //view binding
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var generateListener: OnGenerateRandomClickListener? = null

    private var min = 0
    private var max = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        binding.ffTvPreviousResult.text = getString(R.string.previous_result, result)

        setListeners()

    }

    override fun onResume() {
        super.onResume()
        binding.ffTvPreviousResult.text =
            getString(R.string.previous_result, (requireActivity() as LastResultKeeper).lastResult)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnGenerateRandomClickListener) {
            generateListener = context
        }
    }

    override fun onDetach() {
        generateListener = null
        super.onDetach()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setListeners() {
        binding.ffBtnGenerate.setOnClickListener {
            generateListener?.onGenerateBtnPressed(min, max)
        }
        addMinChangeListener()
        addMaxChangeListener()
    }

    private fun addMinChangeListener() {
        with(binding) {
            ffTilMinValue.editText?.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                    try {
                        when {
                            text.isEmpty() -> {
                                min = 0
                                ffTilMinValue.error = "Minimum value is required"
                                ffTilMinValue.isErrorEnabled = true
                                ffBtnGenerate.isEnabled = false
                            }
                            text.isNotEmpty() -> {
                                if (text.trim().toString().toLong() < Int.MAX_VALUE) {
                                    val minNum = text.trim().toString().toInt()
                                    if (minNum < max && ffTilMaxValue.editText?.text?.trim().toString().toLong() < Int.MAX_VALUE) {
                                        min = minNum
                                        ffTilMinValue.isErrorEnabled = false
                                        if (ffTilMaxValue.editText?.text?.isNotEmpty()!!) {
                                            ffTilMaxValue.isErrorEnabled = false
                                            ffBtnGenerate.isEnabled = true
                                        }
                                    } else {
                                        min = minNum
                                        ffTilMinValue.error = "Must be less than maximum"
                                        ffTilMinValue.isErrorEnabled = true
                                        ffBtnGenerate.isEnabled = false
                                    }
                                } else {
                                    ffTilMinValue.error = "Should be less than ${Int.MAX_VALUE}"
                                    ffTilMinValue.isErrorEnabled = true
                                    ffBtnGenerate.isEnabled = false
                                }
                            }
                        }
                    } catch (e: NumberFormatException) {

                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
    }

    private fun addMaxChangeListener() {
        with(binding) {
            ffTilMaxValue.editText?.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                    try {
                        when {
                            text.isEmpty() -> {
                                max = 0
                                ffTilMaxValue.error = "Maximum value is required"
                                ffTilMaxValue.isErrorEnabled = true
                                ffBtnGenerate.isEnabled = false
                            }
                            text.isNotEmpty() -> {
                                if (text.trim().toString().toLong() < Int.MAX_VALUE) {
                                    val maxNum = text.trim().toString().toInt()
                                    val minNum =
                                        ffTilMinValue.editText?.text?.trim().toString().toLong()
                                    if (maxNum > min && minNum < Int.MAX_VALUE) {
                                        max = maxNum
                                        ffTilMaxValue.isErrorEnabled = false
                                        if (ffTilMinValue.editText?.text?.isNotEmpty()!!) {
                                            ffTilMinValue.isErrorEnabled = false
                                            ffBtnGenerate.isEnabled = true
                                        }
                                    } else {
                                        max = maxNum
                                        ffTilMaxValue.error = "Must be more than minimum"
                                        ffTilMaxValue.isErrorEnabled = true
                                        ffBtnGenerate.isEnabled = false
                                    }
                                } else {
                                    ffTilMaxValue.error = "Should be less than ${Int.MAX_VALUE}"
                                    ffTilMaxValue.isErrorEnabled = true
                                    ffBtnGenerate.isEnabled = false
                                }
                            }

                        }
                    } catch (e: NumberFormatException) {
                    }

                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}