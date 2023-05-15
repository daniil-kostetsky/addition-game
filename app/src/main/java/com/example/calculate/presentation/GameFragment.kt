package com.example.calculate.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.calculate.R
import com.example.calculate.databinding.FragmentGameBinding
import com.example.calculate.domain.entities.GameLevel
import com.example.calculate.domain.entities.GameResult

class GameFragment : Fragment() {

    private lateinit var gameLevel: GameLevel

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

    private val gameViewModel: GameViewModel by lazy {
        ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvAnswerOption1)
            add(binding.tvAnswerOption2)
            add(binding.tvAnswerOption3)
            add(binding.tvAnswerOption4)
            add(binding.tvAnswerOption5)
            add(binding.tvAnswerOption6)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setOnAnswersOptionsCLickListener()
        gameViewModel.startGame(gameLevel)

    }

    private fun setOnAnswersOptionsCLickListener() {
        for (tvAnswerOption in tvOptions) {
            tvAnswerOption.setOnClickListener {
                gameViewModel.chooseAnswer(tvAnswerOption.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        gameViewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        gameViewModel.gameQuestion.observe(viewLifecycleOwner) {
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            binding.tvResult.text = it.sum.toString()
            for (i in tvOptions.indices) {
                tvOptions[i].text = it.answerOptions[i].toString()
            }
        }
        gameViewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        gameViewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
        gameViewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.setTextColor(getColorByState(it))
        }
        gameViewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        gameViewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        gameViewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }

    }

    private fun getColorByState(state: Boolean): Int {
        val colorResId =
            if (state) {
                android.R.color.holo_green_light
            } else {
                android.R.color.holo_red_light
            }
        return ContextCompat.getColor(requireContext(), colorResId)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun parseArgs() {
        requireArguments().getParcelable<GameLevel>(KEY_LEVEL)?.let {
            gameLevel = it
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        fun newInstance(gameLevel: GameLevel): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, gameLevel)
                }
            }
        }
    }
}