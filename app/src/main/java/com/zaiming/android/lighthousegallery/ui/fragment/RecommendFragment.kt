package com.zaiming.android.lighthousegallery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.zaiming.android.lighthousegallery.databinding.FragmentRecommendsBinding
import com.zaiming.android.lighthousegallery.extensions.customViewModel
import com.zaiming.android.lighthousegallery.viewmodel.RecommendViewModel
import timber.log.Timber

/**
 * @author zaiming
 */
class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendsBinding? = null
    private val binding get() = _binding!!
    private val recommendViewModel by customViewModel {
        RecommendViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecommendsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRecommend
        recommendViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.e("RecommendFragment instance id is ${Integer.toHexString(this.hashCode())}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
