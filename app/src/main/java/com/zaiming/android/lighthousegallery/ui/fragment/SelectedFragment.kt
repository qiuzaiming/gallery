package com.zaiming.android.lighthousegallery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zaiming.android.lighthousegallery.databinding.FragmentSelectedBinding
import com.zaiming.android.lighthousegallery.extensions.customViewModel
import com.zaiming.android.lighthousegallery.viewmodel.SelectedViewModel
import timber.log.Timber

/**
 * @author zaiming
 */
class SelectedFragment : Fragment() {

    private var _binding: FragmentSelectedBinding? = null
    private val binding get() = _binding!!
    private val selectViewModel by customViewModel {
        SelectedViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSelectedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSelected
        selectViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.e("SelectedFragment instance id is ${Integer.toHexString(this.hashCode())}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}