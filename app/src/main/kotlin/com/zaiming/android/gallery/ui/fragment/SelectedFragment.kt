package com.zaiming.android.gallery.ui.fragment

import android.view.ContextThemeWrapper
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.base.BaseControllerFragment
import com.zaiming.android.gallery.databinding.FragmentSelectedBinding
import com.zaiming.android.gallery.ui.viewgroup.SnapshotDashboardViewGroup
import com.zaiming.android.gallery.utils.windowInsets.applySystemBarImmersionMode
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author zaiming
 */
@AndroidEntryPoint
class SelectedFragment : BaseControllerFragment<FragmentSelectedBinding>() {

    override fun init() {

        binding.llRoot.applySystemBarImmersionMode()

        val view = SnapshotDashboardViewGroup(ContextThemeWrapper(context, R.style.SnapOutlinedStyle))
        binding.llRoot.addView(view)
    }

    override fun scrollToTop() {
    }

}
