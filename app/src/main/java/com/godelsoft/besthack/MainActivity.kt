package com.godelsoft.besthack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycleView = findViewById<RecyclerView>(R.id.recycleView)
        calendarView = findViewById<CalendarView>(R.id.calendarView)
        val currentDate = findViewById<TextView>(R.id.currentDate)

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleAdapter = IssueAdapter(this)
        recycleView.adapter = recycleAdapter

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                    motionLayout: MotionLayout,
                    i: Int,
                    i1: Int
            ) {
            }

            override fun onTransitionChange(
                    motionLayout: MotionLayout,
                    i: Int,
                    i1: Int,
                    v: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout, i: Int) {
//                recycleView.setScrollEnable(motionLayout.currentState == R.id.end)
            }
            override fun onTransitionTrigger(
                    motionLayout: MotionLayout,
                    i: Int,
                    b: Boolean,
                    v: Float
            ) {
            }
        })

//        recycleView.setScrollEnable(false)

        val c: Calendar = Calendar.getInstance()
        c.timeInMillis = calendarView.date

        var location = Calendar.getAvailableLocales()

        currentDate.text = "${c.get(Calendar.DAY_OF_MONTH)} ${c.getDisplayName(Calendar.MONTH, 2, Locale("en", "RU"))} ${c.get(Calendar.YEAR)}"

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.YEAR, year)
            currentDate.text = "$dayOfMonth ${c.getDisplayName(Calendar.MONTH, 2, Locale("en", "RU"))} $year"
        }

        recycleAdapter.update(listOf(
                Issue("header", "20!8", "desc", Chat()),
                Issue("header1", "20!8", "desc", Chat()),
                Issue("header2", "20!8", "desc", Chat()),
                Issue("header", "20!8", "desc", Chat()),
                Issue("header1", "20!8", "desc", Chat()),
                Issue("header2", "20!8", "desc", Chat()),
                Issue("header", "20!8", "desc", Chat()),
                Issue("header1", "20!8", "desc", Chat()),
                Issue("header2", "20!8", "desc", Chat()),
                Issue("header", "20!8", "desc", Chat()),
                Issue("header1", "20!8", "desc", Chat()),
                Issue("header2", "20!8", "desc", Chat())
        ).sortedBy { it.header })
//        recycleAdapter.update(EventsProvider.getAllAvailableEvents())
    }

    lateinit var recycleAdapter: IssueAdapter
    private lateinit var calendarView: CalendarView
}