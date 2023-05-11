package com.example.calculate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.calculate.R
import com.example.calculate.databinding.FragmentChooseLevelBinding
import com.example.calculate.domain.entities.GameLevel

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameLevelListener()
    }

    private fun gameLevelListener() {
        with(binding) {
            buttonLevelTest.setOnClickListener {
                launchGameFragment(GameLevel.TEST)
            }
            buttonLevelEasy.setOnClickListener {
                launchGameFragment(GameLevel.EASY)
            }
            buttonLevelMedium.setOnClickListener {
                launchGameFragment(GameLevel.MEDIUM)
            }
            buttonLevelHard.setOnClickListener {
                launchGameFragment(GameLevel.HARD)
            }
        }
    }

    private fun launchGameFragment(gameLevel: GameLevel) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(gameLevel))
            .addToBackStack(GameFragment.NAME)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NAME = "ChooseLevelFragment"

        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }
}