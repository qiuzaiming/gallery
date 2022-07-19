package com.zaiming.android.gallery.ui.fragment

import android.view.ContextThemeWrapper
import android.view.ViewGroup
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.base.BaseControllerFragment
import com.zaiming.android.gallery.databinding.FragmentSelectedBinding
import com.zaiming.android.gallery.extensions.dp
import com.zaiming.android.gallery.galleryinterface.INavController
import com.zaiming.android.gallery.ui.base.CustomLayout
import com.zaiming.android.gallery.ui.base.matchParent
import com.zaiming.android.gallery.ui.base.wrapContent
import com.zaiming.android.gallery.ui.viewgroup.*
import com.zaiming.android.gallery.utils.windowInsets.applySystemBarImmersionMode
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author zaiming
 */
@AndroidEntryPoint
class SelectedFragment : BaseControllerFragment<FragmentSelectedBinding>() {

    override fun init() {

        binding.llRoot.applySystemBarImmersionMode()

        binding.scrollview.setOnScrollChangeListener { _, _, _, _, _ ->
          if (binding.scrollview.scrollY > 0) {
              (activity as INavController).slideDown()
          } else {
              (activity as INavController).slideUp()
          }
        }

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

        val view5 = AppItemViewGroup(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
            setContent(R.drawable.ic_launch, "packageName", "name", "1.0.1")
        }

        val view6 = AppInitViewGroup(requireContext())

        val view7 = RulesMaterialViewGroup(ContextThemeWrapper(context, R.style.SnapOutlinedStyle))


        binding.llRoot.addView(view)
        binding.llRoot.addView(view2)
        binding.llRoot.addView(view3)
        binding.llRoot.addView(view4)
        binding.llRoot.addView(view5)
        binding.llRoot.addView(view6)
        binding.llRoot.addView(view7)
    }

    override fun scrollToTop() {
    }

}
