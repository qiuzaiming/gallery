package com.zaiming.android.gallery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zaiming.android.gallery.databinding.FragmentSelectedBinding
import com.zaiming.android.gallery.extensions.customViewModel
import com.zaiming.android.gallery.viewmodel.SelectedViewModel
import timber.log.Timber

/**
 * @author zaiming
 */
class SelectedFragment : Fragment() {

    private lateinit var binding: FragmentSelectedBinding
    private val selectViewModel by customViewModel {
        SelectedViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSelectedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.e("SelectedFragment instance id is ${Integer.toHexString(this.hashCode())}")
    }

}
