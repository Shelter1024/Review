package com.shelter.review

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.LARGE
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.shelter.review.fragment.MyFragment1
import com.shelter.review.fragment.MyFragment2
import kotlinx.android.synthetic.main.activity_touch_event.*
import kotlinx.coroutines.delay
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textColor

class TouchEventActivity : AppCompatActivity() {
    val fragments = ArrayList<Fragment>()
    val views = ArrayList<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_event)
        initView()
    }

    private fun initView() {
        initData1()
        initData2()
//        viewPager2.adapter = TVAdapter(this)
        viewPager.adapter = TVAdapter1()

//        swipeRefreshLayout.setColorSchemeResources(R.color.blue)
        swipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.red, R.color.black)
        swipeRefreshLayout.setSize(LARGE)
//        swipeRefreshLayout.setDistanceToTriggerSync(300)
//        swipeRefreshLayout.setProgressViewOffset(true, 0, 100)
//        swipeRefreshLayout.setProgressViewEndTarget(true, 200)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.postDelayed({
                swipeRefreshLayout.isRefreshing = false
            }, 1000)
        }
    }

    private fun initData2() {
        fragments.add(MyFragment1())
        fragments.add(MyFragment2())
    }

    private fun initData1() {
        for (i in 0..3) {
            val textView = TextView(this)
            textView.textColor = 0xff000000.toInt()
            textView.typeface = Typeface.DEFAULT_BOLD
            val layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
            textView.layoutParams = layoutParams
            textView.gravity = Gravity.CENTER
            textView.textSize = 30F
            textView.text = "TextView $i"
            views.add(textView)
        }
    }

    inner class TVAdapter2(val activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    inner class TVAdapter1() : PagerAdapter() {


        override fun getCount(): Int {
            return views.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(views[position])
            return views[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View?)
        }

    }
}