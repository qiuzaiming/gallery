package com.zaiming.android.lighthousegallery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zaiming.android.lighthousegallery.databinding.FragmentSelectedBinding
import com.zaiming.android.lighthousegallery.viewmodel.SelectedViewModel

class SelectedFragment : Fragment() {

    private var _binding: FragmentSelectedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(SelectedViewModel::class.java)

        _binding = FragmentSelectedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSelected
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}