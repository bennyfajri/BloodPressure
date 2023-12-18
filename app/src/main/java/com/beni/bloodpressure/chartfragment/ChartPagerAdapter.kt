package com.beni.bloodpressure.chartfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.beni.core.util.ConstantVariable.ARG_SECTION_TITLE

class ChartPagerAdapter(
    private val tabTitle: List<String>,
    activity: AppCompatActivity
) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return tabTitle.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = ChartFragment()
        fragment.arguments = Bundle().apply {
            putString(ARG_SECTION_TITLE, tabTitle[position])
        }
        return fragment
    }
}