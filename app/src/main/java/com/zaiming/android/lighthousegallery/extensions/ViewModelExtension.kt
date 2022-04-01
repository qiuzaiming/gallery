package com.zaiming.android.lighthousegallery.extensions

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ParamViewModelFactory<VM : ViewModel>(
    private val factory: () -> VM
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = factory() as T
}

/**
 * create activity viewmodel instance with params
 */
inline fun <reified VM : ViewModel> AppCompatActivity.customViewModel(
    noinline factory: () -> VM
): Lazy<VM> = viewModels { ParamViewModelFactory(factory) }

/**
 * create fragment viewmodel instance with params
 */
inline fun <reified VM : ViewModel> Fragment.customViewModel(
    noinline factory: () -> VM
): Lazy<VM> = viewModels { ParamViewModelFactory(factory) }
