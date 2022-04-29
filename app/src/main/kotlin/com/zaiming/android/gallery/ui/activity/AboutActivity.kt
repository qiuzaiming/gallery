package com.zaiming.android.gallery.ui.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.drakeet.about.*
import com.zaiming.android.gallery.BuildConfig
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.utils.constantUtils.AuthorConstants


/**
 * about
 */
class AboutActivity : AbsAboutActivity() {

    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.mipmap.ic_launcher)
        slogan.setText(R.string.app_name)
        version.text = String.format("%s (%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }

    override fun onItemsCreated(items: MutableList<Any>) {
        items.apply {
            add(Category(getString(R.string.about_category_introduce)))
            add(Card(getString(R.string.about_card_introduce)))

            add(Category("Developer"))
            add(Contributor(R.drawable.developer, "Zaiming", "Developer", "https://github.com/qiuzaiming"))

            add(Category("Open Source Licenses"))
            add(License("kotlin", "JetBrains", License.APACHE_2, "https://github.com/JetBrains/kotlin"))
            add(License("AndroidX", "Google", License.APACHE_2, "https://source.google.com"))
            add(License("Android Jetpack", "Google", License.APACHE_2, "https://source.google.com"))
            add(License("lottie-android", "Airbnb", License.APACHE_2, "https://github.com/airbnb/lottie-android"))
            add(License("MultiType", "drakeet", License.APACHE_2, "https://github.com/drakeet/MultiType"))
            add(License("about-page", "drakeet", License.APACHE_2, "https://github.com/drakeet/about-page"))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.about_menu_like) {
            try {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(AuthorConstants.githubUrl)
                })
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }
}