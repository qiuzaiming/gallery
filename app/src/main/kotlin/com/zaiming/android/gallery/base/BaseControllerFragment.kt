package com.zaiming.android.gallery.base

import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.zaiming.android.gallery.galleryinterface.IController
import com.zaiming.android.gallery.viewmodel.GalleryViewModel

abstract class BaseControllerFragment<VB : ViewBinding> : BaseFragment<VB>(), IController {

    protected val galleryViewModel: GalleryViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        if (galleryViewModel.controller != this) {
            galleryViewModel.controller = this
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (galleryViewModel.controller == this) {
            galleryViewModel.controller = null
        }
    }
}
