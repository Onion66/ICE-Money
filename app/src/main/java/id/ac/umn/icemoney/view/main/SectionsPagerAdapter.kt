package id.ac.umn.icemoney.view.main

import android.content.Context
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.view.transaction.AddExpenseFragment
import id.ac.umn.icemoney.view.transaction.AddIncomeFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, private val bottomSheetBehavior: BottomSheetBehavior<FrameLayout>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> AddIncomeFragment.newInstance(bottomSheetBehavior)
            1 -> AddExpenseFragment.newInstance(bottomSheetBehavior)
            else -> PlaceholderFragment.newInstance(position + 1) // Error Page Fragment
        }
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}