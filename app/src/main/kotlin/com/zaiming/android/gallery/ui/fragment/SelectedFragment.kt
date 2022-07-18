package com.zaiming.android.gallery.ui.fragment

import android.view.ContextThemeWrapper
import android.view.ViewGroup
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.base.BaseControllerFragment
import com.zaiming.android.gallery.databinding.FragmentSelectedBinding
import com.zaiming.android.gallery.extensions.dp
import com.zaiming.android.gallery.ui.base.CustomLayout
import com.zaiming.android.gallery.ui.base.matchParent
import com.zaiming.android.gallery.ui.base.wrapContent
import com.zaiming.android.gallery.ui.viewgroup.AndroidVersionLabelView
import com.zaiming.android.gallery.ui.viewgroup.AppInfoMaterialViewGroup
import com.zaiming.android.gallery.ui.viewgroup.MaterialChooseAlbumViewGroup
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

        val view2 = MaterialChooseAlbumViewGroup(ContextThemeWrapper(context, R.style.SnapOutlinedStyle)).apply {
            with(context) {
                (layoutParams as ViewGroup.MarginLayoutParams).topMargin = 20.dp.toInt()
            }
            chooseAlbumViewGroup.setResource(R.drawable.ic_add, R.string.snapshot_add, R.string.snapshot_move)
        }

        val view3 = AndroidVersionLabelView(requireContext()).apply {
            layoutParams = CustomLayout.LayoutParams(matchParent, wrapContent)
            with(context) {
                (layoutParams as ViewGroup.MarginLayoutParams).topMargin = 20.dp.toInt()
            }
            setAndroidVersionLabelContent(R.drawable.fab_icon_time_add, R.string.snapshot_move)
        }

       /* val view4 = AppInfoHeadView(requireContext()).apply {
            layoutParams = CustomLayout.LayoutParams(matchParent, wrapContent)
            with(context) {
                (layoutParams as ViewGroup.MarginLayoutParams).topMargin = 20.dp.toInt()
            }
        }*/

        val view4 = AppInfoMaterialViewGroup(ContextThemeWrapper(context, R.style.SnapOutlinedStyle))


        binding.llRoot.addView(view)
        binding.llRoot.addView(view2)
        // binding.llRoot.addView(view3)
        binding.llRoot.addView(view4)
    }

    override fun scrollToTop() {
    }

}
