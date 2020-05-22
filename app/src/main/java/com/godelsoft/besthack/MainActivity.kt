package com.godelsoft.besthack

import android.content.Context
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TableRow
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import com.godelsoft.besthack.recycleViewAdapters.IssueAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.startActivity
import java.util.*

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

        for ((r, v) in devicePanel.children.withIndex()) {
            if (v is TableRow) {
                for ((i, card) in v.children.withIndex()) {
                    card.deviceName.text = User.current.devices[3 * r + i].model
                    User.current.devices[3 * r + i].let { card.lastValidDate.text = CalFormatter.datef(Calendar.getInstance().apply {
                        set(Calendar.MILLISECOND, it.buyTime.timeInMillis.toInt())
                        add(Calendar.MILLISECOND,
                        it.validTime.toInt()
                    ) })}
                    card.progressBar.progress = User.current.devices[3 * r + i].getProgress()
                    card.progressBar.progressDrawable.setColorFilter( User.current.devices[3 * r + i].getProgressColor(), PorterDuff.Mode.SRC_IN)
                }
            }
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