package com.elapp.booque.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.elapp.booque.R
import com.elapp.booque.presentation.ui.appintro.ScreenItem
import com.elapp.booque.utils.helper.ArticleOnClickListener
import com.squareup.picasso.Picasso

class BannerAdapter(
    context: Context?, banners: List<BannerModel>
) : PagerAdapter() {

    var mContext: Context = context!!
    var mListBanner: List<BannerModel> = banners
    private lateinit var articleOnClickListener: ArticleOnClickListener

    fun itemOnClickListener(listener: ArticleOnClickListener) {
        this.articleOnClickListener = listener
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutScreen = inflater.inflate(R.layout.fragment_banner, null)
        layoutScreen.setOnClickListener {
            articleOnClickListener.onItemClicked(mListBanner[position].image)
        }

        val imgSlide: ImageView = layoutScreen.findViewById(R.id.imgBanner)

        val url = mListBanner[position].image
        Picasso.get().load(url).into(imgSlide)

        container.addView(layoutScreen)

        return layoutScreen
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = mListBanner.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}