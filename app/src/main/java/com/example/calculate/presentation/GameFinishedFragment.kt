package com.example.calculate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.calculate.R
import com.example.calculate.databinding.FragmentGameFinishedBinding
import com.example.calculate.domain.entities.GameResult

class GameFinishedFragment : Fragment() {

    private val gameResult: GameResult by lazy {
        GameFinishedFragmentArgs.fromBundle(requireArguments()).gameResult
    }

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding = null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        setSmileImage()
        setMinCountOfRightAnswers()
        setUserCountOfRightAnswers()
        setMinPercentageOfRightAnswers()
        setUserPercentageOfRightAnswers()
    }

    private fun setupClickListeners() {
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    private fun setUserPercentageOfRightAnswers() {
        val userPercentage = ((gameResult.countOfRightAnswers /
                gameResult.countOfQuestions.toDouble()) * 100).toInt()
        binding.tvScorePercentage.text = String.format(
            getString(R.string.tv_user_percentage),
            userPercentage.toString()
        )
    }

    private fun setMinPercentageOfRightAnswers() {
        binding.tvRequiredPercentage.text = String.format(
            getString(R.string.tv_min_percentage),
            gameResult.gameSettings.minPercentOfRightAnswer.toString()
        )
    }

    private fun setUserCountOfRightAnswers() {
        binding.tvScoreAnswers.text = String.format(
            getString(R.string.tv_user_score),
            gameResult.countOfRightAnswers.toString()
        )
    }

    private fun setMinCountOfRightAnswers() {
        binding.tvRequiredAnswers.text = String.format(
            getString(R.string.tv_min_count_of_right_answers),
            gameResult.gameSettings.minCountOfRightAnswers.toString())
    }

    private fun setSmileImage() {
        if (gameResult.isWin) {
            binding.ivSmileResult.setImageResource(R.drawable.smile_fun)
        } else {
            binding.ivSmileResult.setImageResource(R.drawable.smile_sad)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }
}