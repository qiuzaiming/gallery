package com.zaiming.android.gallery.ui.viewgroup

import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import com.google.android.material.card.MaterialCardView
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.extensions.*
import com.zaiming.android.gallery.ui.base.CustomLayout
import com.zaiming.android.gallery.ui.base.matchParent
import com.zaiming.android.gallery.ui.base.wrapContent

class SnapshotDashboardViewGroup(context: Context) :
    MaterialCardView(context, null, R.style.SnapOutlinedStyle) {

    private val snapOutlineViewGroup = SnapOutlineViewGroup(context).apply {
        layoutParams = LayoutParams(wrapContent, wrapContent)
        with(context) {
            setPadding(16.dp.toInt())
        }
        clipToPadding = false
    }

    init {
        layoutParams = LayoutParams(matchParent, wrapContent)
        with(context) {
            radius = 8.dp
            cardElevation = 0.dp
        }
        setCardBackgroundColor(R.color.teal_200)
        addView(snapOutlineViewGroup)
    }

    private class SnapOutlineViewGroup(context: Context) : CustomLayout(context) {

        private val addDetail = SnapOutlineDetailViewGroup(context).apply {
            layoutParams = LayoutParams(wrapContent, wrapContent)
            setDetailContent(
                content = R.string.snapshot_add.getString(context),
                iconRes = R.drawable.ic_add.getDrawable(context),
                desColor = R.color.purple_500.getColor(context)
            )
            this@SnapOutlineViewGroup.addView(this)
        }

        private val removeDetail = SnapOutlineDetailViewGroup(context).apply {
            layoutParams = LayoutParams(wrapContent, wrapContent)
            setDetailContent(
                content = R.string.snapshot_remove.getString(context),
                iconRes = R.drawable.ic_remove.getDrawable(context),
                desColor = R.color.purple_500.getColor(context)
            )
            this@SnapOutlineViewGroup.addView(this)
        }

        private val changeDetail = SnapOutlineDetailViewGroup(context).apply {
            layoutParams = LayoutParams(wrapContent, wrapContent)
            setDetailContent(
                content = R.string.snapshot_change.getString(context),
                iconRes = R.drawable.ic_changed.getDrawable(context),
                desColor = R.color.purple_500.getColor(context)
            )
            this@SnapOutlineViewGroup.addView(this)
        }

        private val moveDetail = SnapOutlineDetailViewGroup(context).apply {
            layoutParams = LayoutParams(wrapContent, wrapContent)
            setDetailContent(
                content = R.string.snapshot_move.getString(context),
                iconRes = R.drawable.ic_move.getDrawable(context),
                desColor = R.color.purple_500.getColor(context)
            )
            this@SnapOutlineViewGroup.addView(this)
        }

        private val timeStampTitle = AppCompatTextView(context).apply {
            layoutParams = LayoutParams(wrapContent, wrapContent)
            text = R.string.snapshot_time_stamp.getString(context)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            this@SnapOutlineViewGroup.addView(this)
        }

        private val timeStampContent = AppCompatTextView(context).apply {
            layoutParams = LayoutParams(wrapContent, wrapContent)
            text = (System.currentTimeMillis() / 1000).dateFormat()
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            this@SnapOutlineViewGroup.addView(this)
        }

        private val picturesNumberTitle = AppCompatTextView(context).apply {
            layoutParams = LayoutParams(wrapContent, wrapContent).also {
                it.topMargin = 5.dp
            }
            text = R.string.snapshot_number_pictures.getString(context)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            this@SnapOutlineViewGroup.addView(this)
        }

        private val picturesNumberContent = AppCompatTextView(context).apply {
            layoutParams = LayoutParams(wrapContent, wrapContent)
            text = "22 / 34"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            this@SnapOutlineViewGroup.addView(this)
        }


        override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            addDetail.autoMeasure()
            removeDetail.autoMeasure()
            changeDetail.autoMeasure()
            moveDetail.autoMeasure()

            val maxDetailContentWidth = listOf(
                addDetail.measuredWidth,
                removeDetail.measuredWidth,
                changeDetail.measuredWidth,
                moveDetail.measuredWidth
            ).maxOrNull()!!

            val detailContentTotalHeight = addDetail.measuredHeight * 4

            // default this ViewGroup has padding value
            val remainWidth = measuredWidth - maxDetailContentWidth - paddingStart - paddingEnd
            timeStampTitle.let {
                it.measure(remainWidth.toExactlyMeasureSpec(), it.defaultHeightMeasureSpec(this))
            }

            timeStampContent.let {
                it.measure(remainWidth.toExactlyMeasureSpec(), it.defaultHeightMeasureSpec(this))
            }

            picturesNumberTitle.let {
                it.measure(remainWidth.toExactlyMeasureSpec(), it.defaultHeightMeasureSpec(this))
            }

            picturesNumberContent.let {
                it.measure(remainWidth.toExactlyMeasureSpec(), it.defaultHeightMeasureSpec(this))
            }

            setMeasuredDimension(measuredWidth,
                (timeStampTitle.measuredHeight + timeStampContent.measuredHeight
                        + picturesNumberTitle.measureHeightWithMargin + picturesNumberContent.measuredHeight)
                    .coerceAtLeast(detailContentTotalHeight) + paddingTop + paddingEnd
            )
        }

        override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
            timeStampTitle.layout(paddingStart, paddingTop)
            timeStampContent.layout(timeStampTitle.left, timeStampTitle.bottom)
            picturesNumberTitle.layout(timeStampTitle.left,
                timeStampContent.bottom + picturesNumberTitle.marginTop)
            picturesNumberContent.layout(timeStampTitle.left, picturesNumberTitle.bottom)

            addDetail.layout(paddingEnd, paddingTop, fromEnd = true)
            removeDetail.layout(paddingEnd, addDetail.bottom, fromEnd = true)
            changeDetail.layout(paddingEnd, removeDetail.bottom, fromEnd = true)
            moveDetail.layout(paddingEnd, changeDetail.bottom, fromEnd = true)
        }

        private class SnapOutlineDetailViewGroup(context: Context) : CustomLayout(context) {

            private val text = AppCompatTextView(context).apply {
                layoutParams = LayoutParams(wrapContent, wrapContent)
                setTextColor(Color.BLACK)
                addView(this)
            }

            private val imageDes = AppCompatImageView(context).apply {
                layoutParams = LayoutParams(16.dp, 16.dp).apply {
                    marginStart = 4.dp
                }
                addView(this)
            }

            private val descriptionView = View(context).apply {
                layoutParams = LayoutParams(20.dp, 5.dp).apply {
                    marginStart = 4.dp
                }
                addView(this)
            }

            fun setDetailContent(content: String, iconRes: Drawable?, @ColorInt desColor: Int) {
                text.text = content
                imageDes.setImageDrawable(iconRes)
                descriptionView.setBackgroundColor(desColor)
            }

            override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                text.autoMeasure()
                imageDes.autoMeasure()
                descriptionView.autoMeasure()
                setMeasuredDimension(text.measuredWidth + descriptionView.measureWidthWithMargin + descriptionView.measureWidthWithMargin,
                    text.measuredHeight.coerceAtLeast(imageDes.measuredHeight))

                descriptionView.apply {
                    clipToOutline = true
                    outlineProvider = object : ViewOutlineProvider() {
                        override fun getOutline(view: View, ouline: Outline?) {
                            ouline?.setRoundRect(
                                0,
                                0,
                                this@apply.measuredWidth,
                                this@apply.measuredHeight,
                                this@apply.measuredHeight / 2f
                            )
                        }
                    }
                }
            }

            override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
                text.layout(0, text.toVerticalCenter())
                imageDes.layout(text.right + imageDes.marginStart, imageDes.toVerticalCenter())
                descriptionView.layout(imageDes.right + descriptionView.marginStart,
                    descriptionView.toVerticalCenter())
            }

        }

    }

}




