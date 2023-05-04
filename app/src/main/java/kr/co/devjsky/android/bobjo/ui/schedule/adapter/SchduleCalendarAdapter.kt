package kr.co.devjsky.android.bobjo.ui.schedule.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.devjsky.android.bobjo.ui.schedule.fragment.ScheduleCalendarFragment
import org.joda.time.DateTime

class SchduleCalendarAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    /* 달의 첫 번째 Day timeInMillis*/
    private var start: Long = DateTime().withDayOfMonth(1).withTimeAtStartOfDay().millis

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): ScheduleCalendarFragment {
        val millis = getItemId(position)
        return ScheduleCalendarFragment.newInstance(millis)
    }

    override fun getItemId(position: Int): Long
            = DateTime(start).plusMonths(position - START_POSITION).millis

    override fun containsItem(itemId: Long): Boolean {
        val date = DateTime(itemId)

        return date.dayOfMonth == 1 && date.millisOfDay == 0
    }

    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2
    }
}