package com.godelsoft.besthack

import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.godelsoft.besthack.recycleViewAdapters.IssueAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    private lateinit var recycleAdapter: IssueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainContext = this
        main = this

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleAdapter =
            IssueAdapter(this)
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


        system.apply {
            deviceName.text = "ASUS ROG"
            lastValidDate.text = "28.04"
            progressBar.progress = 68
            progressBar.progressDrawable.setColorFilter(Color.rgb( 255 - 255 / 100 * progressBar.progress, 255 / 100 * progressBar.progress, 0), PorterDuff.Mode.SRC_IN )
        }

        monitor.apply {
            deviceName.text = "AOC 14\""
            lastValidDate.text = "29.04"
            progressBar.progress = 20
            progressBar.progressDrawable.setColorFilter(Color.rgb( 255 - 255 / 100 * progressBar.progress, 255 / 100 * progressBar.progress, 0), PorterDuff.Mode.SRC_IN )
        }

        second_monitor.apply {
            deviceName.text = "AOC 16\""
            lastValidDate.text = "30.04"
            progressBar.progress = 45
            progressBar.progressDrawable.setColorFilter(Color.rgb( 255 - 255 / 100 * progressBar.progress, 255 / 100 * progressBar.progress, 0), PorterDuff.Mode.SRC_IN )
        }

        keyboard.apply {
            deviceName.text = "ASUS Claymore"
            lastValidDate.text = "20.06"
            progressBar.progress = 97
            progressBar.progressDrawable.setColorFilter(Color.rgb( 255 - 255 / 100 * progressBar.progress, 255 / 100 * progressBar.progress, 0), PorterDuff.Mode.SRC_IN )
        }

        mouse.apply {
            deviceName.text = "Zowie EC2-A"
            lastValidDate.text = "14.04"
            progressBar.progress = 6
            progressBar.progressDrawable.setColorFilter(Color.rgb( 255 - 255 / 100 * progressBar.progress, 255 / 100 * progressBar.progress, 0), PorterDuff.Mode.SRC_IN )
        }

        headphones.apply {
            deviceName.text = "HyperX"
            lastValidDate.text = "16.04"
            progressBar.progress = 28
            progressBar.progressDrawable.setColorFilter(Color.rgb( 255 - 255 / 100 * progressBar.progress, 255 / 100 * progressBar.progress, 0), PorterDuff.Mode.SRC_IN )
        }

        cam.apply {
            deviceName.text = "LG 270"
            lastValidDate.text = "15.04"
            progressBar.progress = 70
            progressBar.progressDrawable.setColorFilter(Color.rgb( 255 - 255 / 100 * progressBar.progress, 255 / 100 * progressBar.progress, 0), PorterDuff.Mode.SRC_IN )
        }

        mic.apply {
            deviceName.text = "Yeti"
            lastValidDate.text = "23.05"
            progressBar.progress = 87
            progressBar.progressDrawable.setColorFilter(Color.rgb( 255 - 255 / 100 * progressBar.progress, 255 / 100 * progressBar.progress, 0), PorterDuff.Mode.SRC_IN )
        }

        wifi.apply {
            deviceName.text = "Xiaomi A8"
            lastValidDate.text = "21.09"
            progressBar.progress = 94
            progressBar.progressDrawable.setColorFilter(Color.rgb( 255 - 255 / 100 * progressBar.progress, 255 / 100 * progressBar.progress, 0), PorterDuff.Mode.SRC_IN )
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

        when(User.current.type) {
            UserType.WORKER -> {
                floatingActionButton.visibility = VISIBLE
                floatingActionButton.setOnClickListener {
                    Issue.startCreation()
                }
            }
            UserType.SUPPORT -> {
                floatingActionButton.visibility = GONE
            }
            UserType.TEAM_LEAD -> {
                floatingActionButton.visibility = GONE
            }
        }

        floatingActionButton.setOnClickListener {
            startActivity<IssueChatActivity>()
        }
    }

    companion object {
        lateinit var mainContext: Context
        lateinit var main: MainActivity
    }
}